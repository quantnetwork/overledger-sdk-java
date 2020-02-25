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

    /**
     * the list of payable functions
     * @return String[] containing the payable function names
     */
    String[] getPayableFunctions();

    /**
     * which smart contract functions require new transactions to be added on the distributed ledger
     * (i.e. which functions change the state of the ledger)
     * @return String[] containing the function name list
     */
    String[] getNewDLTxRequired();

}
