package network.quant.bitcoin.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BitcoinBlockData {

    String hash;
    BigDecimal confirmations;
    BigDecimal strippedsize;
    BigDecimal size;
    BigDecimal weight;
    BigDecimal height;
    BigDecimal version;
    BigDecimal versionHex;
    String merkleroot;
    String tx[];
    BigDecimal time;
    BigDecimal mediantime;
    BigDecimal nonce;
    String bits;
    BigInteger difficulty;
    String chainwork;
    String previousblockhash;
    String nextblockhash;

}
