package com.tc.echosense.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tc.echosense.R;
import com.tc.echosense.cache.boundary.CacheBoundary;

import java.util.ArrayList;

public class ShowWifiDetailsActivity extends AppCompatActivity {

    TextView textViewSSID,textViewBSSID,textViewLevel;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_wifi_details);

        textViewBSSID= (TextView) findViewById(R.id.textViewBSSID);
        textViewSSID= (TextView) findViewById(R.id.textViewSSID);
        textViewLevel= (TextView) findViewById(R.id.textViewLevel);
        listView= (ListView) findViewById(R.id.listViewShowWifiDetailsActivityId);

        String ssid=getIntent().getStringExtra("ssid");
        String bssid=getIntent().getStringExtra("bssid");
        String level=getIntent().getStringExtra("level");

        textViewLevel.setText("Level : "+level+" (Max :5)");
        textViewBSSID.setText("BSSID : "+bssid);
        textViewSSID.setText("SSID : "+ssid);


        ArrayList<String> ssidStringArrayList=new CacheBoundary().getSSIDArrayList(ShowWifiDetailsActivity.this);
        listView.setAdapter(new ArrayAdapter<String>(ShowWifiDetailsActivity.this,R.layout.inflate_listview,ssidStringArrayList.toArray(new String[0])));

    }
}
