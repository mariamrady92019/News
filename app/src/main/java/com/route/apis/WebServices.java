package com.route.apis;

import com.route.apis.model.NewsResponse;
import com.route.apis.model.SourcesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mohamed Nabil Mohamed on 10/18/2019.
 * m.nabil.fci2015@gmail.com
 */
public interface WebServices {

    @GET("sources")
    Single<SourcesResponse> getNewsSources(@Query("apiKey") String apikey);

    @GET("everything")
    Single<NewsResponse> getNewsBySourceId(@Query("apiKey") String apikey,
                                         @Query("sources") String sourceId
    );
}
