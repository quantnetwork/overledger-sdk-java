package network.quant.util;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusRequest implements Serializable {
    @NonNull
    private String mappId;
    @NonNull
    private String callbackUrl;
    private Instant timestamp;
    @NonNull
    private UUID overledgerTransactionId;
}
