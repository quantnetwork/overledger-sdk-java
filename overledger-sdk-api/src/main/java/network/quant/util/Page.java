package network.quant.util;

@Deprecated
public class Page {

    private static Page I = null;
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private boolean last;
    private boolean first;
    private int totalPages;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public static Page newInstance(PagedResult pagedResult) {
        if (null == pagedResult) {
            return null;
        }
        if (null == I) {
            I = new Page();
        }
        I.setPageSize(pagedResult.getPageSize());
        I.setPageNumber(pagedResult.getPageNumber());
        I.setFirst(pagedResult.isFirst());
        I.setLast(pagedResult.isLast());
        I.setTotalElements(pagedResult.getTotalElements());
        I.setTotalPages(pagedResult.getTotalPages());
        return I;
    }

}
