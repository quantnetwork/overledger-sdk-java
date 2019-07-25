package network.quant.bitcoin.experimental.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaucetResponseDto {

    public String address;
    public BigDecimal amount;
    public String message;
    public String status;
    public String txnHash;
    public Long vout;

}
