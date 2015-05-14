package com.nullcognition.java7concurrencycookbook.chapter02.pract;// Created by ersin on 08/05/15

public class Pract02{

	public void tl(){
		new TLCode().start();
		// thread local is for the runnable to manipulate values per thread instance but via internal
		// modification, due to my inability to call commands from the main thread to containing thread
		// to runnable to make changes.
	}

	public void wn(){
		new WN();
	}

	public void waitN(){
		new WaitN();
	}

	public void usingLocks(){new UsingLocks();}

	public void reentrantLocks(){new Reentr(); }

	public void rwlock(){ new RWLock().startThreads();}

	public void multiConLock(){new MultiConLock().startThreads();}
}
