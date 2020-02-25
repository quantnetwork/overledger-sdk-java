package network.quant.ethereum.model;

import network.quant.api.SmartContract;

/**
 * Definition of DLT Ethereum smart contract
 * This extends the basic DLT smart contract definition
 */
public interface SmartContractEthereum extends SmartContract {

    /**
     * Get any additional DLT specific smart contract fields,
     * @return Object describing the additionalFields
     */
    EthereumSmartContractExtraFields getExtraFields();

}
