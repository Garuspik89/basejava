package com.urise.WorkWithThreads;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {


    private static final Lock LOCK1 = new ReentrantLock();
    private static final Lock LOCK2 = new ReentrantLock();


    public static void main(String[] args) throws InterruptedException {

        Account account1 = new Account(10000);
        Account account2 = new Account(10000);
        Random  random = new Random();

        Thread thread1 = new Thread(() -> {
            synchronized (DeadLock.class) {
                for (int i = 0; i < 1000; i++) {
                    try {
                        DeadLock.class.wait();
                        Account.transferDepositeFromAccount1ToAccount2(account2, account1, random.nextInt(100));
                        DeadLock.class.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (DeadLock.class) {
                    for (int i = 0; i < 1000; i++) {
                        try {
                            DeadLock.class.wait();
                            Account.transferDepositeFromAccount1ToAccount2(account2, account1, random.nextInt(100));
                            DeadLock.class.notify();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        });


        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Deposite of account 1 = " + account1.getDeposite());
        System.out.println("Deposite of account 2 = " + account2.getDeposite());

    }

}
