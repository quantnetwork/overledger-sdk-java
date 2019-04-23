package network.quant.bitcoin.experimental.Dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FaucetResponseDto {

    public String address;
    public BigDecimal amount;
    public String message;
    public String status;
    public String txnHash;
    public Long vout;
    public String statusMessage;
}
