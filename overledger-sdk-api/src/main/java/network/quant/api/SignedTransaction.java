package network.quant.api;

import java.util.List;

public class SignedTransaction {

    List<String> signatures;
    List<String> transactions;

    public SignedTransaction() {
    }

    public SignedTransaction(List<String> signatures, List<String> transactions) {
        this.signatures = signatures;
        this.transactions = transactions;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }

}
