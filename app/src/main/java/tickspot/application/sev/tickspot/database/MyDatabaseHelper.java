package tickspot.application.sev.tickspot.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import retrofit.Endpoint;
import tickspot.application.sev.tickspot.Constants;

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


    private RuntimeExceptionDao<Endpoint, Integer> endpointRuntimeExceptionDao = null;


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
            TableUtils.createTable(connectionSource, Endpoint.class);

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
            TableUtils.dropTable(connectionSource, Endpoint.class, true);

            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
    }

}
