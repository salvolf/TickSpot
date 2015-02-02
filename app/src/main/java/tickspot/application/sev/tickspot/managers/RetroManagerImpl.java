package tickspot.application.sev.tickspot.managers;


import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tickspot.application.sev.tickspot.TickspotApplication;
import tickspot.application.sev.tickspot.model.ProjectList;
import tickspot.application.sev.tickspot.preferences.Preferences;
import tickspot.application.sev.tickspot.restservice.ServiceFactory;
import tickspot.application.sev.tickspot.restservice.models.ProjectResponse;

public class RetroManagerImpl implements RetroManager {

    private Callback<List<ProjectResponse>> projectsResponseCallback = new Callback<List<ProjectResponse>>() {
        @Override
        public void success(List<ProjectResponse> projectResponses, Response response) {
            //Post it into the bus:
            TickspotApplication.getEventBus().post(new ProjectList(projectResponses));
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
