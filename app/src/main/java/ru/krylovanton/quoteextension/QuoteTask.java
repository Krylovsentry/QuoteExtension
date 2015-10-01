package ru.krylovanton.quoteextension;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Антон on 08.09.2015.
 */
public class QuoteTask extends AsyncTask <Void, Void, String>{


    @Override
    protected String doInBackground(Void... params) {
        String exitString = "";

        try {
            String url = "http://api.forismatic.com/api/1.0/?method=getQuote&format=text&lang=ru";
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
