package network.quant.essential.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import network.quant.api.OverledgerTransactions;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OverledgerTransactionsResponse implements OverledgerTransactions {

    int totalTransactions;
    List<OverledgerTransactionResponse> transactions;

}
