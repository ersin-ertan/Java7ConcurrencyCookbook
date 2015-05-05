package com.nullcognition.java7concurrencycookbook;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nullcognition.java7concurrencycookbook.chapter02.SynchMethodAndAccount;


public class Chapter02Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter02);

//        synchMethod();

        int debugPoint = 0;
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
