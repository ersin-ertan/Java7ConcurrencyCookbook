package com.nullcognition.java7concurrencycookbook.chapter02.pract;// Created by ersin on 15/05/15

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MCL{

	public void startMCL(){
		MCLBuffer buffer = new MCLBuffer();

		Thread[] threads = new Thread[3];
		MCLReader[] runnable = new MCLReader[3];
		for(int i = 0; i < 3; ++i){
			runnable[i] = new MCLReader(buffer);
			threads[i] = new Thread(runnable[i]);
			threads[i].start();
		}

		MCLWriter writerRunnable = new MCLWriter(buffer);
		Thread writerThread = new Thread(writerRunnable);
		writerThread.start();
	}
}

class MCLWriter implements Runnable{

	MCLBuffer buffer;
	public MCLWriter(MCLBuffer buffer){this.buffer = buffer;}

	@Override
	public void run(){
		buffer.modify(0, 0);
		buffer.modify(1, 1);
		buffer.modify(2, 2);
		buffer.modify(3, 3);
		buffer.modify(4, 4);
		buffer.modify(5, 5);
		buffer.modify(6, 6);
		buffer.modify(7, 7);
	}
}

class MCLReader implements Runnable{

	MCLBuffer buffer;
	public MCLReader(MCLBuffer buffer){this.buffer = buffer;}

	@Override
	public void run(){
		buffer.peekFirst();
		buffer.peekFirst();
		buffer.peekFirst();
		buffer.peekFirst();
		buffer.peekLast();
		buffer.peekLast();
		buffer.peekLast();
		buffer.peekLast();
		buffer.peekLast();
	}
}

class MCLBuffer{

	LinkedList<Integer> list;
	ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
	Condition readCondition = readWriteLock.readLock().newCondition();
	Condition writeCondition = readWriteLock.writeLock().newCondition();

	MCLBuffer(){
		list = new LinkedList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
	}

	public Integer peekFirst(){
		readWriteLock.readLock().lock();
		while(list.isEmpty()){
			try{readCondition.await();}
			catch(InterruptedException e){e.printStackTrace();}
		}
		int myInt = list.peekFirst();
		readWriteLock.readLock().unlock();
		readCondition.signalAll();
		return myInt;
	}
	public Integer peekLast(){
		readWriteLock.readLock().lock();
		while(list.isEmpty()){
			try{readCondition.await();}
			catch(InterruptedException e){e.printStackTrace();}
		}
		int myInt = list.peekLast();
		readCondition.signalAll();
		readWriteLock.readLock().unlock();
		return myInt;
	}
	public void modify(int position, int value){
		readWriteLock.writeLock().lock();
		int myInt;
		try{myInt = list.get(position);} // position does not exist
		catch(Exception e){
			readWriteLock.writeLock().unlock();
			writeCondition.signalAll();
			return;
		}

		myInt = list.set(position, value);
		readWriteLock.writeLock().unlock();
		writeCondition.signalAll();
	}

}
