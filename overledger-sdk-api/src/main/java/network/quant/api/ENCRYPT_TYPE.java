package network.quant.api;

/**
 * Data encryption type in transaction
 * Basic encryption algorithm SDK prefer to accept
 * SDK does not enforce encrypt mode and padding strategy
 */
public enum ENCRYPT_TYPE {

    NONE, DSA, RSA, AES1, AES2, AES3

}
