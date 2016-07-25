package com.tc.echosense.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.tc.echosense.service.MonitorWifiService;

public class WifiBroadCastReceiver extends BroadcastReceiver {


    WifiManager wifi;

    public WifiBroadCastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
           if (wifi.isWifiEnabled() == true){
               context.startService(new Intent(context,MonitorWifiService.class));
           }
    }
}
