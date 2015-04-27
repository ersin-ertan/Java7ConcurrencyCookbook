package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by ersin on 27/04/15 at 2:58 PM
 */
public class DataSourceLoader implements Runnable {

   @Override
   public void run(){

	  Log.e(getClass().getSimpleName(), "starting initialization");
	  try{
		 TimeUnit.SECONDS.sleep(2);
	  }
	  catch(InterruptedException e){
		 Log.e(getClass().getSimpleName(), "dataSL sleep catch");
	  }
	  Log.e(getClass().getSimpleName(), "Done init");

   }
}

