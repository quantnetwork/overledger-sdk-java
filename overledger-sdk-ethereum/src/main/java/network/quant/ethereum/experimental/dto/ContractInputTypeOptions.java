package network.quant.ethereum.experimental.dto;

public enum ContractInputTypeOptions {
    UINT("uint"),
    INT("int"),
    ADDRESS("address"),
    BOOL("bool"),
    BYTES("bytes"),
    STRING("string"),
    INT_ARRAY("int[k]"),
    UINT_ARRAY("uint[k]"),
    ADDRESS_ARRAY("address[k]"),
    BOOLEAN_ARRAY("bool[k]"),
    BYTES_B_ARRAY("bytes[k]");

    public final String label;

    private ContractInputTypeOptions(String label) {
        this.label = label;
    }


}
