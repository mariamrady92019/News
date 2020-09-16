package com.route.newsfri;

import androidx.lifecycle.MutableLiveData;

import com.route.apis.ApiManager;
import com.route.apis.model.SourcesItem;
import com.route.apis.model.SourcesResponse;
import com.route.database.MyDataBase;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mohamed Nabil Mohamed on 11/22/2019.
 * m.nabil.fci2015@gmail.com
 */
public class SourcesRepository {
    MutableLiveData<List<SourcesItem>> sources=new MutableLiveData<>();

    public void getNewsSources(){
        ApiManager.getApis()
                .getNewsSources(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SourcesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SourcesResponse sourcesResponse) {
                        sources.setValue(sourcesResponse.getSources());
                        // cache data in db
                        cacheNewsSources(sourcesResponse.getSources());
                    }

                    @Override
                    public void onError(Throwable e) {
                       // message.setValue(e.getLocalizedMessage());
                        //getDatafromDatabase
                        getSourcesFromCache();
                    }
                });
    }

    private void getSourcesFromCache() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                List<SourcesItem> data = MyDataBase.getInstance()
                        .sourcesDao()
                        .getNewsSources();
                sources.postValue(data);
            }
        };
        thread.start();
    }

    private void cacheNewsSources(final List<SourcesItem> data) {
        Thread thread =new Thread(){
          public void run(){
              MyDataBase.getInstance()
                      .sourcesDao()
                      .cacheSourcesList(data);
          }
        };
        thread.start();
    }

}
