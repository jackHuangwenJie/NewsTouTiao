package jack.toutiao.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 黄文杰 on 2016/7/7.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

   // private List<View> mViewList;
    private List<String> mTitleList;
    private List<Fragment> mFragments;
    private Context context;

    public MyPagerAdapter(List<Fragment> mFragments,List<String> mTitleList,FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
        this.mFragments=mFragments;
        this.mTitleList=mTitleList;
    }

    @Override
    public int getCount() {
        return mTitleList.size();//页卡数
    }

  /*  @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;//官方推荐写法
    }*/


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

   /* @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));//添加页卡
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));//删除页卡
    }*/

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);//页卡标题
    }

}
