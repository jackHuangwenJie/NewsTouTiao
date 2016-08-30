package jack.toutiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.LoginFilter;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jack.toutiao.R;
import jack.toutiao.bean.NewsEntity;
import jack.toutiao.cache.ACache;
import jack.toutiao.cache.DiskLruCache;
import jack.toutiao.holder.ViewHolder;
import jack.toutiao.task.DownloadTask;
import jack.toutiao.ui.ReadNewsActivity;
import jack.toutiao.util.MyApplication;
import jack.toutiao.util.Utils;

/**
 * Created by 黄文杰 on 2016/7/10.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<NewsEntity> newsEntityList;
    private LayoutInflater mLayoutInflater;
    private List<NewsEntity> newsEntitiesList;
    String key;
    private ACache aCache;
    DiskLruCache.Snapshot snapshot;
    DiskLruCache diskLruCache;


    public void setNewsEntities(List<NewsEntity> newsEntities) {

        newsEntitiesList = new ArrayList<>();
        for (int i = 0; i < newsEntities.size(); i++) {
            for (int j = 0; j < newsEntities.get(i).getResult().getData().size(); j++) {
                newsEntitiesList.add(newsEntities.get(i).getResult().getData().get(j));
            }
        }
        this.newsEntityList = newsEntities;
    }

    public RecyclerViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        aCache = ACache.get(MyApplication.getContext());
    }

    public List<NewsEntity> getNewsEntitiesList() {
        return newsEntityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        ViewHolder viewHolder = new ViewHolder(mLayoutInflater.inflate(R.layout.news_item, parent, false));
        viewHolder.itemView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        NewsEntity.ResultBean.DataBean dataBean = (NewsEntity.ResultBean.DataBean) newsEntitiesList.get(position);
        ((ViewHolder) holder).getNews_title().setText(dataBean.getTitle());
        ((ViewHolder) holder).getNews_author().setText(dataBean.getAuthor_name());
        ((ViewHolder) holder).getNews_date().setText(dataBean.getDate());
        ((ViewHolder) holder).itemView.setTag(dataBean.getUrl());
            if (aCache.getAsBitmap(dataBean.getThumbnail_pic_s())!=null)
        {
            ((ViewHolder)holder).getNews_img().setImageBitmap(aCache.getAsBitmap(dataBean.getThumbnail_pic_s()));
        }

        new DownloadTask(((ViewHolder) holder).getNews_img()).execute(dataBean.getThumbnail_pic_s());
    }


    @Override
    public int getItemCount() {

        if (newsEntitiesList == null) {
            return 0;
        } else {

            return newsEntitiesList.size();

        }

    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(MyApplication.getContext(), ReadNewsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", (String) view.getTag());
        MyApplication.getContext().startActivity(intent);


    }


}
