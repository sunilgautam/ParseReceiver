package app.receiver.parse.parsereceiver;

import android.app.Application;

import utility.Utility;

public class ParseReceiver extends Application
{

    public static String PACKAGE_NAME;
    public static String SHARED_PREF_KEY;

    public static Setting PARSE_SETTING;

    @Override
    public void onCreate()
    {
        super.onCreate();

        PACKAGE_NAME = getApplicationContext().getPackageName();
        SHARED_PREF_KEY = PACKAGE_NAME + ".PREFERENCE_FILE_KEY";

        PARSE_SETTING = Utility.getSettings(getApplicationContext());

        Utility.initializeParse(this, PARSE_SETTING);
    }
}