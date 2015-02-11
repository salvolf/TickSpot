package tickspot.application.sev.tickspot.database;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.j256.ormlite.android.apptools.OpenHelperManager;

@Singleton
public class MyDatabaseHelperProvider implements Provider<MyDatabaseHelper> {

	@Inject
	protected Context mContext;

	@Override
	public MyDatabaseHelper get() {
		return (MyDatabaseHelper) OpenHelperManager.getHelper(mContext, MyDatabaseHelper.class);
	}
}