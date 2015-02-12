package tickspot.application.sev.tickspot.restservice;

import com.google.inject.Inject;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Endpoints;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import tickspot.application.sev.tickspot.BuildConfig;
import tickspot.application.sev.tickspot.database.MyDatabaseHelper;
import tickspot.application.sev.tickspot.managers.RetroManager;

public class ServiceFactory {

   @Inject
   private  MyDatabaseHelper databaseHelper;

    private static RetroManager.Service authenticatedService;
    private static RetroManager.Service notAuthenticatedService;

    public static RetroManager.Service getService(String token) {
        if (token != null) {
            if (authenticatedService == null) {
                authenticatedService = getAuthenticatedAdapter(token).create(RetroManager.Service.class);
            }
            return authenticatedService;
        } else {
            if (notAuthenticatedService == null) {
                notAuthenticatedService = getNotAuthenticatedAdapter().create(RetroManager.Service.class);
            }
            return notAuthenticatedService;
        }
    }


    private static RestAdapter getAuthenticatedAdapter(final String token) {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                //String accessToken = Preferences.getAccessToken();
                requestFacade.addHeader("Authorization", "Token token=" + token);
                requestFacade.addHeader("User-Agent", "MyCoolApp (me@example.com)");
            }
        };

        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(requestInterceptor)
                .setEndpoint(Endpoints.newFixedEndpoint(BuildConfig.TICKSPOT_API))
                .setClient(new OkClient(new OkHttpClient())).build();
    }

    private static RestAdapter getNotAuthenticatedAdapter() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                requestFacade.addHeader("User-Agent", "MyCoolApp (me@example.com)");
            }
        };

        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(requestInterceptor)
                .setEndpoint(Endpoints.newFixedEndpoint(BuildConfig.TICKSPOT_API))
                .setClient(new OkClient(new OkHttpClient())).build();

    }


}
