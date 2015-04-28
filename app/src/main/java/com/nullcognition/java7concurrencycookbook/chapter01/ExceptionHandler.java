package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

/**
 * Created by ersin on 28/04/15 at 1:55 PM
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

   @Override
   public void uncaughtException(Thread thread, Throwable ex){

	  Log.e("THREXA", "exception " + thread.getId() + "\n" + ex.getClass()
															   .getName() + "\n" + ex.getMessage() + "\n" + thread.getState());
   }

   public static class Task implements Runnable {

      @Override
      public void run(){

         int i = Integer.parseInt("not an int");
      }
   }
}

