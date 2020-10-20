package network.quant.ethereum.experimental.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractArgument implements Serializable {

    ContractInputTypeOptions type;
    String value;
    EthereumUintIntOptions selectedIntegerLength;
    EthereumBytesOptions selectedBytesLength;
    Long selectedArrayLength;

}
