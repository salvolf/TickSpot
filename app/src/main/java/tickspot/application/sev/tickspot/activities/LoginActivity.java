package tickspot.application.sev.tickspot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import tickspot.application.sev.tickspot.Credentials;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.TickspotApplication;
import tickspot.application.sev.tickspot.managers.RetroManager;
import tickspot.application.sev.tickspot.restservice.models.Client;
import tickspot.application.sev.tickspot.restservice.models.ClientList;
import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.ProjectList;
import tickspot.application.sev.tickspot.restservice.models.Subscription;
import tickspot.application.sev.tickspot.restservice.models.SubscriptionsList;
import tickspot.application.sev.tickspot.restservice.models.Task;
import tickspot.application.sev.tickspot.restservice.models.TaskList;


public class LoginActivity extends RoboActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private final static String TAG = "LoginActivity";

    @Inject
    private RetroManager retroManager;

    @InjectView(R.id.email)
    private EditText mEmailView;

    @InjectView(R.id.password)
    private EditText mPasswordView;

    @InjectView(R.id.sign_in_button)
    private Button mSignInButton;

    @InjectView(R.id.progress)
    private View mProgress;

    List<Subscription> subscriptions;

    List<Project> projects;

    List<Task> tasks;

    List<Client> clients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPasswordView.setOnEditorActionListener(this);
        mSignInButton.setOnClickListener(this);
        //TODO Remove it from here later on
        String encodedCredentials = "Basic " + Base64.encodeToString(Credentials.credentials.getBytes(), Base64.NO_WRAP);
        retroManager.attemptLogin(encodedCredentials);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TickspotApplication.getEventBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TickspotApplication.getEventBus().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "Try to authenticate");
        attemptToLogin();
    }

    private void attemptToLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        if (TextUtils.isEmpty(mEmailView.getText())) {
            mEmailView.setError(getString(R.string.error_field_required));
            mEmailView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mPasswordView.getText())) {
            mPasswordView.setError(getString(R.string.error_field_required));
            mPasswordView.requestFocus();
            return;
        }


        /*if (mLoginTask != null && (mLoginTask.getStatus() == AsyncTask.Status.RUNNING || mLoginTask.getStatus() == AsyncTask.Status.PENDING)) {
            mLoginTask.cancel(true);
        }*/

        String encodedCredentials = "Basic " + Base64.encodeToString(Credentials.credentials.getBytes(), Base64.NO_WRAP);
        retroManager.attemptLogin(encodedCredentials);
        //mLoginTask = new UserLoginTask();
        //mLoginTask.execute();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
            attemptToLogin();
        }
        return false;
    }

    private void showProgress(boolean show) {
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        mSignInButton.setEnabled(!show);
    }

    @Subscribe
    public void onLoginDone(SubscriptionsList subscriptionsList) {
        Log.e("TEST", "Login done");
        this.subscriptions = subscriptionsList.subscriptions;
        retroManager.getClients();
        retroManager.getProjects();
        retroManager.getTasks();
    }

    @Subscribe
    public void onProjectsApiCallDone(ProjectList projectList) {
        Log.e("TEST", "Received ProjectList through the bus");
        this.projects = projectList.projects;
        //responsesManager.setProjects(projectList.projects);
        areAllApiCallDone();
    }

    @Subscribe
    public void onTasksApiCallDone(TaskList taskList) {
        Log.e("TEST", "Received TaskList through the bus");
        this.tasks = taskList.tasks;
        //responsesManager.setTasks(taskList.tasks);
        areAllApiCallDone();
    }

    @Subscribe
    public void onClientsApiCallDone(ClientList clientList) {
        Log.e("TEST", "Received ClientList through the bus");
        this.clients = clientList.clients;
        //responsesManager.setClients(clientList.clients);
        areAllApiCallDone();
    }

    private void areAllApiCallDone() {
        if(tasks!=null && projects!=null && clients!=null && subscriptions!=null) {
            showProgress(false);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
