package com.nullcognition.java7concurrencycookbook.chapter02;// Created by ersin on 05/05/15

import android.util.Log;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteL {

    private static Random r = new Random(1);
    private ReadWriteLock rwl;

    private int price1;
    private int price2;

    public ReadWriteL() {
        price1 = 1;
        price2 = 2;
        rwl = new ReentrantReadWriteLock(true); // fair policy will choose the
        // thread in the waiting queue with the longest waiting time
    }

    public int readP1() {
        rwl.readLock().lock();
        int value = price1;
        rwl.readLock().unlock();
        return value;
    }

    public int readP2() {
        rwl.readLock().lock();
        int value = price2;
        rwl.readLock().unlock();
        return value;
    }

    public void writeP1P2(int value1, int value2) {
        rwl.writeLock().lock();
        price1 = value1;
        price2 = value2;
        rwl.writeLock().unlock();
    }

    public static class Reader implements Runnable {
        ReadWriteL priceInfo = new ReadWriteL();

        public Reader(ReadWriteL r) {
            priceInfo = r;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1; i++) {
                Log.e("logErr", "price 1" + Thread.currentThread().getName() + " " + priceInfo.price1);
                Log.e("logErr", "price 2" + Thread.currentThread().getName() + " " + priceInfo.price2);

            }

        }
    }

    public static class Writer implements Runnable {
        ReadWriteL priceInfo = new ReadWriteL();

        public Writer(ReadWriteL w) {
            priceInfo = w;
        }

        @Override
        public void run() {
            Log.e("logErr", "modification of prices");
            priceInfo.writeP1P2(r.nextInt(), r.nextInt());
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
