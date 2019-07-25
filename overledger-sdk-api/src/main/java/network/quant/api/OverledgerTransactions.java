package network.quant.api;

import java.util.List;

/**
 * Definition of Overledger transactions
 * This is the basic Overledger message definition that BPI layer accepts
 */
public interface OverledgerTransactions {

    /**
     * Get total transaction count
     * @return int containing total transaction count
     */
    int getTotalTransactions();

    /**
     * Get transactions
     * @return List containing transaction list
     */
    <T extends OverledgerTransaction> List<T> getTransactions();

}
