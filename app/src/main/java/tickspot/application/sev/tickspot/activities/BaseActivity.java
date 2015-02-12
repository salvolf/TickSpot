package tickspot.application.sev.tickspot.activities;

import android.os.Bundle;
import android.view.Menu;

import roboguice.activity.RoboActionBarActivity;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.TickspotApplication;

public class BaseActivity extends RoboActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }


    @Override
    protected void onResume() {
        TickspotApplication.getEventBus().register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        TickspotApplication.getEventBus().unregister(this);
        super.onPause();
    }
}
