package tickspot.application.sev.tickspot.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.disegnator.robotocalendar.RobotoCalendarView;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.TickspotApplication;
import tickspot.application.sev.tickspot.adapters.MenuAdapter;
import tickspot.application.sev.tickspot.adapters.PresetSpinnerAdapter;
import tickspot.application.sev.tickspot.adapters.SubscriptionAdapter;
import tickspot.application.sev.tickspot.database.MyDatabaseHelper;
import tickspot.application.sev.tickspot.dialogs.AddPresetDialog;
import tickspot.application.sev.tickspot.managers.ResponsesManager;
import tickspot.application.sev.tickspot.model.NavigationItem;
import tickspot.application.sev.tickspot.model.Preset;
import tickspot.application.sev.tickspot.preferences.Preferences;
import tickspot.application.sev.tickspot.restservice.models.Client;

public class MainActivity extends RoboActionBarActivity implements RobotoCalendarView.RobotoCalendarListener {

    @InjectView(R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    @InjectView(R.id.left_drawer)
    private ListView mDrawerList;

    @InjectView(R.id.toolbar)
    private Toolbar mToolbar;

    @InjectView(R.id.spinner_preset)
    private Spinner mSpinnerPreset;

    @InjectView(R.id.robotoCalendarPicker)
    private RobotoCalendarView mRobotoCalendarPicker;

    @Inject
    private MyDatabaseHelper databaseHelper;

    @Inject
    private ResponsesManager responsesManager;

    private Spinner mSpinner;
    private SubscriptionAdapter mAdapter;
    private long currentClientId;
    private Calendar currentCalendar;
    private int currentMonthIndex;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                Log.e("TEST", "Drawer close");
            }

            public void onDrawerOpened(View drawerView) {
                Log.e("TEST", "Drawer open");

            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        ArrayList<NavigationItem> temporaryMenuItems = new ArrayList<>();
        temporaryMenuItems.add(new NavigationItem(R.string.menu_profile, R.drawable.ic_facebook_white_24dp));
        temporaryMenuItems.add(new NavigationItem(R.string.menu_settings, R.drawable.ic_dialpad_white_24dp));

        mDrawerList.setAdapter(new MenuAdapter(this, temporaryMenuItems));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        try {
            List<Preset> presets = new ArrayList<Preset>();
            presets.add(databaseHelper.getDefaultPreset());
            mSpinnerPreset.setAdapter(new PresetSpinnerAdapter(this, presets));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mRobotoCalendarPicker.setRobotoCalendarListener(this);
        currentMonthIndex = 0;
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        mRobotoCalendarPicker.initializeCalendar(currentCalendar);

        // Mark current day
        //mRobotoCalendarPicker.markDayAsCurrentDay(currentCalendar.getTime());
    }

    @Override
    public void onSupportContentChanged() {
        super.onSupportContentChanged();
        mSpinner = (Spinner) findViewById(R.id.toolbar_spinner);
        if (mToolbar != null) {
            mSpinner.setVisibility(isSpinnerEnabled() ? View.VISIBLE : View.GONE);
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TickspotApplication.getEventBus().register(this);
        updateActionBar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TickspotApplication.getEventBus().unregister(this);
    }

    protected boolean isSpinnerEnabled() {
        return responsesManager.getClients().size() > 1;
    }

    public void updateActionBar() {
        if (mToolbar != null) {
            if (isSpinnerEnabled() && responsesManager.getSubscriptions().size() > 1) {
                setActionBarModeSimSpinner();
            } else {
                setActionBarModeTitleOnly();
            }
        }
    }

    private void setActionBarModeSimSpinner() {
        if (responsesManager.getClients().size() > 1) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            mAdapter = new SubscriptionAdapter(actionBar.getThemedContext(), databaseHelper.getSelectedSubscriptionCompany(), responsesManager.getSubscriptions());
            mSpinner.setAdapter(mAdapter);
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Client client = responsesManager.getClients().get(position);
                    if (client.id != (currentClientId)) {
                        Preferences.setClientSelectedId(client.id);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            int selectedIndex = 0;
            for (int i = 0; i < responsesManager.getClients().size(); i++) {
                if (Preferences.getClientSelectedId() != -1 && Preferences.getClientSelectedId() == (responsesManager.getClients().get(i).id)) {
                    selectedIndex = i;
                    currentClientId = responsesManager.getClients().get(i).id;
                }
            }
            mSpinner.setSelection(selectedIndex);
            mSpinner.setVisibility(View.VISIBLE);
        }
    }

    private void setActionBarModeTitleOnly() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        mSpinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if (item.getItemId() == R.id.action_add_preset){
            new AddPresetDialog().show(getSupportFragmentManager(),"Add Preset Dialog");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDateSelected(Date date) {
        mRobotoCalendarPicker.markDayAsSelectedDay(date);
    }

    @Override
    public void onLongDateSelected(Date date) {
        mRobotoCalendarPicker.markDayAsSelectedDay(date);

    }

    @Override
    public void onRightButtonClick() {
        currentMonthIndex++;
        updateCalendar();
    }

    @Override
    public void onLeftButtonClick() {
        currentMonthIndex--;
        updateCalendar();
    }

    private void updateCalendar() {
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
        mRobotoCalendarPicker.initializeCalendar(currentCalendar);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
