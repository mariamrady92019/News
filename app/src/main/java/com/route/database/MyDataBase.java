package com.route.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.route.apis.model.SourcesItem;

/**
 * Created by Mohamed Nabil Mohamed on 11/22/2019.
 * m.nabil.fci2015@gmail.com
 */
@Database(entities = {SourcesItem.class},version = 1,exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
   private static MyDataBase myDataBase;
   public abstract SourcesDao sourcesDao();
   public static void init(Context context){
       if(myDataBase==null)
           myDataBase = Room.databaseBuilder(context,
                   MyDataBase.class,"newsDatabase")
                   .fallbackToDestructiveMigration()
                   .build();

   }
   public static MyDataBase  getInstance(){
       return myDataBase;
   }
}
