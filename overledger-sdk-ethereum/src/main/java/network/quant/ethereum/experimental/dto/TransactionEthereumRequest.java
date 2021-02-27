package network.quant.ethereum.experimental.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import network.quant.api.SCFunctionType;
import network.quant.api.SignedTransaction;
import network.quant.essential.dto.DltTransactionRequest;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

@Data
@Builder(builderMethodName = "trxEthereumReqBuilder")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionEthereumRequest extends DltTransactionRequest {

    String dlt;
    String toAddress;
    String fromAddress;
    Long sequence;
    BigInteger amount;
    BigInteger fee;
    BigInteger feeLimit;
    @NotNull
    String funcName;
    SCFunctionType functionType;
    List<ContractArgument> inputValues;
    List<ContractArgument> outputTypes;
    SignedTransaction signedTransaction;

    String code;
}
