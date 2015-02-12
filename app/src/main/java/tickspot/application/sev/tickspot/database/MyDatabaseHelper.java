package tickspot.application.sev.tickspot.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    private RuntimeExceptionDao<Task, Integer> tasksRuntimeExceptionDao = null;
    private RuntimeExceptionDao<Project, Integer> projectsRuntimeExceptionDao = null;
    private RuntimeExceptionDao<Client, Integer> clientsRuntimeExceptionDao = null;
    private RuntimeExceptionDao<Subscription, Integer> subscriptionsRuntimeExceptionDao = null;


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

    /*public String getSelectedClientName() {
        String clientName = "";
        try {
            QueryBuilder<Client, Integer> statementBuilder = getClientsDao().queryBuilder();
            statementBuilder.where().eq(Client.COLUMN_NAME_SELECTED, true);
            List<Client> clients = statementBuilder.query();
            if (!clients.isEmpty()) {
                return clients.get(0).name;
            } else {
                //If no client selected return just the first one.
                statementBuilder.where().eq(Client.COLUMN_NAME_SELECTED, false);
                clients = statementBuilder.query();
                return clients.get(0).name;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientName;
    }

    public void setSelectedClient(long clientId) {
        try {

            UpdateBuilder<Client, Integer> updateBuilder = getClientsDao().updateBuilder();
            // set the criteria like you would a QueryBuilder
            updateBuilder.where().eq(Client.COLUMN_NAME_ID, clientId);
            // update the value of your field(s)
            updateBuilder.updateColumnValue(Client.COLUMN_NAME_SELECTED , true);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

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

}
