package jack.toutiao.ui;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jack.toutiao.R;
import jack.toutiao.adapter.MyPagerAdapter;
import jack.toutiao.fragment.MyFragment;


public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<String> mSelectTitle=new ArrayList<>();
    // private View view1, view2, view3, view4, view5,view6,view7,view8,view9,view10;//页卡视图
    //private List<View> mViewList = new ArrayList<>();//页卡视图集合
    List<Fragment> mFragments = new ArrayList<Fragment>();

    //记录第一次按退出按钮时的时间
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.Tab);

        

      /*  mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.frag_main, null);
        view2 = mInflater.inflate(R.layout.frag_main, null);
        view3 = mInflater.inflate(R.layout.frag_main, null);
        view4 = mInflater.inflate(R.layout.frag_main, null);
        view5 = mInflater.inflate(R.layout.frag_main, null);
        view6 = mInflater.inflate(R.layout.frag_main, null);
        view7 = mInflater.inflate(R.layout.frag_main, null);
        view8 = mInflater.inflate(R.layout.frag_main, null);
        view9 = mInflater.inflate(R.layout.frag_main, null);
        view10 = mInflater.inflate(R.layout.frag_main, null);

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        mViewList.add(view5);
        mViewList.add(view6);
        mViewList.add(view7);
        mViewList.add(view8);
        mViewList.add(view9);
        mViewList.add(view10);*/

        //添加页卡标题
        mTitleList.add("头条");
        mTitleList.add("社会");
        mTitleList.add("国内");
        mTitleList.add("国际");
        mTitleList.add("娱乐");
        mTitleList.add("体育");
        mTitleList.add("军事");
        mTitleList.add("科技");
        mTitleList.add("财经");
        mTitleList.add("时尚");

        mSelectTitle.add("toutiao");
        mSelectTitle.add("shehui");
        mSelectTitle.add("guonei");
        mSelectTitle.add("guoji");
        mSelectTitle.add("yule");
        mSelectTitle.add("tiyu");
        mSelectTitle.add("junshi");
        mSelectTitle.add("keji");
        mSelectTitle.add("caijing");
        mSelectTitle.add("shishang");

        for (int i = 0; i < mSelectTitle.size(); i++) {
            Fragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text", mSelectTitle.get(i));
            fragment.setArguments(bundle);
            mFragments.add(fragment);

        }

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为可滚动模式

        //添加tab选项卡
        for (int i = 0; i < mTitleList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(i)));
        }

        MyPagerAdapter mAdapter = new MyPagerAdapter(mFragments, mTitleList, getSupportFragmentManager(), this);
        mViewPager.setOffscreenPageLimit(10);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
        mTabLayout.setTabTextColors(getResources().getColor(R.color.dark_white), Color.WHITE);
    }

    /**
     * 实现按两次返回键退出app
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if ((System.currentTimeMillis()-exitTime)>2000)
            {
                Toast.makeText(this, "再按一次退出头条", Toast.LENGTH_SHORT).show();
                exitTime=System.currentTimeMillis();
            }
            else
            {
                onBackPressed();
            }
        }
        return false;
    }
}
