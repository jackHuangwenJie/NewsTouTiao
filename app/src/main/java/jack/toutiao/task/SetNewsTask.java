package jack.toutiao.task;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import jack.toutiao.adapter.RecyclerViewAdapter;
import jack.toutiao.bean.NewsEntity;

/**
 * Created by 黄文杰 on 2016/7/10.
 */
public class SetNewsTask extends AsyncTask<Void,Void,Void> {

    private List<NewsEntity>newsEntities;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerViewAdapter mAdapter;

   public SetNewsTask(List<NewsEntity>newsEntities, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout, RecyclerViewAdapter mAdapter)
   {
        this.newsEntities=newsEntities;
       this.recyclerView=recyclerView;
       this.swipeRefreshLayout=swipeRefreshLayout;
       this.mAdapter=mAdapter;
   }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mAdapter.setNewsEntities(newsEntities);
        if (recyclerView.getAdapter()==null)
        {
            recyclerView.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
        this.swipeRefreshLayout.setRefreshing(false);
    }
}
