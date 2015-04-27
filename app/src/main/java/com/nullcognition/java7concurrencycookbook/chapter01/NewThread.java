package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

/**
 * Created by ersin on 27/04/15 at 2:34 PM
 */


public class NewThread implements Runnable {

   int i;

   public NewThread(int inI){ i = inI;}

   @Override
   public void run(){

	  logCurrentThread();


   }

   private void logCurrentThread(){

	  Log.e(getClass().getSimpleName(), Thread.currentThread()
											  .getName() + " " + i);
   }
}
