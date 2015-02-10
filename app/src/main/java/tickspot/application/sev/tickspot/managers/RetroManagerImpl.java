package tickspot.application.sev.tickspot.managers;


import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tickspot.application.sev.tickspot.TickspotApplication;
import tickspot.application.sev.tickspot.model.ProjectList;
import tickspot.application.sev.tickspot.model.TaskList;
import tickspot.application.sev.tickspot.preferences.Preferences;
import tickspot.application.sev.tickspot.restservice.ServiceFactory;
import tickspot.application.sev.tickspot.restservice.models.ProjectOrTasks;

public class RetroManagerImpl implements RetroManager {

    private Callback<List<ProjectOrTasks>> projectsResponseCallback = new Callback<List<ProjectOrTasks>>() {
        @Override
        public void success(List<ProjectOrTasks> projectResponses, Response response) {
            //Post it into the bus:
            TickspotApplication.getEventBus().post(new ProjectList(projectResponses));
        }

        @Override
        public void failure(RetrofitError error) {
            // TODO: What to do?
        }
    };

    private Callback<List<ProjectOrTasks>> tasksResponseCallback = new Callback<List<ProjectOrTasks>>() {
        @Override
        public void success(List<ProjectOrTasks> tasksResponses, Response response) {
            //Post it into the bus:
            TickspotApplication.getEventBus().post(new TaskList(tasksResponses));
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

    @Override
    public void getTasks() {
        ServiceFactory.getService().getTasks(String.valueOf(Preferences.getSubscriptionID()), tasksResponseCallback);

    }
}
