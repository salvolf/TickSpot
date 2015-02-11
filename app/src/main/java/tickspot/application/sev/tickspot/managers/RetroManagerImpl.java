package tickspot.application.sev.tickspot.managers;


import android.util.Log;

import com.google.inject.Inject;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tickspot.application.sev.tickspot.TickspotApplication;
import tickspot.application.sev.tickspot.database.MyDatabaseHelper;
import tickspot.application.sev.tickspot.preferences.Preferences;
import tickspot.application.sev.tickspot.restservice.ServiceFactory;
import tickspot.application.sev.tickspot.restservice.models.Client;
import tickspot.application.sev.tickspot.restservice.models.ClientList;
import tickspot.application.sev.tickspot.restservice.models.ProjectList;
import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.Task;
import tickspot.application.sev.tickspot.restservice.models.TaskList;

public class RetroManagerImpl implements RetroManager {
    @Inject
    private MyDatabaseHelper databaseHelper;

    private Callback<List<Project>> projectsResponseCallback = new Callback<List<Project>>() {
        @Override
        public void success(List<Project> projectsList, Response response) {
            //Post it into the bus:
            TickspotApplication.getEventBus().post(new ProjectList(projectsList));
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RETROFITERROR","RETROFITERROR: "+error.getMessage());
        }
    };

    private Callback<List<Task>> tasksResponseCallback = new Callback<List<Task>>() {
        @Override
        public void success(List<Task> tasksList, Response response) {
            //Store into the database:
            databaseHelper.clearTasks();
            for (Task task : tasksList) {
                databaseHelper.getTasksDao().create(task);
            }
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
