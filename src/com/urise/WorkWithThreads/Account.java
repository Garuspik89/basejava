package com.urise.WorkWithThreads;

public class Account {
    private int deposite;

    public Account(int deposite) {
        this.deposite = deposite;
    }

    public int getDeposite() {
        return deposite;
    }

    public static void transferDepositeFromAccount1ToAccount2(Account account1, Account account2, int deposite) {
        account1.deposite -= deposite;
        account2.deposite += deposite;
    }
}
