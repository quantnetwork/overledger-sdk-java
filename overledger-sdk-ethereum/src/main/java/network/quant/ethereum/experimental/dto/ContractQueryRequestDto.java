package network.quant.ethereum.experimental.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import network.quant.api.DltTransaction;
import network.quant.ethereum.experimental.dto.ContractArgument;

import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public class ContractQueryRequestDto implements DltTransaction {

    String dlt;
    @NonNull
    String fromAddress;
    @NonNull
    String contractAddress;
    @NonNull
    String funcName;
    List<PreparedContractArgument> inputValues;
    List<PreparedContractArgument> outputTypes;

}
