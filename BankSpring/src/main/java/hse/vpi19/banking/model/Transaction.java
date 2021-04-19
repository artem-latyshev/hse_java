package hse.vpi19.banking.model;

import java.math.BigDecimal;

public class Transaction implements Runnable {
    int id;
    Account fromAccount;
    Account toAccount;
    BigDecimal amount;
    boolean processed;
    boolean successful;

    public Transaction() {
    }

    public Transaction(int id, Account fromAccount, Account toAccount, BigDecimal amount) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public void run() {
        Object lock1 = fromAccount.getId() < toAccount.getId() ? fromAccount : toAccount;
        Object lock2 = fromAccount.getId() < toAccount.getId() ? toAccount : fromAccount;

        synchronized (lock1) {
            synchronized (lock2) {
                BigDecimal fromResult = fromAccount.getMoney().subtract(amount);
                if (fromResult.signum() == -1) {
                    System.out.println("Not enough money to perform transaction " + id);
                    successful = false;
                } else {
                    fromAccount.setMoney(fromResult);
                    toAccount.setMoney(toAccount.getMoney().add(amount));
                    successful = true;
                    System.out.println("Transaction " + id + " is successful");
                }
                processed = true;
            }
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", amount=" + amount +
                ", processed=" + processed +
                ", successful=" + successful +
                '}';
    }
}
