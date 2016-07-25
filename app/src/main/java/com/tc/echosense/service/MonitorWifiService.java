package com.tc.echosense.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.tc.echosense.R;
import com.tc.echosense.cache.boundary.CacheBoundary;
import com.tc.echosense.view.ShowWifiDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class MonitorWifiService extends Service {

    WifiManager wifi;
    private static final String TAG = "#MonitorWifiService";
    public static boolean isRunning  = false;
    boolean rescan = true;
    public MonitorWifiService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        isRunning=true;
    }

    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {

        rescan=true;
        Toast.makeText(MonitorWifiService.this,"Service Started",Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(rescan) {
                    try {
                        Thread.sleep(1000);

                        if (wifi.isWifiEnabled() == true){

                            wifi.startScan();
                            List<ScanResult> results = wifi.getScanResults();

                            ArrayList<String> ssidArrayList = new CacheBoundary().getSSIDArrayList(MonitorWifiService.this);

                            for(int i=0;i<results.size();i++){
                                if(ssidArrayList.contains(results.get(i).SSID)){
                                    sendNotification(results.get(i));
                                    rescan=false;
                                    //MonitorWifiService.this.stopSelf();
                                }
                            }

                        }

                    } catch (Exception e) {
                    }

                    if(isRunning){
                        Log.i(TAG, "Service running");
                    }
                }

            }
        }).start();

        return Service.START_STICKY;



    }

    private void sendNotification(ScanResult scanResult) {

        Intent intent = new Intent(this, ShowWifiDetailsActivity.class);
        intent.putExtra("ssid",scanResult.SSID);
        intent.putExtra("bssid",scanResult.BSSID);
        int level=WifiManager.calculateSignalLevel(scanResult.level,5);
        intent.putExtra("level",String.valueOf(level));
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification noti = new Notification.Builder(this)
                .setContentTitle("EchoSence SSID : "+scanResult.SSID)
                .setContentText("BSSID : "+scanResult.BSSID).setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        isRunning = false;
    }

}
