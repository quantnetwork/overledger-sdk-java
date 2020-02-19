package network.quant.essential.dto;

import lombok.Data;
import network.quant.util.PagedResult;

import java.util.List;

@Deprecated
@Data
public class OverledgerTransactionPageResult implements PagedResult<OverledgerTransactionResponse> {

    List<OverledgerTransactionResponse> content;
    int pageNumber;
    int pageSize;
    int totalElements;
    boolean last;
    boolean first;
    int totalPages;

}
