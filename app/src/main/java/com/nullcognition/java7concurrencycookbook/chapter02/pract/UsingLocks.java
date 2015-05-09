package com.nullcognition.java7concurrencycookbook.chapter02.pract;// Created by ersin on 09/05/15

import android.util.Log;

import java.util.Date;
import java.util.LinkedList;

public class UsingLocks{

	LinkedList<Integer> a = new LinkedList<>();
	LinkedList<Integer> b = new LinkedList<>();

	UsingLocks(){

		AttributeLock attributeLock = new AttributeLock(a,b);
		Thread[] threads = new Thread[5];

		for(int i = 0; i < 5; i++){
			threads[i] = new Thread(attributeLock);
			threads[i].start();
		}
	}
}

class AttributeLock implements Runnable{

	LinkedList<Integer> a = new LinkedList<>();
	LinkedList<Integer> b = new LinkedList<>();

	public AttributeLock(LinkedList<Integer> aa, LinkedList<Integer> bb){
		a = aa;
		b = bb;
	}

	@Override
	public void run(){

		insertA(1);
		insertA(2);
		removeA();
		insertA(3);
		insertA(4);
		insertB(1);
		removeB();
		insertB(2);
		removeA();
		removeB();
		insertB(3);
		insertB(4);
		Log.e("logErr", Thread.currentThread().getName() + " is done");



	}

	private void insertA(int i){
		synchronized(a){
			try{Thread.sleep(1_000);} catch(InterruptedException e){e.printStackTrace();}
			Log.e("logErr", "InsertA " + new Date());
			a.add(i);
		}
	}

	private void removeA(){
		synchronized(a){
			try{Thread.sleep(1_000);} catch(InterruptedException e){e.printStackTrace();}
			Log.e("logErr", "removeA " + new Date());
			a.removeFirst();
		}
	}

	private void insertB(int i){
		synchronized(b){
			try{Thread.sleep(1_000);} catch(InterruptedException e){e.printStackTrace();}
			Log.e("logErr", "InsertB " + new Date());
			b.add(i);
		}
	}

	private void removeB(){
		synchronized(b){
			try{Thread.sleep(1_000);} catch(InterruptedException e){e.printStackTrace();}
			Log.e("logErr", "removeB " + new Date());
			b.removeFirst();
		}
	}
}
