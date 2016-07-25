package com.tc.echosense.cache.interactor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tc.echosense.cache.entity.DatabaseHandler;

import java.util.ArrayList;

/**
 * Created by tc on 7/25/16.
 */

public class CacheInteractorWifiTable {

    public DatabaseHandler databaseHandler;
    public SQLiteDatabase db ;
    public Cursor cursor;
    ContentValues cv;

     public String setWifiSSIDInWifiTable(Context context, ArrayList<String> ssidArrayList){

          String status="ERROR: in setWifiSSIDInWifiTable()";

          if(context ==null || ssidArrayList==null){
               return status;
          }

          try{
          databaseHandler = new DatabaseHandler(context);
          db = databaseHandler.getWritableDatabase();
          db.execSQL("delete from "+DatabaseHandler.WIFI_TABLE);

          int i;
          for(i=0;i<ssidArrayList.size();i++) {

               cv = new ContentValues();
               cv.put("ssid", ssidArrayList.get(i));

               if(db.insert(DatabaseHandler.WIFI_TABLE,null,cv)==-1){
                   return status;
               }
          }
              if(i==ssidArrayList.size()){
                  status="SUCCESS";
              }
         }
        catch (Exception e){e.printStackTrace();}
        finally {
            if(db!=null)db.close();
            if(databaseHandler!=null)databaseHandler.close();
        }
          return status;
     }


    public ArrayList<String> getSaveSSIDArrayList(Context context){
        ArrayList<String> ssidArrayList=new ArrayList<>();

        if(context==null){
            return ssidArrayList;
        }

        try {

        databaseHandler = new DatabaseHandler(context);
        db = databaseHandler.getWritableDatabase();
        cursor=null;

                            String selectQuery = "SELECT * FROM " + DatabaseHandler.WIFI_TABLE +" ;";
                            cursor = db.rawQuery(selectQuery, null);

                            if (cursor.moveToFirst()) {
                                do {
                                     ssidArrayList.add(
                                             cursor.getString(cursor.getColumnIndex("ssid")
                                 ));
                                } while (cursor.moveToNext());
                            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(db!=null)db.close();
            if(databaseHandler!=null)databaseHandler.close();
             if(cursor!=null){cursor.close();}
        }

        return ssidArrayList;
    }

}
