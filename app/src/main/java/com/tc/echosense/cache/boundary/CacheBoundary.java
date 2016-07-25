package com.tc.echosense.cache.boundary;

import android.content.Context;

import com.tc.echosense.cache.interactor.CacheInteractorWifiTable;

import java.util.ArrayList;

/**
 * Created by tc on 7/25/16.
 */

public class CacheBoundary {

    CacheInteractorWifiTable cacheInteractorWifiTable;

    public CacheBoundary(){
        cacheInteractorWifiTable=new CacheInteractorWifiTable();
    }

    public String setWifiSSid(Context context, ArrayList<String> ssidArrayList){
        return cacheInteractorWifiTable.setWifiSSIDInWifiTable(context,ssidArrayList);
    }

    public ArrayList<String> getSSIDArrayList(Context context){
        return cacheInteractorWifiTable.getSaveSSIDArrayList(context);
    }

}
