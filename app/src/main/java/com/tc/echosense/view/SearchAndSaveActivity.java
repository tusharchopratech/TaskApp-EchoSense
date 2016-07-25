package com.tc.echosense.view;

import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tc.echosense.R;
import com.tc.echosense.cache.boundary.CacheBoundary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchAndSaveActivity extends AppCompatActivity {


    Button buttonSearch;
    Button buttonSave;
    ListView listView;

    List<ScanResult> results;
    ArrayList<String> ssids=new ArrayList<>();
    WifiManager wifi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_and_save);

        buttonSave= (Button) findViewById(R.id.buttonSaveId);
        buttonSearch= (Button) findViewById(R.id.buttonSearchId);
        listView= (ListView) findViewById(R.id.listViewSearchAndSaveActivityId);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ssids.size()>0) {
                    if(new CacheBoundary().setWifiSSid(SearchAndSaveActivity.this, ssids).equals("SUCCESS")){
                        Toast.makeText(SearchAndSaveActivity.this,"SSID saved",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SearchAndSaveActivity.this,"SSID saving failed",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SearchAndSaveActivity.this,"No Ssid to save",Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wifi.startScan();
                results = wifi.getScanResults();

                ssids=new ArrayList<>();
                for(int i=0;i<results.size();i++) {
                    if(!results.get(i).SSID.trim().equals("")) {
                        ssids.add(results.get(i).SSID);
                    }
                }
                listView.setAdapter(new ArrayAdapter<String>(SearchAndSaveActivity.this,R.layout.inflate_listview,ssids.toArray(new String[0])));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog(i);
            }
        });

    }


    public void openDialog(final int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter New SSID");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ssid = input.getText().toString();
                ssids.set(i,ssid);
                listView.setAdapter(new ArrayAdapter<String>(SearchAndSaveActivity.this,R.layout.inflate_listview,ssids.toArray(new String[0])));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


}
