package com.route.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.route.apis.model.SourcesItem;

import java.util.List;

/**
 * Created by Mohamed Nabil Mohamed on 11/22/2019.
 * m.nabil.fci2015@gmail.com
 */
@Dao
public interface SourcesDao {

    @Query("select * from sourcesitem")
    List<SourcesItem> getNewsSources();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void cacheSourcesList(List<SourcesItem> items);
}
