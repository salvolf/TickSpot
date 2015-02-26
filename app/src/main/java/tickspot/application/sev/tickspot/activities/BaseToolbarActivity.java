package tickspot.application.sev.tickspot.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.TickspotApplication;
import tickspot.application.sev.tickspot.adapters.MenuAdapter;
import tickspot.application.sev.tickspot.database.MyDatabaseHelper;
import tickspot.application.sev.tickspot.model.NavigationItem;
import tickspot.application.sev.tickspot.preferences.Preferences;
import tickspot.application.sev.tickspot.restservice.models.Client;
import tickspot.application.sev.tickspot.restservice.models.Subscription;

public abstract class BaseToolbarActivity extends RoboActionBarActivity {


    private Spinner mSpinner;
    private SimSpinnerAdapter mAdapter;
    private long currentClientId;
    private ActionBarDrawerToggle mDrawerToggle;

    @InjectView(R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;

    @InjectView(R.id.left_drawer)
    private ListView mDrawerList;

    @InjectView(R.id.toolbar)
    private Toolbar mToolbar;

    @Inject
    private MyDatabaseHelper databaseHelper;

    protected boolean isSpinnerEnabled() {
        return databaseHelper.getClients().size() > 1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_dialpad_white_24dp, R.string.drawer_open,
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

        ArrayList<NavigationItem> temporaryMenuItems = new ArrayList<>();
        temporaryMenuItems.add(new NavigationItem(R.string.menu_profile, R.drawable.ic_facebook_white_24dp));
        temporaryMenuItems.add(new NavigationItem(R.string.menu_settings, R.drawable.ic_dialpad_white_24dp));

        mDrawerList.setAdapter(new MenuAdapter(this, temporaryMenuItems));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

   /* @Subscribe
    public void handleClientSelection(ClientSelectionEvent clientSelectionEvent) {
        if (clientSelectionEvent.clientSelectedId != currentClientId) {
            currentClientId = clientSelectionEvent.clientSelectedId;
            updateActionBar();
        }
    }*/

    public void updateActionBar() {
        if (mToolbar != null) {
            if (isSpinnerEnabled() && databaseHelper.getSubscriptions().size() > 1) {
                setActionBarModeSimSpinner();
            } else {
                setActionBarModeTitleOnly();
            }
        }
    }

    private void setActionBarModeSimSpinner() {
        if (databaseHelper.getClients().size() > 1) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            mAdapter = new SimSpinnerAdapter(actionBar.getThemedContext(), databaseHelper.getSelectedSubscriptionCompany(), databaseHelper.getSubscriptions());
            mSpinner.setAdapter(mAdapter);
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Client client = databaseHelper.getClients().get(position);
                    if (client.id != (currentClientId)) {
                        Preferences.setClientSelectedId(client.id);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            int selectedIndex = 0;
            for (int i = 0; i < databaseHelper.getClients().size(); i++) {
                if (Preferences.getClientSelectedId() != -1 && Preferences.getClientSelectedId() == (databaseHelper.getClients().get(i).id)) {
                    selectedIndex = i;
                    currentClientId = databaseHelper.getClients().get(i).id;
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

    public class SimSpinnerAdapter extends BaseAdapter {

        private final String title;
        private LayoutInflater inflater;

        public SimSpinnerAdapter(Context context, String title, List<Subscription> subscriptions) {
            this.inflater = LayoutInflater.from(context);
            this.title = title;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent, false);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent, true);
        }

        @Override
        public int getCount() {
            return databaseHelper.getSubscriptions().size();
        }

        @Override
        public Subscription getItem(int position) {
            return databaseHelper.getSubscriptions().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getCustomView(int position, View convertView, ViewGroup parent, boolean isHeader) {

            View row = inflater.inflate(isHeader ? R.layout.spinner_sim_header : R.layout.spinner_sim_item, parent, false);

            TextView bigTitle = (TextView) row.findViewById(android.R.id.text1);

            if (isHeader) {
                bigTitle.setText(title);
            } else {
                if (position == mSpinner.getSelectedItemPosition()) {
                    bigTitle.setTypeface(null, Typeface.BOLD);
                } else {
                    bigTitle.setTypeface(null, Typeface.NORMAL);
                }
            }

            return row;
        }
    }

    /*@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
