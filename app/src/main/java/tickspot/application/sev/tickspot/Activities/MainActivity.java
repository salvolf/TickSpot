package tickspot.application.sev.tickspot.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.astuetz.PagerSlidingTabStrip;
import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

import java.util.Calendar;

import roboguice.inject.InjectView;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.adapters.daysAdapter;
import tickspot.application.sev.tickspot.managers.RetroManager;
import tickspot.application.sev.tickspot.model.ProjectList;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Inject
    private RetroManager retroManager;

    @InjectView(R.id.viewPager)
    private ViewPager viewPager;

    @InjectView(R.id.tabs)
    private PagerSlidingTabStrip mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retroManager.getProjects();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        Log.e("TEST","Calendar year: "+currentYear + "\n Month: "+ currentMonth + "\n Number of days: "+ Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        viewPager.setAdapter(new daysAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(this);
        //viewPager.setOffscreenPageLimit(contactFragments.size());

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
