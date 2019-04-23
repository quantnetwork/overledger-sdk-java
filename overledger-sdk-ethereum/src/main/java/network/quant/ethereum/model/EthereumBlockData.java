package network.quant.ethereum.model;

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
public class EthereumBlockData {

    BigDecimal difficulty;
    String extraData;
    BigDecimal gasLimit;
    BigDecimal gasUsed;
    String hash;
    String logsBloom;
    String miner;
    String mixHash;
    String nonce;
    BigDecimal number;
    String parentHash;
    String receiptsRoot;
    String sha3Uncles;
    BigDecimal size;
    String stateRoot;
    BigDecimal timestamp;
    BigDecimal totalDifficulty;
    String transactions[];
    String transactionsRoot;
    String uncles[];

}
