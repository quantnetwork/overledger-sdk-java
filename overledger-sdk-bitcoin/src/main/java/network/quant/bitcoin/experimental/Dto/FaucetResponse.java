package network.quant.bitcoin.experimental.Dto;

import lombok.Data;

@Data
public class FaucetResponse {

    int status;
    String statusMessage;
    String message;

}
