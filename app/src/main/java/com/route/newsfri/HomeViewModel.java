package com.route.newsfri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.route.apis.ApiManager;
import com.route.apis.model.ArticlesItem;
import com.route.apis.model.NewsResponse;
import com.route.apis.model.SourcesItem;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mohamed Nabil Mohamed on 11/22/2019.
 * m.nabil.fci2015@gmail.com
 */
public class HomeViewModel extends ViewModel {

    MutableLiveData<List<SourcesItem>> sources;
    MutableLiveData<String> message =new MutableLiveData<>();
    MutableLiveData<List<ArticlesItem>> newsList = new MutableLiveData<>();
    SourcesRepository sourcesRepository=new SourcesRepository();

    public HomeViewModel(){

        sources = sourcesRepository.sources;
    }

    public void getNewsBySourceId(String sourceId){
        ApiManager.getApis()
                .getNewsBySourceId(Constants.API_KEY,sourceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NewsResponse newsResponse) {
                        newsList.setValue(newsResponse.getArticles());
                    }

                    @Override
                    public void onError(Throwable e) {
                        message.setValue(e.getLocalizedMessage());
                    }
                });
    }

    public void getNewsSources() {
        sourcesRepository.getNewsSources();
    }
}
