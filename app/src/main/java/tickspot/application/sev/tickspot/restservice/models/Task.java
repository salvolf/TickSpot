package tickspot.application.sev.tickspot.restservice.models;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Sev on 10/02/15.
 */
public class Task {
    public final static String COLUMN_NAME_ID = "id";
    public final static String COLUMN_NAME_NAME = "name";
    public final static String COLUMN_NAME_PROJECT_ID = "project_id";

    @DatabaseField(generatedId = true)
    public int generatedId;

    @DatabaseField(columnName = COLUMN_NAME_ID)
    public int id;

    @DatabaseField(columnName = COLUMN_NAME_NAME)
    public String name;

    @DatabaseField
    public double budget;

    @DatabaseField(columnName = COLUMN_NAME_PROJECT_ID)
    public long project_id;
}
