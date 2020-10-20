package network.quant.ethereum.experimental.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import network.quant.api.DltTransaction;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PUBLIC)
public class EventSubscribeRequest implements Serializable, DltTransaction {
    String dlt;
    List<EventSubscribeRequestDetails> requestDetailsList;
}
