package network.quant.api;

/**
 * A generic object to describe a smart contract function parameter.
 */
public interface SmartContractFunctionParam {

    /**
     * Get information on the parameter's type
     * @return Object containing the parameter's type
     */
    Object getType();

    /**
     * Get information on the parameter's name
     * @return String containing the parameter's name
     */
    String getName();

    /**
     * Get information on the parameter's value
     * @return Object containing the parameter's value
     */
    Object getValue();

    /**
     * Get information on the valid values that this parameter can take
     * @return Object containing the parameter's valid values
     */
    Object getOptions();

}
