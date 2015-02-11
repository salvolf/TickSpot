package tickspot.application.sev.tickspot.restservice.models;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Sev on 09/02/15.
 */
public class Project {
    public final static String COLUMN_NAME_ID = "id";
    public final static String COLUMN_NAME_NAME = "name";

    @DatabaseField(columnName = COLUMN_NAME_ID)
    public int id;

    @DatabaseField(columnName = COLUMN_NAME_NAME)
    public String name;

    @DatabaseField
    public double budget;
}
