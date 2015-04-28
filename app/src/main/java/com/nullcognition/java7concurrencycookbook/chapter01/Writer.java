package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

/**
 * Created by ersin on 28/04/15 at 12:30 PM
 */
public class Writer extends Thread {

   Deque<AnEvent> deque;

   public Writer(Deque<AnEvent> inDeque){

	  deque = inDeque;
   }

   @Override
   public void run(){

	  super.run();

	  for(int i = 0; i < 100; i++){
		 AnEvent ae = new AnEvent(new Date(), Thread.currentThread()
													.getName());
		 deque.addFirst(ae);
		 try{ TimeUnit.MILLISECONDS.sleep(100); }
		 catch(InterruptedException e){
			Log.e("THREXA", "Writer error on sleep");
		 }
	  }
   }
}
