package app.receiver.parse.parsereceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONObject;

import db.NotificationDBHelper;
import pojo.Notification;

public class NotificationReceiver extends ParsePushBroadcastReceiver
{
    public final static String LOGTAG = NotificationReceiver.class.getName();

    @Override
    protected void onPushOpen(Context context, Intent intent)
    {
        Log.i(LOGTAG, "Push notification opened");

        try {
            Bundle payloadBundle = intent.getExtras();
            if(payloadBundle != null)
            {
                String jsonPayload = payloadBundle.getString("com.parse.Data");
                JSONObject jsonObj = new JSONObject(jsonPayload);

                Notification notification = new Notification();
                notification.setPayload(jsonObj.toString());

                if (jsonObj.has("alert"))
                {
                    notification.setAlert(jsonObj.getString("alert"));
                }

                if (jsonObj.has("badge"))
                {
                    notification.setBadge(jsonObj.getString("badge"));
                }

                if (jsonObj.has("sound"))
                {
                    notification.setSound(jsonObj.getString("sound"));
                }

                if (jsonObj.has("title"))
                {
                    notification.setTitle(jsonObj.getString("title"));
                }

                Log.i(LOGTAG, "=================| Notification |=================");
                Log.i(LOGTAG, "alert => " + notification.getAlert());
                Log.i(LOGTAG, "badge => " + notification.getBadge());
                Log.i(LOGTAG, "sound => " + notification.getSound());
                Log.i(LOGTAG, "title => " + notification.getTitle());
                Log.i(LOGTAG, notification.getPayload());

                // SAVE NOTIFICATION
                NotificationDBHelper db = new NotificationDBHelper(context);
                long newId = db.addNotification(notification);

                if (newId != -1)
                {
                    Intent pushIntent = new Intent(context, NotificationActivity.class);
                    pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    pushIntent.putExtra("notification_id", newId);
                    context.startActivity(pushIntent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
