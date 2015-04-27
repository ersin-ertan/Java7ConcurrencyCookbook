package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by ersin on 27/04/15 at 2:34 PM
 */
public class FileClock extends Thread {

   @Override
   public void run(){

	  super.run();
	  for(int i = 0; i < 10; i++){
		 Log.e(getClass().getSimpleName(), new Date().toString());
		 try{TimeUnit.SECONDS.sleep(1);}
		 catch(InterruptedException e){
			Log.e(getClass().getSimpleName(), "interrupted");
		 }
	  }
   }
}
