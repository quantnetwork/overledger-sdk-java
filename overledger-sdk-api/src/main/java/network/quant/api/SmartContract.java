package network.quant.api;

/**
 * A generic object to describe a smart contract.
 */
public interface SmartContract {

    /**
     * Get the code of this smart contract (if available)
     * @return String containing the smart contracts code
     */
    String getCode();

    /**
     * Get the identifier of this smart contract (if it has one)
     * @return String containing the smart contract's id
     */
    String getId();

    /**
     * the interface of this smart contract, defined one function at a time
     * @return SmartContractFunctionDefinition[] containing each function's definition
     */
    SmartContractFunctionDefinition[] getInterface();

    /**
     * information on what function of the smart contract to call, and with what parameters,
     * defined one function at a time
     * @return SmartContractFunctionDefinition[] containing each function call's definition
     */
    SmartContractFunctionDefinition[] getFunctionCall();

    /**
     * Get any additional DLT specific transaction fields,
     * @return Object describing the additionalFields
     */
    Object getExtraFields();

}
