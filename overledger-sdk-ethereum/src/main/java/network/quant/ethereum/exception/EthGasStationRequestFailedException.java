package network.quant.ethereum.exception;

/**
 *  This exception is thrown when the Ethereum gas station information could not be retrieved.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class EthGasStationRequestFailedException extends Exception {

    public EthGasStationRequestFailedException(String message) {
        super(String.format("Unable to retrieve fee from ethgasstation.info: %s", message));
    }

}
