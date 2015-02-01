package tickspot.application.sev.tickspot.RestService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import tickspot.application.sev.tickspot.Constants;

/**
 * Created by Sev on 01/02/15.
 */
public class ApiUtils {

    public static void doApiCall(Context context, CallType type) {
        doApiCall(context, type, null);
    }

    public static void doApiCall(Context context, CallType type, Bundle extras) {
        Intent mServiceIntent = new Intent(context, ApiIntentService.class);
        if (extras != null) {
            mServiceIntent.putExtras(extras);
        }
        mServiceIntent.putExtra(Constants.CALL_TYPE, type);
        context.startService(mServiceIntent);
    }
}
