package app.receiver.parse.parsereceiver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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

            Bundle bundle = getIntent().getExtras();
            if(bundle != null)
            {
                Bundle payloadBundle = (Bundle)bundle.get("payload");
                String jsonPayload = payloadBundle.getString("com.parse.Data");
                JSONObject jsonObj = new JSONObject(jsonPayload);

                String wholePayload = jsonObj.toString();
                String alert = null;
                String badge = null;
                String sound = null;
                String title = null;

                if (jsonObj.has("alert"))
                {
                    alert = jsonObj.getString("alert");
                }

                if (jsonObj.has("badge"))
                {
                    badge = jsonObj.getString("badge");
                }

                if (jsonObj.has("sound"))
                {
                    sound = jsonObj.getString("sound");
                }

                if (jsonObj.has("sound"))
                {
                    sound = jsonObj.getString("sound");
                }

                Log.i(LOGTAG, wholePayload);
                Log.i(LOGTAG, "alert => " + alert);
                Log.i(LOGTAG, "badge => " + badge);
                Log.i(LOGTAG, "sound => " + sound);
                Log.i(LOGTAG, "title => " + title);

                TextView lblAlert = (TextView) findViewById(R.id.lblParseAlert);
                TextView lblBadge = (TextView) findViewById(R.id.lblParseBadge);
                TextView lblSound = (TextView) findViewById(R.id.lblParseSound);
                TextView lblTitle = (TextView) findViewById(R.id.lblParseTitle);
                TextView lblParsePayload = (TextView) findViewById(R.id.lblParsePayload);

                lblAlert.setText("alert: " + alert);
                lblBadge.setText("badge: " + badge);
                lblSound.setText("sound: " + sound);
                lblTitle.setText("title: " + title);
                lblParsePayload.setText(wholePayload);

            }
        }
        catch (JSONException jEx)
        {
            jEx.printStackTrace();
        }
    }
}
