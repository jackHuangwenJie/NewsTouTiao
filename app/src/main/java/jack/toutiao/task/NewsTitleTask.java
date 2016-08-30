package jack.toutiao.task;

import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;

import jack.toutiao.adapter.RecyclerViewAdapter;
import jack.toutiao.bean.NewsEntity;
import jack.toutiao.cache.ACache;
import jack.toutiao.util.Http;
import jack.toutiao.util.MyApplication;


/**
 * Created by 黄文杰 on 2016/7/10.
 */
public class NewsTitleTask extends AsyncTask<String, Void, NewsEntity> {

    private List<NewsEntity>newsEntities;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter mAdapter;
    private String url;
    private ACache mACache;

    public NewsTitleTask(List<NewsEntity>newsEntities, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout, RecyclerViewAdapter mAdapter) {
        this.newsEntities = newsEntities;
        this.recyclerView = recyclerView;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.mAdapter = mAdapter;
    }

    @Override
    protected NewsEntity doInBackground(String... strings) {

        NewsEntity newsEntity = null;
     for (int i=0;i<strings.length;i++) {
         url=strings[i];
         try {
             String json_NewsEntity = Http.getJsonString(strings[0]);
             if (json_NewsEntity == null) {
                 return null;
             }
             newsEntity = JSON.parseObject(json_NewsEntity, NewsEntity.class);

         } catch (IOException e) {
             e.printStackTrace();
         }
     }
        return newsEntity;
    }

    @Override
    protected void onPostExecute(NewsEntity newsEntity) {
        super.onPostExecute(newsEntity);

        if (newsEntity == null) {
            Toast.makeText(MyApplication.getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            this.swipeRefreshLayout.setRefreshing(false);
            return;
        }


        newsEntities.add(newsEntity);

        new SetNewsTask(newsEntities,recyclerView,swipeRefreshLayout,mAdapter).execute();
        new SaveToCaChe(url,newsEntity).execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.swipeRefreshLayout.setRefreshing(true);
    }
}
