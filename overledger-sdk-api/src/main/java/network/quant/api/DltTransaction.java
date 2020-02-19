package network.quant.api;

/**
 * Definition of DLT transaction
 * This is the basic DLT message definition that BPI layer accepts
 */
public interface DltTransaction {

    /**
     * Get DLT type, e.g: bitcoin, ethereum, ripple, etc
     * It has to be an acceptable string by BPI layer
     * DLT should be in lower case only
     * @return String containing DLT type
     */
    String getDlt();

}
