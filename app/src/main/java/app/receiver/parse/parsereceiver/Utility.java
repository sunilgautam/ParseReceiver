package app.receiver.parse.parsereceiver;

import android.content.Context;
import android.content.SharedPreferences;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class Utility {

    private static final String APP_SET_APP_ID = "app_set_app_id";
    private static final String APP_SET_CLIENT_KEY = "app_set_client_key";

    public static Setting getSettings(Context context) {
        Setting setting = new Setting();
        SharedPreferences sharedPref = context.getSharedPreferences(ParseReceiver.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        setting.setAppId(sharedPref.getString(APP_SET_APP_ID, null));
        setting.setClientKey(sharedPref.getString(APP_SET_CLIENT_KEY, null));
        System.out.println(setting);
        return setting;
    }

    public static void setSettings(Context context, Setting setting) {
        SharedPreferences sharedPref = context.getSharedPreferences(ParseReceiver.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(APP_SET_APP_ID, setting.getAppId());
        editor.putString(APP_SET_CLIENT_KEY, setting.getClientKey());
        editor.commit();
    }

    public static void initializeParse(Context context, Setting setting) {
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

    public static void reInitializeParse(Context context, Setting setting) {
        Parse.initialize(context, setting.getAppId(), setting.getClientKey());
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
