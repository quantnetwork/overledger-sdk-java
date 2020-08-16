package network.quant.ethereum.experimental.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import network.quant.api.DltTransaction;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EthBuildTransactionResponse implements DltTransaction {
    String dlt;
    Long nonce;
    BigInteger chainId;
    @NotNull
    String toAddress;
    BigInteger gasLimit;
    BigInteger gasPrice;
    BigInteger value;
    @NotNull
    String data;
}
