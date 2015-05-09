package com.nullcognition.java7concurrencycookbook;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nullcognition.java7concurrencycookbook.chapter02.AttrbInSync;
import com.nullcognition.java7concurrencycookbook.chapter02.ConditionsSync;
import com.nullcognition.java7concurrencycookbook.chapter02.MultiConLock;
import com.nullcognition.java7concurrencycookbook.chapter02.ReadWriteL;
import com.nullcognition.java7concurrencycookbook.chapter02.SyncLock;
import com.nullcognition.java7concurrencycookbook.chapter02.SynchMethodAndAccount;
import com.nullcognition.java7concurrencycookbook.chapter02.pract.Pract02;
import com.nullcognition.java7concurrencycookbook.chapter02.pract.TLCode;

import java.util.Random;


public class Chapter02Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter02);

//        synchMethod();
//        attrbSync();
//        conditionSync();
//        syncLock();
//        readWriteLock();
//        multiConLock();
	    pract02();


        int i = 0; // debug point
    }

	private void pract02(){
//		new Pract02().tl();
//        new Pract02().wn();
		new Pract02().waitN();
	}

	private void multiConLock() {
        class Consumer implements Runnable {
            private MultiConLock.Buffer buffer;

            public Consumer(MultiConLock.Buffer b) {
                buffer = b;
            }

            @Override
            public void run() {
                while (buffer.hasPendingLines()) {
                    String line = buffer.get();
                    processLine(line);
                }
            }

            private void processLine(String line) {
                try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        class Producer implements Runnable {
            private MultiConLock mock;
            private MultiConLock.Buffer buffer;

            public Producer(MultiConLock mcl, MultiConLock.Buffer b) {
                mock = mcl;
                buffer = b;
            }

            @Override
            public void run() {
                buffer.setPendingLines(true);
                while (mock.hasMoreLines()) {
                    String line = mock.getLine();
                    buffer.insert(line);
                }
                buffer.setPendingLines(false);
            }
        }

        MultiConLock mock = new MultiConLock(100, 10);
        MultiConLock.Buffer buffer = new MultiConLock.Buffer(20);

        Producer p = new Producer(mock, buffer);
        Thread pt = new Thread(p, "p");

        Consumer[] c = new Consumer[3];
        Thread[] ct = new Thread[3];

        for (int i = 0; i < 3; i++) {
            c[i] = new Consumer(buffer);
            ct[i] = new Thread(c[i], "consumer@" + i);
        }

        pt.start();
        for (int i = 0; i < 3; i++) {
            ct[i].start();
        }
    }

    private void readWriteLock() {
        ReadWriteL priceInfo = new ReadWriteL();
        ReadWriteL.Reader[] r = new ReadWriteL.Reader[5];

        Thread[] rt = new Thread[5];

        for (int i = 0; i < 5; i++) {
            r[i] = new ReadWriteL.Reader(priceInfo);
            rt[i] = new Thread(r[i]);
        }

        ReadWriteL.Writer writer = new ReadWriteL.Writer(priceInfo);
        Thread wt = new Thread(writer);

        for (int i = 0; i < 5; i++) {
            rt[i].start();
        }
        wt.start();

    }

    private void syncLock() {
        SyncLock printQueue = new SyncLock();
        Thread[] thread = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new SyncLock.Job(printQueue));
        }
        for (int i = 0; i < 10; ++i) {
            thread[i].start();
        }

    }

    private void conditionSync() {
        ConditionsSync storage = new ConditionsSync();
        ConditionsSync.Producer p = new ConditionsSync.Producer(storage);
        ConditionsSync.Consumer c = new ConditionsSync.Consumer(storage);
        Thread tp = new Thread(p);
        Thread tc = new Thread(c);
        tp.start();
        tc.start();
        try {
            tp.join();
            tc.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void attrbSync() {
        AttrbInSync.Cinema cinema = new AttrbInSync.Cinema();

        AttrbInSync ticketOffice1 = new AttrbInSync(cinema);
        AttrbInSync ticketOffice2 = new AttrbInSync(cinema);

        Thread t1 = new Thread(ticketOffice1);
        Thread t2 = new Thread(ticketOffice2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.e("attr", "cinema 1 " + cinema.getVacantC1());
            Log.e("attr", "cinema 2 " + cinema.getVacantC2());
        }

    }

    private void synchMethod() {
        SynchMethodAndAccount synchMethod = new SynchMethodAndAccount();
        synchMethod.balance = 1000;

        Thread company = new Thread(SynchMethodAndAccount.newCompany(synchMethod));
        Thread bank = new Thread(SynchMethodAndAccount.newBank(synchMethod));

        Log.e("synch", "Start value is " + String.valueOf(synchMethod.balance));

        company.start();
        bank.start();

        try {
            company.join();
            bank.join();
            Log.e("synch", "Final value is " + synchMethod.balance);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chapter02, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
