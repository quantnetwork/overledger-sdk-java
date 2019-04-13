package network.quant.essential.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import network.quant.util.Transaction;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenericDltTransaction extends Transaction {

}
