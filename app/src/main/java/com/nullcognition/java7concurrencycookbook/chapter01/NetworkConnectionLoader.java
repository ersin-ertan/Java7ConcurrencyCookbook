package com.nullcognition.java7concurrencycookbook.chapter01;

/**
 * Created by ersin on 27/04/15 at 3:04 PM
 */

public class NetworkConnectionLoader implements Runnable {

   @Override
   public void run(){

	  long num = 0L;
	  while(num != 20_000){++ num;}
   }
}

