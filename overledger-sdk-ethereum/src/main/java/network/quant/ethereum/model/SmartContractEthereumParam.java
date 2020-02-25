package network.quant.ethereum.model;

import network.quant.api.SmartContractFunctionParam;
import network.quant.ethereum.model.associatedEnums.TypeOptions;

/**
 * Definition of DLT Ethereum smart contract parameter
 * This extends the basic smart contract function param
 */
public interface SmartContractEthereumParam extends SmartContractFunctionParam {

    /**
     * redefinition on selectedType for Ethereum.
     * Get information on the parameter's type
     * @return SmartContractEthereumParamType containing the parameter's type information
     */
     SmartContractEthereumParamType getSelectedType();

    /**
     * Get information on the parameter's type (optional)
     * @return SmartContractEthereumParamType containing the parameter's name
     */
    String getSelectedName();

}
