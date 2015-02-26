package tickspot.application.sev.tickspot.managers;


import android.util.Log;

import com.google.inject.Inject;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tickspot.application.sev.tickspot.TickspotApplication;
import tickspot.application.sev.tickspot.database.MyDatabaseHelper;
import tickspot.application.sev.tickspot.restservice.ServiceFactory;
import tickspot.application.sev.tickspot.restservice.models.Client;
import tickspot.application.sev.tickspot.restservice.models.ClientList;
import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.ProjectList;
import tickspot.application.sev.tickspot.restservice.models.Subscription;
import tickspot.application.sev.tickspot.restservice.models.SubscriptionsList;
import tickspot.application.sev.tickspot.restservice.models.Task;
import tickspot.application.sev.tickspot.restservice.models.TaskList;

public class RetroManagerImpl implements RetroManager {

    @Inject
    private MyDatabaseHelper databaseHelper;

    private Callback<List<Subscription>> subscriptionsResponseCallback = new Callback<List<Subscription>>() {
        @Override
        public void success(List<Subscription> subscriptions, Response response) {
            //Store into the database:
            databaseHelper.clearSubscriptions();
            for (Subscription subscription : subscriptions) {
                databaseHelper.getSubscriptionsDao().create(subscription);
            }
            //Post it into the bus:
            TickspotApplication.getEventBus().post(new SubscriptionsList(subscriptions));
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RETROFITERROR","RETROFITERROR: "+error.getMessage());
        }
    };



    private Callback<List<Project>> projectsResponseCallback = new Callback<List<Project>>() {
        @Override
        public void success(List<Project> projects, Response response) {
            //Store into the database:
            databaseHelper.clearProjects();
            for (Project project : projects) {
                databaseHelper.getProjectsDao().create(project);
            }
            //Post it into the bus:
            TickspotApplication.getEventBus().post(new ProjectList(projects));
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RETROFITERROR","RETROFITERROR: "+error.getMessage());
        }
    };

    private Callback<List<Task>> tasksResponseCallback = new Callback<List<Task>>() {
        @Override
        public void success(List<Task> tasks, Response response) {
            //Store into the database:
            //TODO Create asynctask for every task related to the database.
            databaseHelper.clearTasks();
            for (Task task : tasks) {
                databaseHelper.getTasksDao().create(task);
            }
            //Post it into the bus:
            TickspotApplication.getEventBus().post(new TaskList(tasks));
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("RETROFITERROR","RETROFITERROR: "+error.getMessage());
        }
    };

    private Callback<List<Client>> clientResponseCallback = new Callback<List<Client>>() {
        @Override
        public void success(List<Client> clients, Response response) {
            //Store into the database:
            databaseHelper.clearClients();
            for (Client client : clients) {
                databaseHelper.getClientsDao().create(client);
            }
            TickspotApplication.getEventBus().post(new ClientList(clients));
        }

        @Override
        public void failure(RetrofitError error) {
        Log.e("RETROFITERROR","RETROFITERROR: "+error.getMessage());
        }
    };

    @Override
    public void attemptLogin(String encodedCredentials) {
        ServiceFactory.getService(databaseHelper.getAccessToken()).getTokens(String.valueOf(encodedCredentials), subscriptionsResponseCallback);
    }

    @Override
    public void getProjects() {
        ServiceFactory.getService(databaseHelper.getAccessToken()).getProjects(String.valueOf(databaseHelper.getSelectedSubscriptionId()), projectsResponseCallback);
    }

    @Override
    public void getTasks() {
        ServiceFactory.getService(databaseHelper.getAccessToken()).getTasks(String.valueOf(databaseHelper.getSelectedSubscriptionId()), tasksResponseCallback);

    }

    @Override
    public void getClients() {
                ServiceFactory.getService(databaseHelper.getAccessToken()).getClients(String.valueOf(databaseHelper.getSelectedSubscriptionId()),clientResponseCallback);
    }
}
