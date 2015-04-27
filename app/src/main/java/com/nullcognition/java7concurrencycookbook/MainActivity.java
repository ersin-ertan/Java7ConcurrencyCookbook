package com.nullcognition.java7concurrencycookbook;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

// Logcat stopped working, required restart. Not sure why it happened.

public class MainActivity extends ActionBarActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState){

	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);

	  // simpleThread();
	  Log.e(getClass().getSimpleName(), "starting thread info method");
	  threadInfo();
   }

   private void threadInfo(){

	  Log.e(getClass().getSimpleName(), "started thread info method");


	  Thread[] threads = new Thread[10];
	  Thread.State[] states = new Thread.State[10];

	  for(int i = 0; i < 10; i++){

		 threads[i] = new Thread(new NewThread(i));

		 if((i % 2) == 0){
			threads[i].setPriority(Thread.MAX_PRIORITY);
		 }
		 else{ threads[i].setPriority(Thread.MIN_PRIORITY); }

		 states[i] = threads[i].getState();
		 threads[i].setName("Thread @ " + i + " state is " + states[i]);
		 Log.e(getClass().getSimpleName(), threads[i].getName());

	  }

	  for(int i = 0; i < 10; i++){
		 threads[i].start();
	  }

	  boolean finished = false;

	  do{

		 finished = true;

		 for(int i = 0; i < 10; i++){

			Thread.State state = threads[i].getState();

			if(state != states[i]){
			   states[i] = state;
			   Log.e(getClass().getSimpleName(), threads[i].getState().toString());
			}

			finished = finished && (states[i] == Thread.State.TERMINATED);
		 }

	  }
	  while(! finished);
   }

   private void simpleThread(){

	  for(int i = 0; i < 5; i++){
		 new Thread(new NewThread(i)).start();
	  }

   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu){

	  getMenuInflater().inflate(R.menu.menu_main, menu);
	  return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item){

	  int id = item.getItemId();

	  if(id == R.id.action_settings){
		 return true;
	  }

	  return super.onOptionsItemSelected(item);
   }
}

class NewThread implements Runnable {

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
