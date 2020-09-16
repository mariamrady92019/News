package com.route.newsfri;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.route.apis.model.ArticlesItem;
import com.route.apis.model.SourcesItem;

import java.util.List;

public class HomePage extends AppCompatActivity {

    protected TabLayout tablayout;
    protected RecyclerView recyclerView;
    protected ProgressBar progressBar;
    NewsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    HomeViewModel viewModel ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home_page);
        initView();
        initRecyclerView();
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.getNewsSources();
        observeLiveData();
    }


    public void observeLiveData(){

        viewModel.sources.observe(this,
                new Observer<List<SourcesItem>>() {
            @Override
            public void onChanged(List<SourcesItem> sourcesItems) {
                initTabLayout(sourcesItems);
            }
        });

        viewModel.message.observe(this,
                new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(HomePage.this, s,
                        Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.newsList.observe(this,
                new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articlesItems) {
                adapter.changeData(articlesItems);
            }
        });
    }


    private void initRecyclerView() {

        adapter=new NewsAdapter(null);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initTabLayout(final List<SourcesItem> sources) {

        for(SourcesItem source : sources){
         TabLayout.Tab tab =  tablayout.newTab();
         tab.setText(source.getName());
         tab.setTag(source);
         tablayout.addTab(tab);
        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SourcesItem sourcesItem = ((SourcesItem) tab.getTag());
                String sourceId = sourcesItem.getId();
                viewModel.getNewsBySourceId(sourceId);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                SourcesItem sourcesItem = ((SourcesItem) tab.getTag());
                String sourceId = sourcesItem.getId();
                viewModel.getNewsBySourceId(sourceId);
            }
        });
        tablayout.getTabAt(0).select();
    }


    private void initView() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }
}
