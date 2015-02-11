package tickspot.application.sev.tickspot.activities;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import roboguice.inject.InjectView;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.adapters.MenuAdapter;
import tickspot.application.sev.tickspot.model.NavigationItem;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle drawerToggle;

    @InjectView(R.id.left_drawer)
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, new Toolbar(MainActivity.this), R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {

            }

            public void onDrawerOpened(View drawerView) {

            }
        };
        mDrawerLayout.setDrawerListener(drawerToggle);

        ArrayList<NavigationItem> temporaryMenuItems  = new ArrayList<>();
        temporaryMenuItems.add(new NavigationItem(R.string.menu_profile, R.drawable.ic_facebook_white_24dp));
        temporaryMenuItems.add(new NavigationItem(R.string.menu_settings, R.drawable.ic_dialpad_white_24dp));

        mDrawerList.setAdapter(new MenuAdapter(this,temporaryMenuItems));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
