package tickspot.application.sev.tickspot.managers;


import android.util.Log;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tickspot.application.sev.tickspot.TickspotApplication;
import tickspot.application.sev.tickspot.preferences.Preferences;
import tickspot.application.sev.tickspot.restservice.ServiceFactory;
import tickspot.application.sev.tickspot.restservice.models.Client;
import tickspot.application.sev.tickspot.restservice.models.ClientList;
import tickspot.application.sev.tickspot.restservice.models.ProjectList;
import tickspot.application.sev.tickspot.restservice.models.ProjectOrTasks;
import tickspot.application.sev.tickspot.restservice.models.TaskList;

public class RetroManagerImpl implements RetroManager {

    private Callback<List<ProjectOrTasks>> projectsResponseCallback = new Callback<List<ProjectOrTasks>>() {
        @Override
        public void success(List<ProjectOrTasks> projectsList, Response response) {
            //Post it into the bus:
            TickspotApplication.getEventBus().post(new ProjectList(projectsList));
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RETROFITERROR","RETROFITERROR: "+error.getMessage());
        }
    };

    private Callback<List<ProjectOrTasks>> tasksResponseCallback = new Callback<List<ProjectOrTasks>>() {
        @Override
        public void success(List<ProjectOrTasks> tasksList, Response response) {
            //Post it into the bus:
            TickspotApplication.getEventBus().post(new TaskList(tasksList));
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RETROFITERROR","RETROFITERROR: "+error.getMessage());
        }
    };

    private Callback<List<Client>> clientResponseCallback = new Callback<List<Client>>() {
        @Override
        public void success(List<Client> clientList, Response response) {
            TickspotApplication.getEventBus().post(new ClientList(clientList));
        }

        @Override
        public void failure(RetrofitError error) {
        Log.e("RETROFITERROR","RETROFITERROR: "+error.getMessage());
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

    @Override
    public void getClients() {
                ServiceFactory.getService().getClients(String.valueOf(Preferences.getSubscriptionID()),clientResponseCallback);
    }
}
