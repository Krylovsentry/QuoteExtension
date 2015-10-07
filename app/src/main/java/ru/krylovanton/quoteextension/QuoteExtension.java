package ru.krylovanton.quoteextension;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Антон on 05.09.2015.
 */
public class QuoteExtension extends DashClockExtension {
    private static final String TAG = "QuoteExtension";

    public static final String PREF_LANGUAGE = "pref_language";
    boolean isInternetConnection = false ;
    ConnectionDetector cd;
    QuoteTask quoteTask;
    String quote;



    @Override
    protected void onInitialize(boolean isReconnect) {
        super.onInitialize(isReconnect);
        setUpdateWhenScreenOn(true);




    }


    @Override
    protected void onUpdateData(int reason) {



        cd = new ConnectionDetector(getApplicationContext());
        isInternetConnection = cd.ConnectingInternet();
        if (isInternetConnection){

              quoteTask = new QuoteTask();
              quoteTask.execute();
              try {
                     quote = quoteTask.get();
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



    class QuoteTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {
            String exitString = null;

            try {
                String url = "http://api.forismatic.com/api/1.0/?method=getQuote&format=text&lang="+getString(R.string.locale_quote);
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    exitString = response.toString();
                }
                in.close();

            } catch (Exception e){
                exitString = "The man who makes no mistakes does not usually make anything";
                e.printStackTrace();

            }




            return exitString;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}



