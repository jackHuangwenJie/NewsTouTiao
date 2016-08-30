package jack.toutiao.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import jack.toutiao.R;

/**
 * Created by 黄文杰 on 2016/7/8.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView news_title;
    private ImageView news_img;
    private TextView news_author;
    private TextView news_date;

    public ViewHolder(View itemView) {
        super(itemView);

        news_title = (TextView) itemView.findViewById(R.id.news_title);
        news_img = (ImageView) itemView.findViewById(R.id.news_img);
        news_author= (TextView) itemView.findViewById(R.id.text_author);
        news_date = (TextView) itemView.findViewById(R.id.text_date);
    }

    public TextView getNews_title() {
        return news_title;
    }

    public ImageView getNews_img() {
        return news_img;
    }
    public TextView getNews_author()
    {
        return news_author;
    }
    public TextView getNews_date()
    {
        return news_date;
    }



}
