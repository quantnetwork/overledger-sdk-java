package network.quant.exception;

/**
 *  This exception is thrown when an address checksum does not match the address calculation.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class AddressChecksumNotMatchException extends Exception {

    public AddressChecksumNotMatchException() {
        super("Given address does not match the checksum");
    }

}
