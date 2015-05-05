package com.nullcognition.java7concurrencycookbook.chapter02;// Created by ersin on 05/05/15

import android.util.Log;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncLock {
    private final Lock queueLock = new ReentrantLock();

    public void printJob(Object doc) {
        queueLock.lock();
        try {

            int duration = (new Random().nextInt(10 - 5)+ 1) + 5;
            Log.e("logErr", Thread.currentThread() + " printing job with duration of" + duration );
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
            Log.e("logErr", "Unlocked");
            
        }
    }

    public static class Job implements Runnable {
        SyncLock printQueue;

        public Job(SyncLock pq) {
            printQueue = pq;
        }

        @Override
        public void run() {
            Log.e("logErr", "printing doc" + Thread.currentThread().getName());
            printQueue.printJob(new Object());
        }
    }
}
