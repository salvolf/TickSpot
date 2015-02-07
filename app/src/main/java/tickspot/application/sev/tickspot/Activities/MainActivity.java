package tickspot.application.sev.tickspot.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

import java.util.Calendar;

import roboguice.inject.InjectView;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.adapters.DaysAdapter;
import tickspot.application.sev.tickspot.managers.RetroManager;
import tickspot.application.sev.tickspot.model.ProjectList;
import tickspot.application.sev.tickspot.model.SlidingTabLayout;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Inject
    private RetroManager retroManager;

    @InjectView(R.id.viewPager)
    private ViewPager viewPager;

    @InjectView(R.id.tabs)
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retroManager.getProjects();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        Log.e("TEST","Calendar year: "+currentYear + "\n Month: "+ currentMonth + "\n Number of days: "+ Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        viewPager.setAdapter(new DaysAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(this);
        //viewPager.setOffscreenPageLimit(contactFragments.size());
        mTabs.setSelectedIndicatorColors(Color.WHITE);
        mTabs.setCustomTabView(R.layout.tab_layout,R.id.text_day);
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(viewPager);
        mTabs.setOnPageChangeListener(this);
    }

   @Subscribe
    public void onProjectsApiCallDone(ProjectList projectList){
       Log.e ("TEST","Received projectResponse through the bus");
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
