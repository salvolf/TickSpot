package tickspot.application.sev.tickspot.restservice.models;

import com.j256.ormlite.field.DatabaseField;

/**
 * "subscription_id":15,
 * "company":"Empire",
 * "api_token":"f67158e7bf3d7a0fcaf9d258ace8b468"
 */
public class Subscription {

    public final static String COLUMN_NAME_ID = "subscription_id";
    public final static String COLUMN_NAME_COMPANY = "company";
    public final static String COLUMN_NAME_TOKEN = "api_token";
    public final static String COLUMN_NAME_SELECTED = "selected";
    public final static String COLUMN_NAME_AUTOGENERATE_ID = "id";

    @DatabaseField(columnName = COLUMN_NAME_AUTOGENERATE_ID,generatedId = true)
    private long id;

    @DatabaseField(columnName = COLUMN_NAME_ID)
    public long subscription_id;

    @DatabaseField(columnName = COLUMN_NAME_COMPANY)
    public String company;

    @DatabaseField(columnName = COLUMN_NAME_TOKEN)
    public String api_token;

    @DatabaseField(columnName = COLUMN_NAME_SELECTED)
    public boolean selected;

}
