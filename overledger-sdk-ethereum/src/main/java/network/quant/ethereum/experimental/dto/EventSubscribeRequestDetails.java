package network.quant.ethereum.experimental.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import network.quant.api.DltTransaction;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventSubscribeRequestDetails implements Serializable {
    @NotNull
    String contractAddress;
    @NotNull
    String eventName;
    @NotNull
    List<ContractArgument> eventParams;
    @NotNull
    String callBackURL;
}
