package network.quant.essential.dto;

import network.quant.api.DLTStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * DLT transaction for write response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DltTransactionResponse implements network.quant.api.DltTransactionResponse {

    String dlt;
    String transactionHash;
    Integer blockHeight;
    DLTStatus status;
    String dltVersion;
    String customReference;

}
