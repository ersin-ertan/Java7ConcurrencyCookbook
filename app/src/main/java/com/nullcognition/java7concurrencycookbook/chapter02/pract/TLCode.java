package com.nullcognition.java7concurrencycookbook.chapter02.pract;
// Created by ersin on 08/05/15

import android.util.Log;

public class TLCode{

	Run run = new Run();
	Thread t1 = new Thread(run);
	Thread t2 = new Thread(run);

	public void start() {
		t1.start();
		t2.start();

		try{
			Thread.sleep(1000);
		} catch(InterruptedException e){
			e.printStackTrace();
		}

		run.setIsRunning(Boolean.FALSE); // creating a thread will run the runnable from main...
		// don't know why
	}


}


class Run implements Runnable{

	private ThreadLocal<Boolean> isRunning = new ThreadLocal<Boolean>(){
		@Override
		protected Boolean initialValue(){
			return Boolean.TRUE;
		}
	};
	private ThreadLocal<Integer> myInt = new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue(){
			return 0;
		}
	};

	@Override
	public void run(){
		while(isRunning.get()){
			try{
				Thread.sleep(1000);
			} catch(InterruptedException e){
				e.printStackTrace();
			}
			myInt.set(myInt.get() + 1);
			Log.e("logErr", Thread.currentThread().getName() + " int count:" + myInt.get());

		}
	}

//	public boolean getIsRunning(){return isRunning.get();}

//	public int getMyInt(){return myInt.get();}

   public void setIsRunning(Boolean b){isRunning.set(b);} // what is the point of having the method
	//if there is no way to access the variable from a thread specific way, it is either from main
	// and directly from the runnable not the housing thread, tried to create a thread to house then
	// chain delegate calls from thread to runnable but method is seen from main thread...
//
//	public void setMyInt(Integer i){myInt.set(i);}


}
