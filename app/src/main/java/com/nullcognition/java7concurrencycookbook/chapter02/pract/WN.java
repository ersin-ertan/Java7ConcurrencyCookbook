package com.nullcognition.java7concurrencycookbook.chapter02.pract;// Created by ersin on 08/05/15

import android.util.Log;

import java.util.Date;
import java.util.LinkedList;

public class WN{

	EventStorage storage = new EventStorage();
	RunP runP = new RunP(storage);
	RunC runC = new RunC(storage);

	Thread t = new Thread(runP);
	Thread tt = new Thread(runC);

	public WN(){
		t.start();
		tt.start();


		// runWN.notifyAll(); // does not work, must be called from within itself
	}


}

class RunP implements Runnable{
	RunP(EventStorage s){storage = s;}

	EventStorage storage;

	@Override
	public void run(){

		storage.get();
		Log.e("logErr", Thread.currentThread().getName() + " get ");
	}
}

class RunC implements Runnable{
	RunC(EventStorage s){storage = s;}

	EventStorage storage;

	@Override
	public void run(){

		storage.set();
		Log.e("logErr", Thread.currentThread().getName() + " set ");
	}

}

class EventStorage{
	int maxSize;
	LinkedList<Date> storage;

	public EventStorage(){
		maxSize = 10;
		storage = new LinkedList<>();
	}

	public synchronized void set(){
		while(storage.size() == maxSize){
			try{
				wait();
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		storage.offer(new Date());
		System.out.printf("Set: %d", storage.size());
		notifyAll();
	}

	public synchronized void get(){
		while(storage.size() == 0){
			try{
				wait();
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		System.out.printf("Get: %d: %s", storage.size(), ((LinkedList<?>) storage).poll());
		notifyAll();
	}
}

