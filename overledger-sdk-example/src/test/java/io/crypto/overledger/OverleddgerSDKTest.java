package io.crypto.overledger;

import network.quant.api.Account;
import network.quant.api.DLT;
import network.quant.api.NETWORK;
import network.quant.api.OverledgerSDK;
import network.quant.bitcoin.BitcoinAccount;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.ethereum.EthereumAccount;
import network.quant.ripple.RippleAccount;
import org.junit.Before;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;

public class OverleddgerSDKTest {

    private OverledgerSDK overledgerSDK;
    private Account bitcoinAccount;
    private Account ethereumAccount;
    private Account rippleAccount;

    @Before
    public void setup() {
        DefaultOverledgerSDK.setDefaultLocation("../src/main/resources/context.properties");
        this.bitcoinAccount = BitcoinAccount.getInstance(NETWORK.TEST, DatatypeConverter.parseHexBinary("9d154eb68f5bcec61463885e9f32eeeb8f9dc53cfec87810e0e98529e4acdd27"));
        this.ethereumAccount = EthereumAccount.getInstance(NETWORK.TEST, DatatypeConverter.parseHexBinary("0B2BBC62B5544B7703C207D8C4B3866EF247C0F8FCC4A57E9F29BB08CA373E31"), BigInteger.ZERO);
        this.rippleAccount = RippleAccount.getInstance(NETWORK.TEST, "shJSofDUkKCAwFt6TsK4yfCR4JfA9", BigInteger.ONE);
        if (null == this.overledgerSDK) {
            this.overledgerSDK = DefaultOverledgerSDK.newInstance(NETWORK.TEST);
        }
        this.overledgerSDK.addAccount(DLT.bitcoin.name(), this.bitcoinAccount);
        this.overledgerSDK.addAccount(DLT.ethereum.name(), this.ethereumAccount);
        this.overledgerSDK.addAccount(DLT.ripple.name(), this.rippleAccount);
    }
}
