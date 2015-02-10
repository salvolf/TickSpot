package tickspot.application.sev.tickspot.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import roboguice.inject.InjectView;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.adapters.DaysAdapter;
import tickspot.application.sev.tickspot.model.SlidingTabLayout;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.viewPager)
    private ViewPager viewPager;

    @InjectView(R.id.tabs)
    private SlidingTabLayout mTabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager.setAdapter(new DaysAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(this);
        //viewPager.setOffscreenPageLimit(contactFragments.size());
        mTabs.setSelectedIndicatorColors(Color.WHITE);
        mTabs.setCustomTabView(R.layout.tab_layout, R.id.text_day);
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(viewPager);
        mTabs.setOnPageChangeListener(this);


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
