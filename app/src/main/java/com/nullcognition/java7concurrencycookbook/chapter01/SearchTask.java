package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

import com.nullcognition.java7concurrencycookbook.MainActivity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SearchTask implements Runnable {

   @Override
   public void run(){

	  try{
		 task();
		 MainActivity.threadName = Thread.currentThread()
										 .getName();
		 Log.e("THREXA", Thread.currentThread()
							   .getName() + " succeded");

	  }
	  catch(InterruptedException e){
		 Log.e("THREXA", Thread.currentThread()
							   .getName() + " Interrupted");
	  }


   }

   public void task() throws InterruptedException{

	  Log.e("THREXA", Thread.currentThread()
							.getName() + " starting task");

	  int i = new Random().nextInt((5 - 1 + 1) + 1); // max - min + 1 ) + min
	  TimeUnit.SECONDS.sleep(i);
   }
}
