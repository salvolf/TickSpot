package tickspot.application.sev.tickspot.roboguice;


import com.google.inject.AbstractModule;

import tickspot.application.sev.tickspot.database.MyDatabaseHelper;
import tickspot.application.sev.tickspot.database.MyDatabaseHelperProvider;
import tickspot.application.sev.tickspot.managers.RetroManager;
import tickspot.application.sev.tickspot.managers.RetroManagerImpl;

public class BindingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(RetroManager.class).to(RetroManagerImpl.class).asEagerSingleton();
        bind(MyDatabaseHelper.class).toProvider(MyDatabaseHelperProvider.class).asEagerSingleton();
    }
}