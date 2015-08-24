package app.receiver.parse.parsereceiver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import db.NotificationDBHelper;
import pojo.Notification;

public class NotificationActivity extends Activity
{
    public final static String LOGTAG = NotificationActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_notification);

            long notification_id = getIntent().getLongExtra("notification_id", -1);
            if (notification_id != -1)
            {
                Log.i(LOGTAG, "Showing notification => " + notification_id);
                NotificationDBHelper db = new NotificationDBHelper(this);
                Notification notification = db.getNotification(notification_id);

                if (notification != null)
                {
                    TextView lblAlert = (TextView) findViewById(R.id.lblParseAlert);
                    TextView lblBadge = (TextView) findViewById(R.id.lblParseBadge);
                    TextView lblSound = (TextView) findViewById(R.id.lblParseSound);
                    TextView lblTitle = (TextView) findViewById(R.id.lblParseTitle);
                    TextView lblParsePayload = (TextView) findViewById(R.id.lblParsePayload);

                    lblAlert.setText("alert: " + notification.getAlert());
                    lblBadge.setText("badge: " + notification.getBadge());
                    lblSound.setText("sound: " + notification.getSound());
                    lblTitle.setText("title: " + notification.getTitle());
                    lblParsePayload.setText(notification.getPayload());
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
