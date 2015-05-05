package com.nullcognition.java7concurrencycookbook;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nullcognition.java7concurrencycookbook.chapter02.AttrbInSync;
import com.nullcognition.java7concurrencycookbook.chapter02.ConditionsSync;
import com.nullcognition.java7concurrencycookbook.chapter02.ReadWriteL;
import com.nullcognition.java7concurrencycookbook.chapter02.SyncLock;
import com.nullcognition.java7concurrencycookbook.chapter02.SynchMethodAndAccount;


public class Chapter02Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter02);

//        synchMethod();
//        attrbSync();
//        conditionSync();
//        syncLock();
        readWriteLock();


        int i = 0; // debug point
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
