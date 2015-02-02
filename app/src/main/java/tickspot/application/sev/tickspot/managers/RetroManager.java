package tickspot.application.sev.tickspot.managers;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import tickspot.application.sev.tickspot.restservice.models.ProjectResponse;
import tickspot.application.sev.tickspot.restservice.models.Subscription;

public interface RetroManager {
    public interface Service {
        @GET("/api/v2/roles.json")
        public ArrayList<Subscription> getTokens(@Header("Authorization") String authorization);

        @GET("/{subscription_id}/api/v2/projects.json")
        public void getProjects(@Path("subscription_id") String subscription_id, retrofit.Callback<List<ProjectResponse>> callback);
    }
    public void getProjects();
}


