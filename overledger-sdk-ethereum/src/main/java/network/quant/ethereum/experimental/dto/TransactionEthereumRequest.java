package network.quant.ethereum.experimental.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import network.quant.api.DltTransactionRequest;
import network.quant.api.SCFunctionType;
import network.quant.api.SignedTransaction;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@Getter
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionEthereumRequest implements DltTransactionRequest {

    String dlt;
    String toAddress;
    Long sequence;
    BigInteger amount;
    BigInteger fee;
    BigInteger feeLimit;
    @NotNull
    String funcName;
    SCFunctionType functionType;
    List<ContractArgument> inputValues;
    List<ContractArgument> outputTypes;

    @Override
    public String getFromAddress() {
        return null;
    }

    @Override
    public String getChangeAddress() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getCallbackUrl() {
        return null;
    }

    @Override
    public SignedTransaction getSignedTransaction() {
        return null;
    }

    @Override
    public void setSignedTransaction(SignedTransaction signedTransaction) {

    }
}
