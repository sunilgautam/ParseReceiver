package app.receiver.parse.parsereceiver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

public class HomeFragment extends BaseFragment {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        ParseInstallation installation = ParseInstallation.getCurrentInstallation();

        installation.put("receive_flag", 1);

        installation.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                //Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_LONG).show();
            }
        });



        return rootView;
    }

    @Override
    int getSectionNumber() {
        return 1;
    }
}
