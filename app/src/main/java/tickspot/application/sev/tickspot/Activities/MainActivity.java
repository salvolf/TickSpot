package tickspot.application.sev.tickspot.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.managers.RetroManager;
import tickspot.application.sev.tickspot.model.ProjectList;

public class MainActivity extends BaseActivity {

    @Inject
    private RetroManager retroManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retroManager.getProjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   @Subscribe
    public void onProjectsApiCallDone(ProjectList projectList){
       Log.e ("TEST","Received projectResponse through the bus");
   }
}
