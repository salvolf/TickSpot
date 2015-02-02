package tickspot.application.sev.tickspot.managers;


import android.util.Log;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tickspot.application.sev.tickspot.preferences.Preferences;
import tickspot.application.sev.tickspot.restservice.models.ProjectResponse;
import tickspot.application.sev.tickspot.restservice.ServiceFactory;

public class RetroManagerImpl implements RetroManager {

    private Callback<List<ProjectResponse>> projectsResponseCallback = new Callback<List<ProjectResponse>>() {
        @Override
        public void success(List<ProjectResponse> projectResponse, Response response) {
            Log.e("TEST", "Va meglio?");
        }

        @Override
        public void failure(RetrofitError error) {
            // TODO: What to do?
        }
    };

    @Override
    public void getProjects() {
        ServiceFactory.getService().getProjects(String.valueOf(Preferences.getSubscriptionID()), projectsResponseCallback);

    }
}
