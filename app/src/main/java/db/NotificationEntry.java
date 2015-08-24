package db;

import android.provider.BaseColumns;

public class NotificationEntry implements BaseColumns
{
    public static final String TABLE_NAME = "notifications";
    public static final String COLUMN_NAME_ALERT = "alert";
    public static final String COLUMN_NAME_BADGE = "badge";
    public static final String COLUMN_NAME_SOUND = "sound";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_PAYLOAD = "payload";
    public static final String COLUMN_NAME_CREATED_AT = "created_at";
}
