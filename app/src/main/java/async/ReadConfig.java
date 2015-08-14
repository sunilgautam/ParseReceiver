package async;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.receiver.parse.parsereceiver.HomeFragment;
import utility.Utility;

public class ReadConfig extends AsyncTask<String, Integer, Boolean>
{
    private final static String LOGTAG = ReadConfig.class.getName();
    private final static String ENTRY_POST_URL = "http://testbed1.kronosis.com/parse-info.json";
    private HomeFragment uiThread;

    /*[
        {
            key: "username",
            type: "string"
        },
        {
            key: "email",
            type: "string"
        }
    ]*/

    public ReadConfig(HomeFragment m)
    {
        Log.d(LOGTAG, "Initialized Async task object to read data");
        this.uiThread = m;
    }

    // runs on UI thread
    @Override
    protected void onPreExecute()
    {
        Log.d(LOGTAG, "PreExecute Async task object");
    }

    // runs on a separate single worker thread
    @Override
    protected Boolean doInBackground(String... params)
    {
        Log.d(LOGTAG, "Performing Async task");
        JSONArray jsonArray = callWebService();
        Utility.setDataConfig(this.uiThread.getActivity(), jsonArray);

        publishProgress(100);
        Log.d(LOGTAG, "Async task Done");
        return Boolean.valueOf(true);
    }

    // runs on UI thread
    @Override
    protected void onProgressUpdate(Integer... values)
    {
        Log.d(LOGTAG, "onProgressUpdate on UI thread");
    }

    // runs on UI thread
    @Override
    protected void onPostExecute(Boolean result)
    {
        Log.d(LOGTAG, "onPostExecute on UI thread");
        uiThread.doneProgressing();
    }

    // runs on UI thread, if cancelled instead of onPostExecute() , this will be
    // called from doInBackground()
    @Override
    protected void onCancelled()
    {
        Log.d(LOGTAG, "onCancelled on UI thread");
        uiThread.hideProgressDialog();
    }

    private JSONArray callWebService()
    {
        JSONArray jArray = null;
        String str = "";
        HttpResponse response;
        HttpClient myClient = null;

        try
        {
            myClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(ENTRY_POST_URL);

            response = myClient.execute(request);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
            Log.i(LOGTAG, "response code: " + response.getStatusLine().getStatusCode());

        } catch (ClientProtocolException e)
        {
            Log.e(LOGTAG, "protocol error, check connection");
            e.printStackTrace();
        } catch (IOException e)
        {
            Log.e(LOGTAG, "io error, have u added the permision: android.permission.INTERNET ");
            e.printStackTrace();
        }

        try
        {
            jArray = new JSONArray(str);
        } catch (JSONException e)
        {
            Log.e(LOGTAG, "unable to parse json :(");
            e.printStackTrace();
        }

        return jArray;
    }
}
