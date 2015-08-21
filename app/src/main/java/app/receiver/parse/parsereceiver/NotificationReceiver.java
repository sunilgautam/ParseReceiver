package app.receiver.parse.parsereceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.parse.ParsePushBroadcastReceiver;

public class NotificationReceiver extends ParsePushBroadcastReceiver
{
    public final static String LOGTAG = NotificationReceiver.class.getName();

    @Override
    protected void onPushOpen(Context context, Intent intent)
    {
        Log.i(LOGTAG, "Push notification opened");

        try {
            Bundle extras = intent.getExtras();
            Intent pushIntent = new Intent(context, NotificationActivity.class);
            pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            pushIntent.putExtra("payload", extras);
            context.startActivity(pushIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
