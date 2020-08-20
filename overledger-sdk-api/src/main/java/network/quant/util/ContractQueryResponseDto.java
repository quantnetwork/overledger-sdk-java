package network.quant.util;

import lombok.*;
import lombok.experimental.FieldDefaults;
import network.quant.api.DltTransaction;

import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
public class ContractQueryResponseDto implements DltTransaction {

    String dlt;
    @NonNull
    String fromAddress;
    @NonNull
    String contractAddress;
    @NonNull
    String funcName;
    List<String> results;

}