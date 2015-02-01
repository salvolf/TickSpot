package tickspot.application.sev.tickspot.RestService;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import tickspot.application.sev.tickspot.RestService.Models.ProjectResponse;
import tickspot.application.sev.tickspot.RestService.Models.Subscription;

public interface RestService {

    @GET("/api/v2/roles.json")
    public ArrayList<Subscription> getTokens(@Header("Authorization") String authorization);

    @GET("/{subscription_id}/api/v2/projects.json")
    public void getProjects(@Path ("subscription_id") String subscription_id,retrofit.Callback<List<ProjectResponse>>callback);
}
