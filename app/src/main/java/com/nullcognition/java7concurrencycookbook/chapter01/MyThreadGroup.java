package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

/**
 * Created by ersin on 28/04/15 at 4:36 PM
 */
public class MyThreadGroup extends ThreadGroup {

   public MyThreadGroup(String name){

	  super(name);

   }

   @Override
   public void uncaughtException(Thread t, Throwable e){

	  super.uncaughtException(t, e);
	  Log.e("AndDebug", "The thread %s has thrown an Exception\n" + t.getId());
	  Log.e("AndDebug", "Terminating the rest of the Threads\n");
	  interrupt();
   }

}
