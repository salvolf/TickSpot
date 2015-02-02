package tickspot.application.sev.tickspot.bus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

import java.util.ArrayList;

/**
 * Created by Sev on 03/02/15.
 */
public class MainThreadBus extends Bus {
    private final Bus mBus;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private ArrayList<Object> listeners = new ArrayList<Object>();

    public MainThreadBus(final Bus bus) {
        if (bus == null) {
            throw new NullPointerException("bus must not be null");
        }
        mBus = bus;
    }

    @Override
    public void register(Object obj) {
        if (!listeners.contains(obj)) {
            listeners.add(obj);
        }

        if (mBus != null) {
            mBus.register(obj);
        }
    }

    @Override
    public void unregister(Object obj) {
        if (listeners.contains(obj)) {
            listeners.remove(obj);
        }

        if (mBus != null) {
            try {
                mBus.unregister(obj);
            } catch (IllegalArgumentException ex) {
                // Object was not registered
            }
        }
    }

    @Override
    public void post(final Object event) {
        if (event != null && !listeners.isEmpty()) {
            try {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    mBus.post(event);
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mBus.post(event);
                        }
                    });
                }
            } catch (RuntimeException ex) {
                // oops somethin went wrong with the otto bus!
            }
        }
    }
}

