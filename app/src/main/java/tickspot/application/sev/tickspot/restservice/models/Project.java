package tickspot.application.sev.tickspot.restservice.models;

import com.j256.ormlite.field.DatabaseField;

public class Project {
    public final static String COLUMN_NAME_ID = "id";
    public final static String COLUMN_NAME_NAME = "name";
    public final static String COLUMN_NAME_CLIENT_ID = "client_id";

    @DatabaseField(generatedId = true)
    public int generatedId;

    @DatabaseField(columnName = COLUMN_NAME_ID)
    public int id;

    @DatabaseField(columnName = COLUMN_NAME_NAME)
    public String name;

    @DatabaseField
    public double budget;

    @DatabaseField(columnName = COLUMN_NAME_CLIENT_ID)
    public long client_id;
}
