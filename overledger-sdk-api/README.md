# overledger-sdk-api

API module contains interfaces which are used as guideline for helping SDK implementation

## Maven dependency:

```xml
<dependency>
    <groupId>io.overledger</groupId>
    <artifactId>overledger-sdk-api</artifactId>
    <version>1.0.0-alpha.2</version>
</dependency>
```

## network.quant.OverledgerContext

[OverledgerContext](./src/network/quant/OverledgerContext.java) is designed for holding Overledger BPI key, Mapp ID and BPI endpoint constants.

OverledgerContext.loadContext() method loads following properties from Properties object or InputStream

```
overledger.baseurl=[Endpont base URL]
overledger.write=[Endpont for writing transaction]
overledger.read.id=[Endpont for reading transaction by transaction ID]
overledger.read.mappid=[Endpont for reading transaction by Mapp ID]
overledger.read.txhash=[Endpont for reading transaction by transaction hash]
overledger.bpikey=[BPI Key]
overledger.mappid=[Mapp ID]
```

## network.quant.api.Account

[Account](./src/network/quant/api/Account.java) interface is used for declaring basic account functions.

Overledger SDK use Account to sign transactions

## network.quant.api.Client

Implement [Client](./src/network/quant/api/Client.java) interface to access BPI service with basic authorization.

Use OverledgerContext to get endpoint URLs, BPI key and Mapp ID

## network.quant.api.OverledgerSDK

Interface [OverledgerSDK](./src/network/quant/api/OverledgerSDK.java) is designed as entry point of the SDK.
Application access BPI layer through OverledgerSDK implementation.

OverledgerSDK also includes methods for collecting DLT accounts and client implementation. 
