package network.quant.ethereum.model;

import network.quant.ethereum.model.associatedEnums.BytesBOptions;
import network.quant.ethereum.model.associatedEnums.TypeOptions;
import network.quant.ethereum.model.associatedEnums.UintIntBOptions;

public interface SmartContractEthereumParamType {

    /**
     * information on the selectedType from the valid options.
     * @return TypeOptions containing the Ethereum parameter type
     */
    TypeOptions getSelectedType();

    /**
     * If an int/uint type is selected, then selectedIntegerLength is required (otherwise it will return null).
     * @return UintIntBOptions containing the uint/int smart contract byte length
     */
    UintIntBOptions getSelectedIntegerLength();

    /**
     * If a byte type was chosen then further information is required on the exact number of bytes being used. (otherwise it will return null).
     * @return BytesBOptions containing length of the smart contract byte variable.
     */
    BytesBOptions getSelectedBytesLength();

}
