package com.nullcognition.java7concurrencycookbook.chapter02;// Created by ersin on 05/05/15

import android.util.Log;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ConditionsSync {
    private int maxSize = 0;
    private List<Date> storage;

    public ConditionsSync() {
        maxSize = 10;
        storage = new LinkedList<>();
    }

    public synchronized void set() {
        while (storage.size() == maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.add(new Date());
        Log.e("logErr", "set" + storage.size());

        notifyAll();
    }

    public synchronized void get() {
        while (storage.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.e("logErr", "get" + storage.size() + ((LinkedList<?>) storage).poll());

        notifyAll();
    }

    public static class Producer implements Runnable {

        private ConditionsSync storage;

        public Producer(ConditionsSync str) {
            storage = str;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                storage.set();
            }
        }
    }

    public static class Consumer implements Runnable {
        private ConditionsSync storage;

        public Consumer(ConditionsSync str) {
            storage = str;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                storage.get();
            }
        }
    }

}
