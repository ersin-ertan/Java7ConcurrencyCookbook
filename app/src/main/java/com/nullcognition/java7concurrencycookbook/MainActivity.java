package com.nullcognition.java7concurrencycookbook;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nullcognition.java7concurrencycookbook.chapter01.AnEvent;
import com.nullcognition.java7concurrencycookbook.chapter01.Cleaner;
import com.nullcognition.java7concurrencycookbook.chapter01.ComplexOrRecursiveTask;
import com.nullcognition.java7concurrencycookbook.chapter01.DataSourceLoader;
import com.nullcognition.java7concurrencycookbook.chapter01.FileClock;
import com.nullcognition.java7concurrencycookbook.chapter01.NetworkConnectionLoader;
import com.nullcognition.java7concurrencycookbook.chapter01.NewThread;
import com.nullcognition.java7concurrencycookbook.chapter01.PrimeGen;
import com.nullcognition.java7concurrencycookbook.chapter01.Writer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

// Logcat stopped working, required restart. Not sure why it happened.

public class MainActivity extends ActionBarActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState){

	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);

//	  simpleThread();
//	  threadInfo();
//	  interruptedThread();
//	  complexThreadInterrupt();
//	  sleepResumeThread();
//	  finalizationThread();
	  daemonThread();

   }

   private void daemonThread(){

	  Deque<AnEvent> deque = new ArrayDeque<>();

	  for(int i = 0; i < 3; i++){
		 new Writer(deque).start();
	  }
	  try{
		 TimeUnit.SECONDS.sleep(1);
	  }
	  catch(InterruptedException e){
		 Log.e("THREXA", "Sleep in main error");
	  }
	  new Cleaner(deque).start();
	  // 3 writers - 1 cleaner
	  int i = 0;
	  int e = 0;
	  int u = 0;


   }

   private void finalizationThread(){

	  DataSourceLoader dsl = new DataSourceLoader();
	  Thread thread = new Thread(dsl, "dataSourceThread");

	  NetworkConnectionLoader ncl = new NetworkConnectionLoader();
	  Thread thread2 = new Thread(ncl, "networkConnectionLoader");

	  thread.start();
	  thread2.start();

	  try{
		 thread.join(); // will only return when both of these classes are done their Thread.run() methods
		 thread2.join(); // may have a timeout
	  }
	  catch(InterruptedException e){Log.e(getClass().getSimpleName(), "interrupts");}

	  Log.e(getClass().getSimpleName(), "Loaded DataSL & NetworkCL");
   }

   private void sleepResumeThread(){

	  Thread fileClock = new FileClock();
	  fileClock.start();

	  try{
		 TimeUnit.SECONDS.sleep(5);
	  }
	  catch(InterruptedException e){
		 Log.e(getClass().getSimpleName(), "Interrupted from main method sleepResume");
	  }

	  fileClock.interrupt();

   }

   private void complexThreadInterrupt(){

	  Thread complex = new ComplexOrRecursiveTask();
	  complex.start();

	  try{TimeUnit.SECONDS.sleep(1);}
	  catch(InterruptedException e){
		 Log.e(getClass().getSimpleName(), Thread.currentThread()
												 .getName() + " interrupted");
	  }
	  complex.interrupt();

   }

   private void interruptedThread(){

	  Thread primeGen = new PrimeGen();
	  primeGen.start();

	  try{ Thread.sleep(2500);} catch(InterruptedException e){e.printStackTrace();}
	  primeGen.interrupt();

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
			   Log.e(getClass().getSimpleName(), threads[i].getState()
														   .toString());
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
