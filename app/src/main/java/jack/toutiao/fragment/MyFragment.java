package jack.toutiao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jack.toutiao.R;
import jack.toutiao.adapter.RecyclerViewAdapter;
import jack.toutiao.api.API;

import jack.toutiao.bean.NewsEntity;
import jack.toutiao.task.NewsTitleTask;
import jack.toutiao.util.MyApplication;

/**
 * Created by 黄文杰 on 2016/7/8.
 */
public class MyFragment extends Fragment{

    private String news_title;
    String news;
    List<NewsEntity>newsEntities;
    RecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);

        if (getArguments()!=null)
        {
            news_title=getArguments().getString("text");

        }

        news= API.news+news_title+"&key="+API.KEY;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LinearLayoutManager manager=new LinearLayoutManager(getContext());

        View view=inflater.inflate(R.layout.frag_main,container,false);
       newsEntities=new ArrayList<NewsEntity>();
        final RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(manager);
        final SwipeRefreshLayout swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        news= API.news+news_title+"&key="+API.KEY;
        mAdapter=new RecyclerViewAdapter(MyApplication.getContext());

       new NewsTitleTask(newsEntities,recyclerView,swipeRefreshLayout,mAdapter).execute(news);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsEntities=new ArrayList<NewsEntity>();
                new NewsTitleTask(newsEntities,recyclerView,swipeRefreshLayout,mAdapter).execute(news);
            }
        });
        recyclerView.setAdapter(mAdapter);
        return view;
    }
}
