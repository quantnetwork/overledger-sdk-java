# overledger-sdk-essential

Essential module implements most of interfaces from Overledger SDK API, except Account interface.

## Maven dependency:

```xml
<dependency>
    <groupId>network.quant</groupId>
    <artifactId>overledger-sdk-essential</artifactId>
    <version>1.0.0-alpha.2</version>
</dependency>
```

## DefaultOverledgerSDK

[DefaultOverledgerSDK](./src/network/quant/essential/DefaultOverledgerSDK.java) implements OverledgerSDK.
Use it as a entry point of the SDK. Includes add DLT account, and request services from BPI layer.

```java
public class OverledgerSDKExample {
    
    private OverledgerSDK overledgerSDK;
    
    public OverledgerSDKExample(Account... accounts) {
        this.overledgerSDK = DefaultOverledgerSDK.newInstance(NETWORK.MAIN);
        this.overledgerSDK.addAccount(DLT.bitcoin.name(), accounts[0]);
        this.overledgerSDK.addAccount(DLT.ethereum.name(), accounts[1]);
        this.overledgerSDK.addAccount(DLT.ripple.name(), accounts[2]);
    }
    
    public OverledgerTransaction writeTransaction(OverledgerTransaction ovlTransaction) {
        return this.overledgerSDK.writeTransaction(ovlTransaction);
    }
    
}
```
