package network.quant.event;

import network.quant.api.OverledgerTransaction;
import network.quant.api.OverledgerTransactions;
import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.essential.dto.OverledgerTransactionsResponse;
import network.quant.util.Page;
import network.quant.util.PageParams;

import java.util.List;
import java.util.UUID;

public interface ApplicationDataHandler {

    void onLoadSetting(
            String bpiKey,
            String mappId,
            String writeTransactions,
            String readTransactionsByMappId,
            String readTransactionsByMappIdPage,
            String readTransactionsByTransactionId,
            String readTransactionsByTransactionHash,
            String searchTransaction,
            String searchAddress,
            String searchBlocks,
            String balances

    );

    void onAccountGenerated(String type, String secretKey, String address);

    void onAccountReceived(String type, int amount);

    void onPurchaseFailed(String account_not_found);

    void onPurchaseSuccess(OverledgerTransaction writeTransaction);

    void onPurchaseSuccess(UUID overledgerTransactionId);

    void onLoadOrders(OverledgerTransactions overledgerTransactionsResponse, PageParams page);

    void onLoadOrders(OverledgerTransactionResponse[] writeOverledgerTransactionResponses);

}
