package app.receiver.parse.parsereceiver;

import android.app.Activity;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    abstract int getSectionNumber();

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(this.getSectionNumber());
    }
}
