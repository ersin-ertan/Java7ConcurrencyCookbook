package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

import java.util.Random;

/**
 * Created by ersin on 28/04/15 at 4:46 PM
 */

public class Task implements Runnable {


   @Override
   public void run(){

	  int result;
	  Random random = new Random(Thread.currentThread()
									   .getId());
	  while(true){
		 result = 1000 / ((int)(random.nextDouble() * 1000));
		 Log.e("AndDebug", Thread.currentThread()
								 .getId() + " " + result);
		 if(Thread.currentThread()
				  .isInterrupted()){
			Log.e("AndDebug", Thread.currentThread()
									.getId() + " Interrupted");
			return;
		 }
	  }
   }
}
