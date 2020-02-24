package network.quant.ethereum.model;

/**
 * Definition of DLT Ethereum smart contract extra fields
 */
public interface EthereumSmartContractExtraFields {

    /**
     * does this function have a payable function (interface) or are we calling a payable smart contrac function (functionCall)
     * @return Boolean containing if the smart contract has payable
     */
    String getPayable();

}
