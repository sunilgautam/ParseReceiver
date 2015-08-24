package utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.receiver.parse.parsereceiver.ParseReceiver;
import app.receiver.parse.parsereceiver.Setting;

public class Utility
{
    private static final String APP_SET_APP_ID = "app_set_app_id";
    private static final String APP_SET_CLIENT_KEY = "app_set_client_key";
    private static final String APP_SET_CONFIG_KEY = "app_set_config_key";

    private static final DateFormat utilDateFormatter = new SimpleDateFormat("dd-MM-yyyy, hh:mm a");

    public static String getReminderDate(Date crDate)
    {
        try
        {
            return utilDateFormatter.format(crDate);
        }
        catch (Exception ex)
        {
            return "";
        }
    }

    public static Setting getSettings(Context context)
    {
        Setting setting = new Setting();
        SharedPreferences sharedPref = context.getSharedPreferences(ParseReceiver.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        setting.setAppId(sharedPref.getString(APP_SET_APP_ID, ""));
        setting.setClientKey(sharedPref.getString(APP_SET_CLIENT_KEY, ""));
        System.out.println(setting);
        return setting;
    }

    public static void setSettings(Context context, Setting setting)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(ParseReceiver.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(APP_SET_APP_ID, setting.getAppId());
        editor.putString(APP_SET_CLIENT_KEY, setting.getClientKey());
        editor.commit();
    }

    public static JSONArray getDataConfig(Context context)
    {
        try
        {
            SharedPreferences sharedPref = context.getSharedPreferences(ParseReceiver.SHARED_PREF_KEY, Context.MODE_PRIVATE);
            String strJson = sharedPref.getString(APP_SET_CONFIG_KEY, "0");
            if (strJson != null)
            {
                JSONArray jsonData = new JSONArray(strJson);
                return jsonData;
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public static void setDataConfig(Context context, JSONArray jsonData)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(ParseReceiver.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(APP_SET_CONFIG_KEY, jsonData.toString());
        editor.commit();
    }

    public static void initializeParse(Context context, Setting setting)
    {
        if (!setting.getAppId().equals("") && !setting.getClientKey().equals(""))
        {
            // Initialize Crash Reporting.
            ParseCrashReporting.enable(context);

            // Add your initialization code here
            Parse.initialize(context, setting.getAppId(), setting.getClientKey());
            ParseInstallation.getCurrentInstallation().saveInBackground();

            ParseUser.enableAutomaticUser();
            ParseACL defaultACL = new ParseACL();

            // If you would like all objects to be private by default, remove this line.
            defaultACL.setPublicReadAccess(true);

            ParseACL.setDefaultACL(defaultACL, true);
        }
    }

    public static void reInitializeParse(Context context, Setting setting)
    {
        if (!setting.getAppId().equals("") && !setting.getClientKey().equals(""))
        {
            Parse.initialize(context, setting.getAppId(), setting.getClientKey());
            ParseInstallation.getCurrentInstallation().saveInBackground();
        }
    }
}
