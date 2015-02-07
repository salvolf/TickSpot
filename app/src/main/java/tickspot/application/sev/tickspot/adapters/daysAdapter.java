package tickspot.application.sev.tickspot.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import tickspot.application.sev.tickspot.fragments.DayFragment;

public class DaysAdapter extends FragmentStatePagerAdapter {
    Calendar calendar;
    private ArrayList<DayFragment> dayFragments;
    public DaysAdapter(FragmentManager fm) {
        super(fm);
        calendar = Calendar.getInstance();
    }

    @Override
    public Fragment getItem(int position) {
        return new DayFragment();
    }


    @Override
    public int getCount() {
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position + 1);
    }
}
