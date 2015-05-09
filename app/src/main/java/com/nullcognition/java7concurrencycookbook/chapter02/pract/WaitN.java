package com.nullcognition.java7concurrencycookbook.chapter02.pract;// Created by ersin on 08/05/15

import android.util.Log;

import java.util.Date;

public class WaitN{
	WaitN(){
		MyRunnable r = new MyRunnable();
		Thread[] threads = new Thread[10];

		for(int i = 0; i < 4; i++){
			threads[i] = new Thread(r);
			try{Thread.sleep(1_000);} catch(InterruptedException e){e.printStackTrace();}
			threads[i].start();
			// all threads will start before the second round of reporting to make apparent the fact
			// that unsafe only has one value, that of the last constructed date
		}
//
//		Log.e("logErr", "Switching tt to now thread with r");
//
//		tt = new Thread(r);
//
//		try{Thread.sleep(1_000);} catch(InterruptedException e){e.printStackTrace();}
//
//		Thread ttt = new Thread(r); // the new thread with the same old runnable does not reinit the
//		// threadlocal variable
	}
}

class MyRunnable implements Runnable{

	Date unsafe;
	int waiter = 3;

	ThreadLocal<Date> threadLocal = new ThreadLocal<Date>(){
		@Override
		protected Date initialValue(){
			return new Date(); // init at first access
		}
	};

	@Override
	public void run(){
		if(waiter == 0){
			synchronized(this){
				notifyAll(); // anything after the synchronized must complete before the threads are notified
				// verified by putting the next two in here thus the wait happened then the notified threads printed out
			}
				Log.e("logErr", "(Last)Notifier thread has started, will wait and be the print out with the oldest TL-2");
				try{Thread.sleep(2_000);} catch(InterruptedException e){e.printStackTrace();}
		}
		while(waiter != 0){
			firstDate();
		}

//		try{Thread.sleep(3_000);} catch(InterruptedException e){e.printStackTrace();} // works
// instead use wait and notify


		// should match up with the same values but does not
		synchronized(this){ // not important, just testing
			Log.e("logErr", "TL-2" + threadLocal.get().toString()); // showing the time of creation
			Log.e("logErr", "UN-2" + unsafe.toString()); // showing the time of last creation
		}
	}

	public synchronized void firstDate(){
		Log.e("logErr", "TL" + threadLocal.get().toString()); // showing the time of creation
		unsafe = new Date();
		Log.e("logErr", "UN" + unsafe.toString()); // showing the time of last creation
		waiter--;
		try{
			wait();
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		Log.e("logErr", "---Done Waiting: Below for second Date---");
		
	}
}
