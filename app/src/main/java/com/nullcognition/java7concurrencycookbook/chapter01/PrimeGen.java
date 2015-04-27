package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

/**
 * Created by ersin on 27/04/15 at 2:35 PM
 */


public class PrimeGen extends Thread {

   @Override
   public void run(){

	  super.run();
	  long number = 1L;
	  while(true){
		 if(isPrime(number)){
			Log.e(getClass().getSimpleName(), number + " is prime");
		 }
		 if(isInterrupted()){
			Log.e(getClass().getSimpleName(), "is interrupted");
			return;
		 }
		 ++ number;
	  }
   }

   private boolean isPrime(long num){

	  if(num <= 2){ return true;}
	  for(long i = 2; i < num; i++){
		 if((num % i) == 0){
			return false;
		 }
	  }
	  return true;
   }
}

