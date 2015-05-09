package com.nullcognition.java7concurrencycookbook.chapter02.pract;// Created by ersin on 09/05/15

import android.util.Log;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Reentr{

	public Reentr(){
		LinkedList<Integer> list = new LinkedList<>();
		Work runnable = new Work(list);
		Log.e("logErr", "-----Start of Reentr-----");

		Thread[] threads = new Thread[5];
		for(int i = 0; i < 5; ++i){
			threads[i] = new Thread(runnable);
			try{Thread.sleep(100);} catch(InterruptedException e){e.printStackTrace();}
			threads[i].start();
		}
	} // note that threads that are blocked from doing an operation are following an order of execution and queueing
	// even with the staggered start, once all threads have started the order of operation is preserved
	// this is because of the Lock lock = new ReenttrantLock(true); which toggles the fair scheduling policy

	// if the scheduling policy is set to false, then one thread will continue its run till completion
	// for the lock is reentrant allowing thread who has the lock to reacquire the lock and do its next operation,
	// would like to know more about how this is enforced, is it till the run is done and statically checked
	// due to the reentrant lock
}

class Work implements Runnable{

	public Work(LinkedList<Integer> l){linkedList = l;}

	Lock lock = new ReentrantLock(false);
	LinkedList<Integer> linkedList;

	public void add(){
		lock.lock();
		try{linkedList.add(1);} finally{
			Log.e("logErr", "Add " + Thread.currentThread().getName() + new Date());
			try{Thread.sleep(100);} catch(InterruptedException e){e.printStackTrace();}
			lock.unlock();
		}
	}

	public void remove(){
		lock.lock();
		try{linkedList.removeFirst();} finally{
			Log.e("logErr", "rem " + Thread.currentThread().getName() + new Date());
			try{Thread.sleep(100);} catch(InterruptedException e){e.printStackTrace();}
			lock.unlock();
		}
	}

	@Override
	public void run(){

		add();
		add();
		add();
		add();
		remove();
		remove();
		add();
		add();
		add();
		add();
		remove();
	}
}

