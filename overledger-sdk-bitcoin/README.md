# overledger-sdk-bitcoin

This module implements Account specifically for Bitcoin blockchain

## network.quant.bitcoin.BitcoinAccount

[BitcoinAccount](./src/main/java/network/quant/bitcoin/BitcoinAccount.java) is the Bitcoin implementation of Account

Instance of BitcoinAccount class by using BitcoinAccount#getInstance(NETWORK) function.
If no private key provided, a new private key will be automatically generated.

UTXO need to be added into the account before all other functions.
Use method BitcoinAccount#addUtxo(transactionHash, outpoint, valueInSatoshi, blockHeight, address) to add UTXO

Use BitcoinAccount#sign(fromAddress, toAddress, message, dltTransaction) to sign DLT transaction.

# Experimental library

## network.quant.bitcoin.experimental.BitcoinFaucetHelper

[BitcoinFaucetHelper](./src/main/java/network/quant/bitcoin/experimental/BitcoinFaucetHelper.java) can be used for accessing Overledger Bitcoin faucet.
Call function BitcoinFaucetHelper#fundAccount(BitcoinAccount) to fund giving account 1 BTC (from Overledger testnet).

Application uses BitcoinFaucetHelper need to have Faucet URL, this module does not provide Faucet URL.
