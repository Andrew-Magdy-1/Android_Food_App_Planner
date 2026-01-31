package com.example.food_app_planner.archistartcode.network.offlineconnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.IntentFilter;


public class NetworkMonitor {
    private Context context;
    private NetworkCallback callback;
    private BroadcastReceiver networkReceiver;

    public interface NetworkCallback {
        void onNetworkAvailable();
        void onNetworkUnavailable();
    }

    public NetworkMonitor(Context context, NetworkCallback callback) {
        this.context = context;
        this.callback = callback;
        initReceiver();
    }

    private void initReceiver() {
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isNetworkAvailable()) {
                    callback.onNetworkAvailable();
                } else {
                    callback.onNetworkUnavailable();
                }
            }


        };
    }
    public void register() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkReceiver, filter);

        if (isNetworkAvailable()) {
            callback.onNetworkAvailable();
        } else {
            callback.onNetworkUnavailable();
        }
    }

    public void unregister() {
        if (networkReceiver != null) {
            try {
                context.unregisterReceiver(networkReceiver);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
