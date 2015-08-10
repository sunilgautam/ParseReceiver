package app.receiver.parse.parsereceiver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingFragment extends BaseFragment {

    public SettingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        return rootView;
    }

    @Override
    int getSectionNumber() {
        return 2;
    }
}