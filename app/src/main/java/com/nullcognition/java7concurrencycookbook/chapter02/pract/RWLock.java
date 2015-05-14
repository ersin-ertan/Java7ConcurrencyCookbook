package com.nullcognition.java7concurrencycookbook.chapter02.pract;// Created by ersin on 09/05/15

import android.util.Log;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLock{

	RWables rWables = new RWables();

	public void startThreads(){
		Writer writer = new Writer(rWables);
		Thread tWriter = new Thread(writer);
		tWriter.start();

		Thread[] threads = new Thread[5];
		Reader[] readers = new Reader[5];
		for(int i = 0; i < 5; ++i){
			readers[i] = new Reader(rWables);
			threads[i] = new Thread(readers[i]);
			try{ Thread.sleep(500);} catch(InterruptedException e){ e.printStackTrace();}
		}
		for(int i = 0; i < 5; i++){ threads[i].start();}
	}
}

class RWables{
	int one = 0;
	int two = 0;
	ReadWriteLock rwl = new ReentrantReadWriteLock(true);

	public int getOne(){
		rwl.readLock().lock();
		int value = one;
		rwl.readLock().unlock();
		return value;
	}

	public int getTwo(){
		rwl.readLock().lock();
		int value = two;
		rwl.readLock().unlock();
		return value;
	}

	public void setBoth(final int one, final int two){
		rwl.writeLock().lock();
		this.one = one;
		this.two = two;
		rwl.writeLock().unlock();
	}
}

class Reader implements Runnable{

	private Date date;
	private final RWables rWables;
	public Reader(RWables rw){
		rWables = rw;
		date = new Date();
	}

	@Override
	public void run(){
		for(int i = 0; i < 5; i++){readValue();}
	}

	private void readValue(){
		Log.e("logErr", "waiting since" + date.toString());
		// Emulating the fairness allowing you to see when it was created thus the older readable will go first and have an older date
		// Date is only valid for the first call, but call order will be preserved with fair lock
		for(int i = 0; i < 5; i++){Log.e("logErr", "one " + rWables.getOne());}
		for(int i = 0; i < 5; i++){Log.e("logErr", "two " + rWables.getTwo());}

	}
}

class Writer implements Runnable{

	private final RWables rWables;
	public Writer(RWables rw){rWables = rw;}

	@Override
	public void run(){
		for(int i = 0; i < 5; i++){writeValue();}
	}

	private void writeValue(){
		for(int i = 0; i < 5; i++){
			Log.e("logErr", "setting ");
			rWables.setBoth(getRandom(0, 10), getRandom(0, 10));
		}
	}

	private static int getRandom(int min, int max){
		Random random = new Random(3);
		return random.nextInt((max - min) + 1) + min;
	}
}
