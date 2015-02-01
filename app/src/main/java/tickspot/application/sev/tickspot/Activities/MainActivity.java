package tickspot.application.sev.tickspot.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tickspot.application.sev.tickspot.Preferences.Preferences;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.RestService.Models.ProjectResponse;
import tickspot.application.sev.tickspot.RestService.ServiceFactory;

public class MainActivity extends ActionBarActivity {
    private Callback<List<ProjectResponse>> projectsResponseCallback = new Callback<List<ProjectResponse>>() {
        @Override
        public void success(List<ProjectResponse> projectResponse, Response response) {
            Log.e("TEST","NOW?");
        }

        @Override
        public void failure(RetrofitError error) {
            // TODO: What to do?
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ApiUtils.doApiCall(getApplicationContext(), CallType.PROJECTS);
        ServiceFactory.getService().getProjects(String.valueOf(Preferences.getSubscriptionID()),projectsResponseCallback);
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
}
