package app.receiver.parse.parsereceiver;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import utility.Utility;

public class SettingFragment extends BaseFragment
{

    public final static String LOGTAG = SettingFragment.class.getName();
    private final static String PARSE_SETUP_LINK = "https://www.parse.com/apps/";

    public SettingFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        final View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        Setting setting = Utility.getSettings(getActivity().getApplicationContext());

        EditText txtAppId = (EditText) rootView.findViewById(R.id.txtAppId);
        EditText txtClientKey = (EditText) rootView.findViewById(R.id.txtClientKey);
        TextView lblParseLink = (TextView) rootView.findViewById(R.id.lblParseLink);
        lblParseLink.setText(Html.fromHtml("<a href=\"" + PARSE_SETUP_LINK + "\">" + "Go to parse" + "</a>"));
        lblParseLink.setLinksClickable(true);
        lblParseLink.setMovementMethod(LinkMovementMethod.getInstance());

        if (setting.getAppId() != null)
        {
            txtAppId.setText(setting.getAppId());
        }

        if (setting.getClientKey() != null)
        {
            txtClientKey.setText(setting.getClientKey());
        }

        Button saveButton = (Button) rootView.findViewById(R.id.btnSaveSetting);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                EditText txtAppId = (EditText) rootView.findViewById(R.id.txtAppId);
                EditText txtClientKey = (EditText) rootView.findViewById(R.id.txtClientKey);

                String appId = txtAppId.getText().toString().trim();
                String clientKey = txtClientKey.getText().toString().trim();

                if (appId.equals(""))
                {
                    Toast.makeText(getActivity(), "Enter app id", Toast.LENGTH_SHORT).show();
                } else if (clientKey.equals(""))
                {
                    Toast.makeText(getActivity(), "Enter client key", Toast.LENGTH_SHORT).show();
                } else
                {
                    if (!appId.equals(ParseReceiver.PARSE_SETTING.getAppId()) || !clientKey.equals(ParseReceiver.PARSE_SETTING.getClientKey()))
                    {
                        Setting setting = new Setting();
                        setting.setAppId(appId);
                        setting.setClientKey(clientKey);
                        Utility.setSettings(getActivity().getApplicationContext(), setting);

                        Utility.reInitializeParse(getActivity(), ParseReceiver.PARSE_SETTING);

                        Toast.makeText(getActivity(), "Settings saved, Restart app.", Toast.LENGTH_LONG).show();
                    } else
                    {
                        Log.e(LOGTAG, "No setting changed");
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    int getSectionNumber()
    {
        return 3;
    }
}