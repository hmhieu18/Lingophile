package com.example.lingophile.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class InternetListener extends BroadcastReceiver {

    private ConnectionListener listener;

    public interface ConnectionListener{
        void onConnected();
        void onDisconnected();
    }

    public InternetListener(){
        listener = null;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null)
            return;
        notifytoListener(context);
        Log.d("@@@", "Internet Notify");
    }


    private void notifytoListener(Context context){
        if (listener == null)
            return;
        if (isOnline(context))
            listener.onConnected();
        else
            listener.onDisconnected();
    }

    public void register(ConnectionListener connectionListener){
        this.listener = connectionListener;
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
