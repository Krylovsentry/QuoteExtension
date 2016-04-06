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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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
    List<String> quote;
    List<Quote> quoteList = new ArrayList<Quote>();



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

            publishUpdate(new ExtensionData()
                    .visible(true)
                    .icon(R.drawable.ic_extension)
                    .expandedTitle(quote.get(1))
                    .expandedBody(quote.get(0)));
            if (quoteList.size() >= 13) {
                quoteList.clear();
            }
            quoteList.add(new Quote(quote.get(1),quote.get(0)));
        }
        // Publish the extension data update.

        else {

            if (quoteList.size() > 1) {

                Random random = new Random();
                int count  = random.nextInt(quoteList.size()-1);
                publishUpdate(new ExtensionData()
                        .visible(true)
                        .icon(R.drawable.ic_extension)
                        .expandedTitle(quoteList.get(count).getAuthor())
                        .expandedBody(quoteList.get(count).getQuote()));


            } else {


                publishUpdate(new ExtensionData()
                        .visible(true)
                        .icon(R.drawable.ic_extension)
                        .expandedTitle(getString(R.string.title_out_connection))
                        .expandedBody(getString(R.string.qoute_out_connection)));

            }
        }
    }



    class QuoteTask extends AsyncTask<Void, Void, List<String>> {


        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> exitList = null;

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

                }
                exitList = new LinkedList<String>(Arrays.asList(response.toString().split("\\(")));

                if (exitList.size()==1){

                    exitList.add("Quote");

                } else {

                    exitList.set(1,exitList.get(1).replaceAll("\\)",""));

                }




                in.close();

            } catch (Exception e){
                exitList = new LinkedList<String>();
                exitList.set(0,"Edward John Phelps");
                exitList.set(1,"The man who makes no mistakes does not usually make anything");

                e.printStackTrace();


            }




            return exitList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<String> s) {
            super.onPostExecute(s);
        }
    }

}



