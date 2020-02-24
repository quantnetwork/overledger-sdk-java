package network.quant.api;

import network.quant.essential.types.associatedEnums.SCFunctionTypeOptions;

/**
 * A generic object to describe a smart contract function.
 */
public interface SmartContractFunctionDefinition {

    /**
     * Get information on what type of function does this describe
     * (constructor, normal function call, with parameters, without parameters)
     * @return functionTypeOptions containing the chosen function type
     */
    SCFunctionTypeOptions getFunctionType();

    /**
     * the name of the function. In some circumstances,
     * you can set to the empty string if the function type is constructor. Check documentation.
     * @return String containing the function name
     */
    String getFunctionName();

    /**
     * the code of the function (if available) or the code used to call the function (if required)
     * @return String containing the function code
     */
    String getFunctionCode();

    /**
     * the list of parameters this function takes as input
     * @return SmartContractFunctionParam[] containing the function's input parameters (in order)
     */
    SmartContractFunctionParam[] getInputParams();

    /**
     * the list of parameters this function takes as output
     * @return SmartContractFunctionParam[] containing the function's output parameters (in order)
     */
    SmartContractFunctionParam[] getOutputParams();

}
