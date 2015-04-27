package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

/**
 * Created by ersin on 27/04/15 at 2:34 PM
 */


public class ComplexOrRecursiveTask extends Thread {

   @Override
   public void run(){

	  long num = 1L;
	  super.run();
	  while(! isInterrupted()){

		 try{ mod1000(num);}
		 catch(InterruptedException e){
			Log.e(getClass().getSimpleName(), Thread.currentThread()
													.getName() + " is interrupted");
		 }

	  }
   }

   private void mod1000(long num) throws InterruptedException{

	  while((num % 1000) != 0){++ num;}
	  ++ num;
	  mod100(num);

	  if(Thread.interrupted()){
		 Log.e(getClass().getSimpleName(), Thread.currentThread()
												 .getName() + " throws in mod 1000");
		 throw new InterruptedException();
	  }

   }

   private void mod100(long num) throws InterruptedException{

	  while((num % 100) != 0){++ num;}
	  ++ num;
	  mod1000(num);
	  if(Thread.interrupted()){
		 Log.e(getClass().getSimpleName(), Thread.currentThread()
												 .getName() + " throws in mod _100");
		 throw new InterruptedException();
	  }
   }
} // bug, does not post logs or post crash report from app
