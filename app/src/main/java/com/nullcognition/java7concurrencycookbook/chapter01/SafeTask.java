package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SafeTask implements Runnable {

   private static ThreadLocal<Date> startDate = new ThreadLocal<>();

   protected Date initialValue(){ return new Date();}


   @Override
   public void run(){

	  Log.e("THREXA", ("Starting Thread: \n" + Thread.currentThread()
																	   .getId() + " " + startDate.get()));
	  try{
		 TimeUnit.SECONDS.sleep((int)Math.rint(Math.random() * 10));
	  }
	  catch(InterruptedException e){
		 e.printStackTrace();
	  }
	  Log.e("THREXA", ("Thread Finished: \n" + Thread.currentThread()
																	   .getId() + " " + startDate.get()));
   }
}
