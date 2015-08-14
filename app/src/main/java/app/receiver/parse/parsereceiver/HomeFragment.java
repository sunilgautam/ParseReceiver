package app.receiver.parse.parsereceiver;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import async.ReadConfig;
import pojo.AppData;
import utility.AppDataAdapter;
import utility.Utility;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class HomeFragment extends BaseFragment implements View.OnClickListener
{
    private final static String LOGTAG = HomeFragment.class.getName();
    private ReadConfig asyncReadConfig;
    private ProgressDialog dialog;
    AppDataAdapter appDataAdapter;
    ListView listView;

    public HomeFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        Button reloadConfigButton = (Button) rootView.findViewById(R.id.btnReloadConfig);
        Button reloadDataButton = (Button) rootView.findViewById(R.id.btnReloadData);

        reloadConfigButton.setOnClickListener(this);
        reloadDataButton.setOnClickListener(this);

        listView = (ListView) rootView.findViewById(R.id.appDataList);
        View empty = rootView.findViewById(R.id.empty);
        listView.setEmptyView(empty);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                AppData appData = (AppData) listView.getItemAtPosition(position);

                android.support.v4.app.FragmentManager manager = getFragmentManager();

                EditDataDialogFragment editDataDialog = new EditDataDialogFragment();
                Bundle args = new Bundle();
                args.putString("key", appData.getKey());
                args.putString("value", appData.getValue());
                editDataDialog.setArguments(args);
                editDataDialog.show(manager, "edit_data_dialog");
            }
        });

        appDataAdapter = new AppDataAdapter();
        getData();

        /*List<AppData> dummyData = new ArrayList<AppData>();
        dummyData.add(new AppData("name", "Something"));
        dummyData.add(new AppData("email", "something@gmail.com"));
        dummyData.add(new AppData("contact", "9800000000"));

        appDataAdapter.setAppDataList(dummyData);*/

        listView.setAdapter(appDataAdapter);

        return rootView;
    }

    public void showProgressDialog(int resId)
    {
        Resources resources = getResources();
        // Hide keyboard before
        InputMethodManager inputManager = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        //check if no view has focus:
        View v = this.getActivity().getCurrentFocus();
        if (v != null)
        {
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        dialog = new ProgressDialog(this.getActivity());
        dialog.setMessage(resources.getString(resId));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void doneProgressing()
    {
        hideProgressDialog();
        Log.d(LOGTAG, "Config loaded");
        Toast.makeText(getActivity().getBaseContext(), R.string.done_read_config, Toast.LENGTH_SHORT).show();
        reloadData();
    }

    public void hideProgressDialog()
    {
        dialog.cancel();
    }

    @Override
    int getSectionNumber()
    {
        return 1;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnReloadConfig:
                asyncReadConfig = new ReadConfig(this);
                showProgressDialog(R.string.progress_read_config);
                asyncReadConfig.execute();
                break;
            case R.id.btnReloadData:
                reloadData();
                break;
            default:
                break;
        }
    }

    public void reloadData()
    {
        showProgressDialog(R.string.progress_read_data);
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.fetchInBackground(new GetCallback<ParseObject>()
        {
            @Override
            public void done(ParseObject parseObject, ParseException e)
            {
                getData();
                appDataAdapter.notifyDataSetChanged();

                hideProgressDialog();
                Log.d(LOGTAG, "Data loaded");
                Toast.makeText(getActivity().getBaseContext(), R.string.done_read_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData()
    {
        try
        {
            // GET INSTALLATION DATA
            List<AppData> appDataList = new ArrayList<AppData>();
            AppData dt = null;
            ParseInstallation installation = null;

            JSONArray jArray = Utility.getDataConfig(getActivity());
            JSONObject json = null;

            if (jArray == null)
            {
                Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_LONG).show();
            } else
            {
                installation = ParseInstallation.getCurrentInstallation();

                for (int i = 0; i < jArray.length(); i++)
                {
                    json = jArray.getJSONObject(i);
                    dt = new AppData();
                    dt.setKey(json.getString("key"));

                    switch (json.getString("type"))
                    {
                        case "string":
                            dt.setValue(installation.getString(json.getString("key")));
                            break;
                        case "number":
                            dt.setValue(Integer.toString(installation.getInt(json.getString("key"))));
                            break;
                        default:
                            break;
                    }

                    appDataList.add(dt);
                }

                this.appDataAdapter.setAppDataList(appDataList);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onFragmentMessage(String message, Object data)
    {
        if (message.equals("saved"))
        {
            getData();
            appDataAdapter.notifyDataSetChanged();
        }
    }
}
