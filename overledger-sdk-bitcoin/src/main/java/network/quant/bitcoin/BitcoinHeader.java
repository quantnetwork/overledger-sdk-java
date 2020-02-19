package network.quant.bitcoin;

import network.quant.api.DATA_TYPE;
import network.quant.api.ENCRYPT_TYPE;
import network.quant.api.NETWORK;
import network.quant.exception.AddressChecksumNotMatchException;
import network.quant.exception.NetworkNotMatchException;
import network.quant.exception.UnknownDataException;
import lombok.Getter;
import org.bitcoinj.core.Base58;

import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Header format [QNT][TYPE][ENCRYPTION][COMPRESSED][DATA_LENGTH]
 * Header in total size 20 bytes, and follow the format: [3][4][4][1][8]
 */
class BitcoinHeader {

    private static final byte CODE[] = "QNT".getBytes();
    private static ByteBuffer LONG_BUF = ByteBuffer.allocate(Long.BYTES);
    @Getter
    private DATA_TYPE type;
    @Getter
    private ENCRYPT_TYPE encryption;
    @Getter
    private Boolean compressed;
    @Getter
    private long length;
    @Getter
    private String address;
    private NETWORK network;

    /**
     * Construct Bitcoin header address from data info
     * @param network NETWORK containing network address
     * @param type DATA_TYPE containing data type
     * @param encryption ENCRYPT_TYPE containing data encryption option
     * @param compressed boolean containing if data is compressed
     * @param length long containing data length
     */
    BitcoinHeader(NETWORK network, DATA_TYPE type, ENCRYPT_TYPE encryption, Boolean compressed, long length) {
        this.network = network;
        this.type = type;
        this.encryption = encryption;
        this.compressed = compressed;
        this.length = length;
        this.parse();
    }

    /**
     * Construct Bitcoin header address from address
     * @param network NETWORK containing network address
     * @param address String containing address
     * @throws NetworkNotMatchException throw when network address not match actual address
     * @throws AddressChecksumNotMatchException throw when address checksum failed
     * @throws UnknownDataException throw when unable to decode the address
     */
    BitcoinHeader(NETWORK network, String address) throws NetworkNotMatchException, AddressChecksumNotMatchException, UnknownDataException {
        this.network = network;
        this.address = address;
        this.format();
    }

    private void parse() {
        byte result[] = new byte[BitcoinUtils.PAYLOAD_SIZE];

        int from = 0;
        System.arraycopy(CODE, 0, result, from, CODE.length);
        from += CODE.length;

        byte type[] = new byte[4];
        byte typeRaw[] = this.type.name().getBytes();
        Arrays.fill(type, BitcoinUtils.ZERO);
        System.arraycopy(typeRaw, 0, type, 0, typeRaw.length);
        System.arraycopy(type, 0, result, from, type.length);
        from += type.length;

        byte encryption[] = new byte[4];
        byte encryptionRaw[] = this.encryption.name().getBytes();
        Arrays.fill(encryption, BitcoinUtils.ZERO);
        System.arraycopy(encryptionRaw, 0, encryption, 0, encryptionRaw.length);
        System.arraycopy(encryption, 0, result, from, encryption.length);
        from += encryption.length;

        result[from++] = this.compressed ? ((byte) 1) : BitcoinUtils.ZERO;

        System.arraycopy(LONG_BUF.putLong(0, this.length).array(), 0, result, from, 8);

        byte addressByte[] = BitcoinUtils.createAddrBytes(this.network, result);
        if (null != addressByte) {
            this.address = Base58.encode(addressByte);
        }
    }

    private void format() throws NetworkNotMatchException, AddressChecksumNotMatchException, UnknownDataException {
        byte bytes[] = Base58.decode(this.address);
        ByteBuffer dataBuffer = ByteBuffer.allocate(BitcoinUtils.PAYLOAD_SIZE);
        dataBuffer.put(bytes, BitcoinUtils.NETWORK_SIZE, BitcoinUtils.PAYLOAD_SIZE);
        ByteBuffer checkSumBuffer = ByteBuffer.allocate(BitcoinUtils.CHECKSUM_SIZE);
        checkSumBuffer.put(bytes, BitcoinUtils.PAYLOAD_SIZE + BitcoinUtils.NETWORK_SIZE, BitcoinUtils.CHECKSUM_SIZE);
        if (bytes[0] == this.network.getAddressType()) {
            byte data[] = dataBuffer.array();
            ByteBuffer hashBuffer = ByteBuffer.allocate(BitcoinUtils.CHECKSUM_SIZE);
            byte hash[] = BitcoinUtils.sha256(BitcoinUtils.sha256(
                    ByteBuffer
                            .allocate(BitcoinUtils.NETWORK_SIZE + BitcoinUtils.PAYLOAD_SIZE)
                            .put((byte)network.getAddressType())
                            .put(data)
                            .array()
            ));
            if (null == hash) {
                return;
            }
            hashBuffer.put(hash, 0, BitcoinUtils.CHECKSUM_SIZE);
            if (Arrays.equals(hashBuffer.array(), checkSumBuffer.array())) {
                ByteBuffer codeBuffer = ByteBuffer.allocate(CODE.length);
                codeBuffer.put(data, 0, CODE.length);
                if (Arrays.equals(codeBuffer.array(), CODE)) {
                    ByteBuffer typeBuffer = ByteBuffer.allocate(4);
                    typeBuffer.put(data, CODE.length, 4);
                    this.type = DATA_TYPE.valueOf(new String(typeBuffer.array()));

                    ByteBuffer encryptionBuffer = ByteBuffer.allocate(4);
                    encryptionBuffer.put(data, CODE.length + 4, 4);
                    this.encryption = ENCRYPT_TYPE.valueOf(new String(encryptionBuffer.array()));

                    this.compressed = BitcoinUtils.ZERO != data[CODE.length + 8];

                    ByteBuffer lengthBuffer = ByteBuffer.allocate(8);
                    lengthBuffer.put(data, CODE.length + 9, 8);
                    lengthBuffer.flip();
                    this.length = lengthBuffer.getLong();
                } else {
                    throw new UnknownDataException();
                }
            } else {
                throw new AddressChecksumNotMatchException();
            }
        } else {
            throw new NetworkNotMatchException();
        }
    }

}
