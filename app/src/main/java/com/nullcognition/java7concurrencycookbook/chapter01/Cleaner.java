package com.nullcognition.java7concurrencycookbook.chapter01;

import android.util.Log;

import java.util.Date;
import java.util.Deque;

/**
 * Created by ersin on 28/04/15 at 12:30 PM
 */
public class Cleaner extends Thread {

   Deque<AnEvent> deque;

   public Cleaner(Deque<AnEvent> inDeque){

	  deque = inDeque;
	  setDaemon(true);
   }

   @Override
   public void run(){

	  super.run();
	  while(deque.size() > 0){
		 if(deque.getLast().date.getTime() < (new Date().getTime() - 2_000)){
			clean();
		 }
	  }
   }

   private void clean(){

	  Log.e("THREXA", "Deleting date " + deque.getLast().date.toString() + "\nSize of deque is " + deque.size() + "\nFrom thread " + deque.getLast().string);
	  deque.removeLast();
   }
}
