package tickspot.application.sev.tickspot.managers;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import tickspot.application.sev.tickspot.restservice.models.Client;
import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.Subscription;
import tickspot.application.sev.tickspot.restservice.models.Task;

public interface RetroManager {

    public interface Service {
        @GET("/api/v2/roles.json")
        public ArrayList<Subscription> getTokens(@Header("Authorization") String authorization);

        @GET("/{subscription_id}/api/v2/projects.json")
        public void getProjects(@Path("subscription_id") String subscription_id, retrofit.Callback<List<Project>> callback);

        @GET("/{subscription_id}/api/v2/tasks.json")
        public void getTasks(@Path("subscription_id") String subscription_id, retrofit.Callback<List<Task>> callback);

        @GET("/{subscription_id}/api/v2/clients.json")
        public void getClients(@Path("subscription_id") String subscription_id,retrofit.Callback<List<Client>> callback);
    }

    public void getProjects();

    public void getTasks();

    public void getClients();
}


