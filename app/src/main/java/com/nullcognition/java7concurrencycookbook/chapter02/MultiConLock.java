package com.nullcognition.java7concurrencycookbook.chapter02;// Created by ersin on 05/05/15

import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MultiConLock {
    private String[] content;
    private int index;

    public MultiConLock(int size, int length) {
        content = new String[size];
        for (int i = 0; i < size; i++) {
            StringBuilder buffer = new StringBuilder(length);
            for (int j = 0; j < length; j++) {
                int indice = (int) (Math.random() * 255);
                buffer.append((char) indice);
            }
            content[i] = buffer.toString();
        }
        index = 0;
    }

    public boolean hasMoreLines() {
        return index < content.length;
    }

    public String getLine() {
        if (this.hasMoreLines()) {
            return content[index++];
        }
        return null;
    }

    public static class Buffer {
        private LinkedList<String> buffer;
        private int maxSize;
        private ReentrantLock lock;
        private Condition lines;
        private Condition space;
        private boolean pendingLines;

        public Buffer(int maxSize) {
            this.maxSize = maxSize;
            buffer = new LinkedList<>();
            lock = new ReentrantLock();
            lines = lock.newCondition();
            space = lock.newCondition();
            pendingLines = true;
        }

        public void insert(String line) {
            lock.lock();
            try {
                while (buffer.size() == maxSize) {
                    space.await();
                }
                buffer.offer(line);
                Log.e("logErr", "inserted line " + Thread.currentThread().getName() + " " + buffer.size());
                lines.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public String get() {
            String line = null;
            lock.lock();
            try {
                while ((buffer.size() == 0) && (hasPendingLines())) {
                    lines.await();
                }
                if (hasPendingLines()) {
                    line = buffer.poll();
                    Log.e("logErr", "Read line" + Thread.currentThread().getName() + " " + buffer.size());
                    space.signalAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return line;
        }

        public void setPendingLines(boolean pl) {
            pendingLines = pl;
        }

        public boolean hasPendingLines() {
            return pendingLines || buffer.size() > 0;
        }
    }
}
