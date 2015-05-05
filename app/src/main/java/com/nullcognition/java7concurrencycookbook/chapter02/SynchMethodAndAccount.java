package com.nullcognition.java7concurrencycookbook.chapter02;// Created by ersin on 04/05/15

import android.util.Log;

public class SynchMethodAndAccount implements Runnable {

    public int balance = 0;


    public synchronized void addAmount(int i) {
        int temp = balance;
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        temp += i;
        balance = temp;
        Log.e("synch", String.valueOf(balance)+ "ADD");
    }

    public synchronized void subAmount(int i) {
        int temp = balance;
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        temp -= i;
        balance = temp;
        Log.e("synch", String.valueOf(balance)+"SUB");
    }

    public static Runnable newBank(SynchMethodAndAccount ac) {
        return new Runnable() {
            private SynchMethodAndAccount thAccount;

            public Runnable inputAccount(SynchMethodAndAccount account) {
                this.thAccount = account;
                return this; // returning this with an anonymous inner class to use the initialization
                // instead of a constructor
            }

            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    thAccount.subAmount(1000);
                }
            }
        }.inputAccount(ac);
    }

    public static Runnable newCompany(SynchMethodAndAccount ac) {
        return new Runnable() {
            private SynchMethodAndAccount thAccount;

            public Runnable inputAccount(SynchMethodAndAccount account) {
                thAccount = account;
                return this;
            }

            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    thAccount.addAmount(1000);
                }
            }
        }.inputAccount(ac);
    }

    @Override
    public void run() {
    }
}
