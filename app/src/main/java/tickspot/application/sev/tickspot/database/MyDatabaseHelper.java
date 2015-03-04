package tickspot.application.sev.tickspot.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tickspot.application.sev.tickspot.Constants;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.TickspotApplication;
import tickspot.application.sev.tickspot.model.Preset;
import tickspot.application.sev.tickspot.restservice.models.Client;
import tickspot.application.sev.tickspot.restservice.models.Project;
import tickspot.application.sev.tickspot.restservice.models.Subscription;
import tickspot.application.sev.tickspot.restservice.models.Task;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
/**
 * This is called when the database is first created. Usually you should
 * call createTable statements here to create the tables that will store
 * your data.
 */
/**
 * This is called when your application is upgraded and it has a higher
 * version number. This allows you to adjust the various data to match the
 * new version number.
 */
/**
 * Close the database connections and clear any cached DAOs.
 */

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class MyDatabaseHelper extends OrmLiteSqliteOpenHelper {
    private final static String TAG = "MyDatabaseHelper";

    private RuntimeExceptionDao<Task, Integer> tasksRuntimeExceptionDao = null;
    private RuntimeExceptionDao<Project, Integer> projectsRuntimeExceptionDao = null;
    private RuntimeExceptionDao<Client, Integer> clientsRuntimeExceptionDao = null;
    private RuntimeExceptionDao<Subscription, Integer> subscriptionsRuntimeExceptionDao = null;
    private RuntimeExceptionDao<Preset, Integer> presetsRuntimeExceptionDao = null;


    public MyDatabaseHelper(Context context) {

        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

        try {
            //TODO CREATE TABLE HERE
            TableUtils.createTable(connectionSource, Subscription.class);
            TableUtils.createTable(connectionSource, Project.class);
            TableUtils.createTable(connectionSource, Task.class);
            TableUtils.createTable(connectionSource, Client.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            //TODO CLOSE TABLE HERE
            TableUtils.dropTable(connectionSource, Subscription.class, true);
            TableUtils.dropTable(connectionSource, Project.class, true);
            TableUtils.dropTable(connectionSource, Task.class, true);
            TableUtils.dropTable(connectionSource, Client.class, true);

            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public RuntimeExceptionDao<Subscription, Integer> getSubscriptionsDao() {

        if (subscriptionsRuntimeExceptionDao == null) {
            subscriptionsRuntimeExceptionDao = getRuntimeExceptionDao(Subscription.class);
        }
        return subscriptionsRuntimeExceptionDao;
    }

    public RuntimeExceptionDao<Task, Integer> getTasksDao() {

        if (tasksRuntimeExceptionDao == null) {
            tasksRuntimeExceptionDao = getRuntimeExceptionDao(Task.class);
        }
        return tasksRuntimeExceptionDao;
    }

    public RuntimeExceptionDao<Project, Integer> getProjectsDao() {

        if (projectsRuntimeExceptionDao == null) {
            projectsRuntimeExceptionDao = getRuntimeExceptionDao(Project.class);
        }
        return projectsRuntimeExceptionDao;
    }

    public RuntimeExceptionDao<Client, Integer> getClientsDao() {

        if (clientsRuntimeExceptionDao == null) {
            clientsRuntimeExceptionDao = getRuntimeExceptionDao(Client.class);
        }
        return clientsRuntimeExceptionDao;
    }

    public RuntimeExceptionDao<Preset, Integer> getPresetsDao() {

        if (presetsRuntimeExceptionDao == null) {
            presetsRuntimeExceptionDao = getRuntimeExceptionDao(Preset.class);
        }
        return presetsRuntimeExceptionDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        tasksRuntimeExceptionDao = null;
        projectsRuntimeExceptionDao = null;
        clientsRuntimeExceptionDao = null;
        subscriptionsRuntimeExceptionDao = null;
        presetsRuntimeExceptionDao = null;
    }

    public void clearSubscriptions() {
        try {
            TableUtils.clearTable(connectionSource, Subscription.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearTasks() {
        try {
            TableUtils.clearTable(connectionSource, Task.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearProjects() {
        try {
            TableUtils.clearTable(connectionSource, Project.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearClients() {
        try {
            TableUtils.clearTable(connectionSource, Client.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearPresets() {
        try {
            TableUtils.clearTable(connectionSource, Preset.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasksRelatedToProject(long projectId) {
        List<Task> tasks = new ArrayList<>();
        try {
            QueryBuilder<Task, Integer> statementBuilder = getTasksDao().queryBuilder();
            statementBuilder.where().eq(Task.COLUMN_NAME_PROJECT_ID, projectId);
            return statementBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public String getClientNameById(long clientId) {
        try {
            QueryBuilder<Client, Integer> statementBuilder = getClientsDao().queryBuilder();
            statementBuilder.where().eq(Client.COLUMN_NAME_ID, clientId);
            List<Client> clients = statementBuilder.query();
            if (clients.size() > 0) {
                return clients.get(0).name;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAccessToken() {
        List<Subscription> subscriptions;
        try {
            QueryBuilder<Subscription, Integer> statementBuilder = getSubscriptionsDao().queryBuilder();
            statementBuilder.where().eq(Subscription.COLUMN_NAME_SELECTED, true);
            subscriptions = statementBuilder.query();
            if (!subscriptions.isEmpty()) {
                return subscriptions.get(0).api_token;
            } else {
                statementBuilder = getSubscriptionsDao().queryBuilder();
                Subscription subscription = statementBuilder.queryForFirst();
                if (subscription != null) {

                    return subscription.api_token;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSelectedSubscription(String api_token) {
        try {
            UpdateBuilder<Subscription, Integer> updateBuilder = getSubscriptionsDao().updateBuilder();
            // set the criteria like you would a QueryBuilder
            updateBuilder.where().eq(Subscription.COLUMN_NAME_TOKEN, api_token);
            // update the value of your field(s)
            updateBuilder.updateColumnValue(Subscription.COLUMN_NAME_SELECTED, true);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getSelectedSubscriptionId() {
        List<Subscription> subscriptions;
        try {
            QueryBuilder<Subscription, Integer> statementBuilder = getSubscriptionsDao().queryBuilder();
            statementBuilder.where().eq(Subscription.COLUMN_NAME_SELECTED, true);
            subscriptions = statementBuilder.query();
            if (!subscriptions.isEmpty()) {
                return subscriptions.get(0).subscription_id;
            } else {
                statementBuilder = getSubscriptionsDao().queryBuilder();
                Subscription subscription = statementBuilder.queryForFirst();
                if (subscription != null) {
                    return subscription.subscription_id;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getSelectedSubscriptionCompany() {
        List<Subscription> subscriptions;
        try {
            QueryBuilder<Subscription, Integer> statementBuilder = getSubscriptionsDao().queryBuilder();
            statementBuilder.where().eq(Subscription.COLUMN_NAME_SELECTED, true);
            subscriptions = statementBuilder.query();
            if (!subscriptions.isEmpty()) {
                return subscriptions.get(0).company;
            } else {
                statementBuilder = getSubscriptionsDao().queryBuilder();
                Subscription subscription = statementBuilder.queryForFirst();
                if (subscription != null) {
                    return subscription.company;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public Preset getDefaultPreset() throws SQLException {
        if (getProjectsDao().isTableExists() && getTasksDao().isTableExists()) {
            QueryBuilder<Project, Integer> statementBuilderProject = getProjectsDao().queryBuilder();
            Project firstProject = statementBuilderProject.queryForFirst();
            QueryBuilder<Task, Integer> statementBuilderTask = getTasksDao().queryBuilder();
            Task firstTask = statementBuilderTask.queryForFirst();
            QueryBuilder<Subscription, Integer> statementBuilderSubscriptions = getSubscriptionsDao().queryBuilder();
            Subscription firstSubscription = statementBuilderSubscriptions.queryForFirst();
            return new Preset(firstProject, firstTask, firstSubscription, 8);
        } else {
            return null;
        }
    }*/

    public List<Preset> getPresetsIfPresent() throws SQLException {
        List<Preset> presets = new ArrayList<Preset>();
        if (getPresetsDao().isTableExists()) {
            QueryBuilder<Preset, Integer> statementBuilderPreset = getPresetsDao().queryBuilder();
            presets = getPresetsDao().queryForAll();
        }
        else{
        }
        return presets;
    }

    public List<Task> getTasks() {
        if (getTasksDao().isTableExists()) {
            List<Task> tasks = getTasksDao().queryForAll();
            return tasks;
        }
        Log.e(TAG, "Tasks table does not exists");
        return null;
    }


    public List<Project> getProjects() {
        if (getProjectsDao().isTableExists()) {
            List<Project> projects = getProjectsDao().queryForAll();
            return projects;
        }
        Log.e(TAG, "Projects table does not exists");
        return null;
    }

    public List<Subscription> getSubscriptions() {
        if (getSubscriptionsDao().isTableExists()) {
            List<Subscription> subscriptions = getSubscriptionsDao().queryForAll();
            return subscriptions;
        }
        Log.e(TAG, "Subscription table does not exists");
        return null;
    }

    public List<Client> getClients() {
        if (getClientsDao().isTableExists()) {
            List<Client> clients = getClientsDao().queryForAll();

            return clients;
        }
        Log.e(TAG, "Clients table does not exists");
        return null;

    }

    public ArrayList<String> getClientsName() {
        ArrayList<String> clientsName=new ArrayList<>();
        if (getClientsDao().isTableExists()) {
            List<Client> clients = getClientsDao().queryForAll();
            for(Client client : clients){
                clientsName.add(client.name);
            }
            return clientsName;
        }
        Log.e(TAG, "Clients table does not exists");
        return null;

    }

    public List<Project> getProjectsByClientId(long id) {
        List<Project> projects = new ArrayList<Project>();
        try {
            if (getProjectsDao().isTableExists()) {
                QueryBuilder<Project, Integer> statementBuilderProject = getProjectsDao().queryBuilder();
                statementBuilderProject.where().eq(Project.COLUMN_NAME_CLIENT_ID, id);
                projects = statementBuilderProject.query();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public ArrayList<String> getProjectsSortedByClient() {
        ArrayList<String> projectsSortedByClient = new ArrayList<String>();
        List<Client> clients = getClients();
        for (int i = 0; i < clients.size(); i++) {
            String name = clients.get(i).name;
            long id = clients.get(i).id;
            projectsSortedByClient.add(String.valueOf(name));
            for (int j = 0; j < getProjectsByClientId(id).size(); j++) {
                projectsSortedByClient.add(String.valueOf(getProjectsByClientId(id).get(j).name));
            }
        }
        projectsSortedByClient.add(TickspotApplication.getContext().getString(R.string.select_project));
        return projectsSortedByClient;
    }

    public long getProjectIdByName(String name) {
        long id = 0L;
        if (getProjectsDao().isTableExists()) {
            try {
                QueryBuilder<Project, Integer> statementBuilderProject = getProjectsDao().queryBuilder();
                statementBuilderProject.where().eq(Project.COLUMN_NAME_NAME, name);
                return statementBuilderProject.queryForFirst().id;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }
}
