package com.tc.echosense.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.tc.echosense.R;
import com.tc.echosense.cache.boundary.CacheBoundary;
import com.tc.echosense.service.MonitorWifiService;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {

    Button buttonSearchAndSave;
    Button buttonService;
    ListView listView;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        buttonSearchAndSave= (Button) findViewById(R.id.buttonHomeScreenId);
        buttonService= (Button) findViewById(R.id.buttonServiceId);
        listView= (ListView) findViewById(R.id.listViewHomeScreenId);

        buttonSearchAndSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()) {
                    startActivity(new Intent(HomeScreenActivity.this, SearchAndSaveActivity.class));
                }else{
                    requestPermission();
                    Toast.makeText(HomeScreenActivity.this,"No wifi permission",Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(checkPermission()){
                startService(new Intent(HomeScreenActivity.this,MonitorWifiService.class));
            }else{
                requestPermission();
                Toast.makeText(HomeScreenActivity.this,"No wifi permission",Toast.LENGTH_SHORT).show();
            }
            }
        });

        ArrayList<String> ssidStringArrayList=new CacheBoundary().getSSIDArrayList(HomeScreenActivity.this);
       listView.setAdapter(new ArrayAdapter<String>(HomeScreenActivity.this,R.layout.inflate_listview,ssidStringArrayList.toArray(new String[0])));
    }


    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<String> ssidStringArrayList=new CacheBoundary().getSSIDArrayList(HomeScreenActivity.this);
        listView.setAdapter(new ArrayAdapter<String>(HomeScreenActivity.this,R.layout.inflate_listview,ssidStringArrayList.toArray(new String[0])));
    }


    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(HomeScreenActivity.this, Manifest.permission.CHANGE_WIFI_STATE);
        int result2 = ContextCompat.checkSelfPermission(HomeScreenActivity.this, Manifest.permission.CHANGE_WIFI_STATE);
        if (result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(HomeScreenActivity.this,Manifest.permission.CHANGE_WIFI_STATE) &&
                ActivityCompat.shouldShowRequestPermissionRationale(HomeScreenActivity.this,Manifest.permission.ACCESS_WIFI_STATE)) {
            Toast.makeText(HomeScreenActivity.this,"This permission is required.",Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(HomeScreenActivity.this,new String[]{Manifest.permission.CHANGE_WIFI_STATE},PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(HomeScreenActivity.this,"Permission Granted.",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeScreenActivity.this,"Permission Denied.",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
