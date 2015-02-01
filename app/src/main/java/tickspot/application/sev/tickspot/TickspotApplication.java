package tickspot.application.sev.tickspot;

import android.app.Application;
import android.content.Context;

public class TickspotApplication extends Application {

    private static TickspotApplication instance;

    public static Context getContext() {
        if (instance != null) {
            return instance.getApplicationContext();
        } else {
            return null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
