package tickspot.application.sev.tickspot.restservice.models;

import com.j256.ormlite.field.DatabaseField;

/**
 * "id": 123738,
 * "name": "VikingCo (General)",
 * "archive": false,
 * "url": "https://www.tickspot.com/43757/api/v2/clients/123738.json",
 * "updated_at": "2014-07-02T10:24:38.000-04:00"
 */
public class Client {
    public final static String COLUMN_NAME_ID = "id";
    public final static String COLUMN_NAME_NAME = "name";
    public final static String COLUMN_NAME_SELECTED = "selected";

    @DatabaseField(columnName = COLUMN_NAME_ID)
    public long id;

    @DatabaseField(columnName = COLUMN_NAME_NAME)
    public String name;

    @DatabaseField
    public boolean archive;

}
