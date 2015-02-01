package tickspot.application.sev.tickspot.Preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import tickspot.application.sev.tickspot.TickspotApplication;

public class Preferences {
    private static final String TAG = "Preferences";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String SUBSCRIPTION_ID = "subscription_id";

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;
    private static long subscriptionID;

    public static SharedPreferences getInstance() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(TickspotApplication.getContext());
            editor = prefs.edit();
        }
        return prefs;
    }

    private static SharedPreferences.Editor getEditorInstance() {
        if (prefs == null || editor == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(TickspotApplication.getContext());
            editor = prefs.edit();
        }
        return editor;
    }


    public static String getAccessToken() {
        return getInstance().getString(ACCESS_TOKEN, null);
    }

    public static void setAccessToken(String accessToken) {
        getEditorInstance().putString(ACCESS_TOKEN, accessToken);
        getEditorInstance().apply();
    }

    public static void setSubscriptionID(long subscriptionID) {
        getEditorInstance().putLong(SUBSCRIPTION_ID, subscriptionID);
        getEditorInstance().apply();
    }

    public static long getSubscriptionID() {
        return getInstance().getLong(SUBSCRIPTION_ID,0);
    }
}
