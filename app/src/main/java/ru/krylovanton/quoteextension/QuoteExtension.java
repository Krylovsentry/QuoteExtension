package ru.krylovanton.quoteextension;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import java.util.concurrent.ExecutionException;

/**
 * Created by Антон on 05.09.2015.
 */
public class QuoteExtension extends DashClockExtension {
   private static final String TAG = "QuoteExtension";
   boolean isInternetConnection = false ;
   ConnectionDetector cd;
   MyTask myTask;
   String quote;


    @Override
    protected void onInitialize(boolean isReconnect) {
        super.onInitialize(isReconnect);
        setUpdateWhenScreenOn(true);




    }


    @Override
    protected void onUpdateData(int reason) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetConnection = cd.ConnectingInternet();
        if (isInternetConnection){
        myTask = new MyTask();
        myTask.execute();
        try {
            quote = myTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        }
        // Publish the extension data update.
        publishUpdate(new ExtensionData()
                .visible(true)
                .icon(R.drawable.ic_extension)
                .expandedTitle("Quote")
                .expandedBody(quote));



    }
    }



