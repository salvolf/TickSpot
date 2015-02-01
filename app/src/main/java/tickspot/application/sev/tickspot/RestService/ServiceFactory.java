package tickspot.application.sev.tickspot.RestService;

import com.squareup.okhttp.OkHttpClient;

import retrofit.Endpoints;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import tickspot.application.sev.tickspot.BuildConfig;
import tickspot.application.sev.tickspot.Preferences.Preferences;

/**
 * Created by Sev on 31/01/15.
 */
public class ServiceFactory {
    private static RestService authenticatedService;
    private static RestService notAuthenticatedService;

    public static RestService getService() {
        if (Preferences.getAccessToken() != null) {
            if (authenticatedService == null) {
                authenticatedService = getAuthenticatedAdapter().create(RestService.class);
            }
            return authenticatedService;
        } else {
            if (notAuthenticatedService == null) {
                notAuthenticatedService = getNotAuthenticatedAdapter().create(RestService.class);
            }
            return notAuthenticatedService;
        }
    }


    private static RestAdapter getAuthenticatedAdapter() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                //String accessToken = Preferences.getAccessToken();
                requestFacade.addHeader("Authorization", "Token token=" + Preferences.getAccessToken());
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
