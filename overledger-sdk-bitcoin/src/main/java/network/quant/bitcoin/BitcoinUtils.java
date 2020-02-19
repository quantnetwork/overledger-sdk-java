package network.quant.bitcoin;


import network.quant.api.NETWORK;
import lombok.extern.slf4j.Slf4j;
import network.quant.bitcoin.exception.BitcoinDataNotMatchingLengthException;
import network.quant.bitcoin.exception.BitcoinInvalidAddressException;
import org.bitcoinj.core.Base58;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for processing Bitcoin data
 */
@Slf4j
public class BitcoinUtils {

    static final int NETWORK_SIZE = 1;
    static final int PAYLOAD_SIZE = 20;
    private static final int ADDRESS_SIZE = 25;
    static final int CHECKSUM_SIZE = 4;
    static final byte ZERO = 0;
    private static final String HASH_ALG = "SHA-256";
    private static MessageDigest DIGEST;

    private static byte[] getBytes(int from, int length, byte bytes[]) {
        byte result[] = new byte[length];
        int byteReadingLength = from + length;
        if (byteReadingLength > bytes.length) {
            Arrays.fill(result, ZERO);
            int remainLength = byteReadingLength - bytes.length;
            int readingLength = length - remainLength;
            System.arraycopy(bytes, from, result, 0, readingLength);
        } else {
            System.arraycopy(bytes, from, result, 0, length);
        }
        return result;
    }

    static byte[] sha256(byte plain[]) {
        if (null == DIGEST) {
            try {
                DIGEST = MessageDigest.getInstance(HASH_ALG);
            } catch (Exception e) {
                log.error(String.format("%s#sha256(): failed", BitcoinUtils.class.getSimpleName()), e);
            }
        }
        if (null != DIGEST) {
            return DIGEST.digest(plain);
        } else {
            return null;
        }
    }

    /**
     * Encode 20 bytes data into a single Bitcoin address
     * @param network NETWORK containing network address
     * @param bytes byte array containing the original data
     * @return byte array containing the encoded address byte array
     */
    static byte[] createAddrBytes(NETWORK network, byte bytes[]) {
        byte result[] = null;
        if (null != bytes) {
            result = new byte[ADDRESS_SIZE];
            System.arraycopy(new byte[]{(byte)network.getAddressType()}, 0, result, 0, NETWORK_SIZE);
            System.arraycopy(bytes, 0, result, NETWORK_SIZE, PAYLOAD_SIZE);
            byte hash[] = sha256(sha256(
                    ByteBuffer
                            .allocate(NETWORK_SIZE + PAYLOAD_SIZE)
                            .put((byte)network.getAddressType())
                            .put(bytes)
                            .array()
            ));
            if (null == hash) {
                return null;
            }
            System.arraycopy(hash, 0, result, PAYLOAD_SIZE + NETWORK_SIZE, CHECKSUM_SIZE);
        }
        return result;
    }

    /**
     * Encode data into a list of Bitcoin address
     * @param network NETWORK containing network address
     * @param bytes byte array containing the original data
     * @return String list containing encoded address list
     */
    static List<String> getAddress(NETWORK network, byte bytes[]) {
        List<String> addrList = new ArrayList<>();
        for (int i=0; i<bytes.length; i+=PAYLOAD_SIZE) {
            byte result[] = createAddrBytes(network, getBytes(i, PAYLOAD_SIZE, bytes));
            if (null != result) {
                addrList.add(Base58.encode(result));
            }
        }
        return addrList;
    }


    /**
     * Decode list of data address to data
     * @param addressList String list containing list of Bitcoin address that contains data
     * @param length int containing expected data length
     * @return byte array containing the decoded data
     */
    static byte[] getData(List<String> addressList, int length) throws BitcoinDataNotMatchingLengthException, BitcoinInvalidAddressException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        for (int i=0; i<addressList.size(); i++) {
            byte[] address = Base58.decode(addressList.get(i));
            ByteBuffer network = ByteBuffer.allocate(BitcoinUtils.NETWORK_SIZE);
            network.put(address, 0, BitcoinUtils.NETWORK_SIZE);
            ByteBuffer data = ByteBuffer.allocate(BitcoinUtils.PAYLOAD_SIZE);
            data.put(address, BitcoinUtils.NETWORK_SIZE, BitcoinUtils.PAYLOAD_SIZE);
            ByteBuffer checksum = ByteBuffer.allocate(BitcoinUtils.CHECKSUM_SIZE);
            checksum.put(address, BitcoinUtils.NETWORK_SIZE+BitcoinUtils.PAYLOAD_SIZE, BitcoinUtils.CHECKSUM_SIZE);
            ByteBuffer calculatedHash = ByteBuffer.allocate(BitcoinUtils.CHECKSUM_SIZE);
            byte hash[] = sha256(sha256(
                    ByteBuffer
                            .allocate(NETWORK_SIZE + PAYLOAD_SIZE)
                            .put(network.array())
                            .put(data.array())
                            .array()
            ));
            if (null != hash) {
                calculatedHash.put(hash, 0, BitcoinUtils.CHECKSUM_SIZE);
                if (Arrays.equals(calculatedHash.array(), checksum.array())) {
                    int location = i * BitcoinUtils.PAYLOAD_SIZE;
                    if (location + BitcoinUtils.PAYLOAD_SIZE > length) {
                        byteBuffer.put(data.array(), 0, length - location);
                        return byteBuffer.array();
                    } else {
                        byteBuffer.put(data.array(),0, BitcoinUtils.PAYLOAD_SIZE);
                    }
                } else {
                    throw new BitcoinInvalidAddressException(addressList.get(i));
                }
            } else {
                throw new BitcoinInvalidAddressException(addressList.get(i));
            }
        }
        throw new BitcoinDataNotMatchingLengthException();
    }

}
