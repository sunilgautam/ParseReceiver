package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pojo.Notification;
import utility.Utility;

public class NotificationDBHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "parse_receiver";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_TABLE_CREATE = "CREATE TABLE " + NotificationEntry.TABLE_NAME + " (" +
            NotificationEntry._ID + " INTEGER PRIMARY KEY," +
            NotificationEntry.COLUMN_NAME_ALERT + TEXT_TYPE + COMMA_SEP +
            NotificationEntry.COLUMN_NAME_BADGE + TEXT_TYPE + COMMA_SEP +
            NotificationEntry.COLUMN_NAME_SOUND + TEXT_TYPE + COMMA_SEP +
            NotificationEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            NotificationEntry.COLUMN_NAME_PAYLOAD + TEXT_TYPE + COMMA_SEP +
            NotificationEntry.COLUMN_NAME_CREATED_AT + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + NotificationEntry.TABLE_NAME;

    public NotificationDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long addNotification(Notification notification)
    {
        SQLiteDatabase db = null;
        try
        {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(NotificationEntry.COLUMN_NAME_ALERT, notification.getAlert());
            values.put(NotificationEntry.COLUMN_NAME_BADGE, notification.getBadge());
            values.put(NotificationEntry.COLUMN_NAME_SOUND, notification.getSound());
            values.put(NotificationEntry.COLUMN_NAME_TITLE, notification.getTitle());
            values.put(NotificationEntry.COLUMN_NAME_PAYLOAD, notification.getPayload());
            values.put(NotificationEntry.COLUMN_NAME_CREATED_AT, Utility.getReminderDate(new Date()));

            return db.insert(NotificationEntry.TABLE_NAME, null, values);
        } catch (Exception ex)
        {
            return -1;
        } finally
        {
            if (db != null)
            {
                db.close();
            }
        }
    }

    public Notification getNotification(long id)
    {
        SQLiteDatabase db = null;
        Notification notification = null;
        Cursor cursor = null;

        try
        {
            db = this.getReadableDatabase();

            cursor = db.query(NotificationEntry.TABLE_NAME,
                    new String[]{
                            NotificationEntry._ID,
                            NotificationEntry.COLUMN_NAME_ALERT,
                            NotificationEntry.COLUMN_NAME_BADGE,
                            NotificationEntry.COLUMN_NAME_SOUND,
                            NotificationEntry.COLUMN_NAME_TITLE,
                            NotificationEntry.COLUMN_NAME_PAYLOAD,
                            NotificationEntry.COLUMN_NAME_CREATED_AT,
                    }, NotificationEntry._ID + "=?",
                    new String[]{
                            String.valueOf(id)
                    },
                    null,
                    null,
                    null,
                    null);
            if (cursor != null)
                cursor.moveToFirst();

            notification = new Notification(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6));
        } catch (Exception ex)
        {

        } finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }

        return notification;
    }

    public List<Notification> getAllNotifications()
    {
        List<Notification> notificationList = new ArrayList<Notification>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try
        {
            db = this.getReadableDatabase();
            cursor = db.query(NotificationEntry.TABLE_NAME,
                    new String[]{
                            NotificationEntry._ID,
                            NotificationEntry.COLUMN_NAME_ALERT,
                            NotificationEntry.COLUMN_NAME_BADGE,
                            NotificationEntry.COLUMN_NAME_SOUND,
                            NotificationEntry.COLUMN_NAME_TITLE,
                            NotificationEntry.COLUMN_NAME_PAYLOAD,
                            NotificationEntry.COLUMN_NAME_CREATED_AT
                    },
                    null,
                    null,
                    null,
                    null,
                    NotificationEntry._ID + " DESC",
                    null);
            if (cursor != null)
            {
                if (cursor.moveToFirst())
                {
                    do
                    {
                        Notification notification = new Notification(
                                Integer.parseInt(cursor.getString(0)),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6)
                        );

                        notificationList.add(notification);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }

        return notificationList;
    }

    public void deleteNotification(Notification notification)
    {
        SQLiteDatabase db = null;
        try
        {
            db = this.getWritableDatabase();

            db.delete(NotificationEntry.TABLE_NAME, NotificationEntry._ID + " = ?", new String[]{String.valueOf(notification.getId())});
        } catch (Exception ex)
        {

        } finally
        {
            if (db != null)
            {
                db.close();
            }
        }
    }

    public void deleteAllNotifications()
    {
        SQLiteDatabase db = null;
        try
        {
            db = this.getWritableDatabase();

            db.delete(NotificationEntry.TABLE_NAME, null, null);
        } catch (Exception ex)
        {

        } finally
        {
            if (db != null)
            {
                db.close();
            }
        }

    }

    public int getNotificationCount()
    {
        String countQuery = "SELECT * FROM " + NotificationEntry.TABLE_NAME;
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try
        {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(countQuery, null);
        } catch (Exception ex)
        {

        } finally
        {
            if (db != null)
            {
                db.close();
            }
        }

        return (cursor == null) ? 0 : cursor.getCount();
    }
}
