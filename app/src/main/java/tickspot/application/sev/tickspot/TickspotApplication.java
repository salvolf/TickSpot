package tickspot.application.sev.tickspot;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;

import tickspot.application.sev.tickspot.bus.MainThreadBus;

public class TickspotApplication extends Application {

    private static TickspotApplication instance;
    private static MainThreadBus eventBus;

    public static Context getContext() {
        if (instance != null) {
            return instance.getApplicationContext();
        } else {
            return null;
        }
    }

    public static final MainThreadBus getEventBus() {
        if (eventBus == null) {
            eventBus = new MainThreadBus(new Bus());
        }
        return eventBus;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
