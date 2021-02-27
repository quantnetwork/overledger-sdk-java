package network.quant.essential.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import network.quant.api.SignedTransaction;

import java.math.BigInteger;

/**
 * DLT transaction for write request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DltTransactionRequest implements network.quant.api.DltTransactionRequest {

    String dlt;
    String fromAddress;
    String toAddress;
    String changeAddress;
    String message;
    BigInteger amount;
    BigInteger fee;
    BigInteger feeLimit;
    String callbackUrl;
    SignedTransaction signedTransaction;
    @JsonIgnore
    Long sequence;


}
