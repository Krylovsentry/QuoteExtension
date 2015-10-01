package ru.krylovanton.quoteextension;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Anton on 30.09.2015.
 *class for checking connection with internet on a mobile device
 **/
public class ConnectionDetector {
    private Context context;

    //standart constructor
    public ConnectionDetector(Context context){

        this.context = context;


    }

    // method for check. true if we have connection and false if not
    public boolean ConnectingInternet(){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null){

                for (int i = 0 ; i < info.length ; i ++){

                    if (info[i].getState() == NetworkInfo.State.CONNECTED){

                        return true;
                    }
                }

            }
        }


        return  false;
    }


}
