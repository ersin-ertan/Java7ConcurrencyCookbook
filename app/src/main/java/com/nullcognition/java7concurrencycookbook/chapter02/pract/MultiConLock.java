package com.nullcognition.java7concurrencycookbook.chapter02.pract;
// Created by ersin on 13/05/15

import android.util.Log;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MultiConLock{

	public void startThreads(){

		Buffer buffer = new Buffer(10);
		Thread[] threads = new Thread[4];
		threads[3] = new Thread(new Producer(buffer, 100));
		threads[3].start();
		try{Thread.sleep(1_000);} catch(InterruptedException e){e.printStackTrace();}
		// quick fix to ensure buffer hasPending string before consumers start and read the false
		for(int i = 0; i < 3; ++i){
			threads[i] = new Thread(new Consumer(buffer));
			threads[i].start();
		}
	}
}

class Buffer{

	private LinkedList<String> list;
	private final int length;
	private ReentrantLock reentrantLock;
	private Condition hasStrings;
	private Condition hasSpace;
	private volatile boolean hasPendingStrings = true;

	public Buffer(int length){
		this.length = length;
		list = new LinkedList<>();
		reentrantLock = new ReentrantLock(true);
		hasStrings = reentrantLock.newCondition();
		hasSpace = reentrantLock.newCondition();
	}

	private boolean isEmpty(){return list.isEmpty();}

	public void insert(String string){
		reentrantLock.lock();
		try{
			while(list.size() == length){hasSpace.await();}

			list.add(string);
			Log.e("logErr", "added:" + string + ". Buffer size is " + list.size());
			hasStrings.signalAll();

		} catch(InterruptedException e){e.printStackTrace();}
		finally{reentrantLock.unlock();}
	}

	public String remove(){
		String remove = null;
		reentrantLock.lock();
		try{
			while(list.size() == 0 && hasPendingStrings()){hasSpace.await();}

			remove = list.remove();
			Log.e("logErr", "removed:" + remove + ". Buffer size is " + list.size());
			hasSpace.signalAll();

		} catch(InterruptedException e){e.printStackTrace();}
		finally{reentrantLock.unlock();}

		return remove;
	}

	public boolean hasPendingStrings(){return list.size() > 0 || hasPendingStrings;}
	public void setPendingStrings(final boolean pending){ hasPendingStrings = pending;}
}

class Producer implements Runnable{

	String[] strings;
	Buffer buffer;
	int sizeOfInput;
	int index = 0;

	public Producer(Buffer buffer, int size){
		sizeOfInput = size;
		strings = new String[sizeOfInput];
		if(size > 0){ buffer.setPendingStrings(true); }
		this.buffer = buffer;
		makeStrings();
	}

	@Override
	public void run(){
		while(index < sizeOfInput){ buffer.insert(strings[index++]); }
		buffer.setPendingStrings(false);
	}

	private void makeStrings(){
		for(int i = 0; i < sizeOfInput; i++){
			strings[i] = String.valueOf((char) getRandom(33, 126));
			Log.e("logErr", i + ":" + strings[i] + ", ");
		}
	}

	static Random random = new Random(3);
	private static int getRandom(int min, int max){
		return random.nextInt((max - min) + 1) + min;
	}
}

class Consumer implements Runnable{

	Buffer buffer;

	public Consumer(Buffer buffer){
		this.buffer = buffer;
	}

	@Override
	public void run(){ while(buffer.hasPendingStrings()){processString();}}

	private void processString(){
		Log.e("logErr", "removed" + buffer.remove());
		try{Thread.sleep(getRandom(100, 1000));} catch(InterruptedException e){e.printStackTrace();}
	}

	static Random random = new Random(3);
	private static int getRandom(int min, int max){
		return random.nextInt((max - min) + 1) + min;
	}
}
