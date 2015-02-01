package tickspot.application.sev.tickspot.RestService;

import android.content.Intent;
import android.os.Bundle;

import retrofit.RetrofitError;
import roboguice.service.RoboIntentService;
import tickspot.application.sev.tickspot.Constants;

/**
 * Created by Sev on 01/02/15.
 */
public class ApiIntentService extends RoboIntentService {

    public ApiIntentService(String name) {
        super("BackgroundIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            CallType type = (CallType) extras.getSerializable(Constants.CALL_TYPE);
            switch (type){
                case PROJECTS:
                    makeProjectApiCall();
                    break;
            }
        }

    }

    private void makeProjectApiCall() {
        try {
            //List<ProjectResponse> projectResponses = ServiceFactory.getService().getProjects();
        } catch (RetrofitError e) {
        }
    }
}