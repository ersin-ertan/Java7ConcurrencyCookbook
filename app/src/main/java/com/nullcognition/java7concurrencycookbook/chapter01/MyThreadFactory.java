package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ersin on 28/04/15 at 4:53 PM
 */
public class MyThreadFactory { // implements ThreadFactory { // not available

   private int          counter;
   private String       name;
   private List<String> stats;

   public MyThreadFactory(String name){

	  counter = 0;
	  this.name = name;
	  stats = new ArrayList<String>();
   }

   public Thread newThread(Runnable inRunnable){

	  Thread thread = new Thread(inRunnable, name + "-Thread_" + counter);
	  ++ counter;
	  stats.add("Created thread " + thread.getId() + thread.getName() + new Date());
	  return thread;
   }

   public String getStats(){

	  StringBuffer buffer = new StringBuffer();
	  Iterator<String> it = stats.iterator();
	  while(it.hasNext()){
		 buffer.append(it.next());
		 buffer.append("\n");
	  }
	  return buffer.toString();
   }

   public static class Task implements Runnable {


	  @Override
	  public void run(){

		 try{TimeUnit.SECONDS.sleep(1);}
		 catch(InterruptedException e){
			Log.e("AndDebug", "Task Sleep error");
		 }
	  }
   }

}
