package app.receiver.parse.parsereceiver;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import db.NotificationDBHelper;
import pojo.Notification;
import utility.NotificationAdapter;

public class NotificationListFragment extends BaseFragment
{
    public final static String LOGTAG = NotificationListFragment.class.getName();
    NotificationAdapter notificationAdapter;
    ListView listView;

    public NotificationListFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_notification_list, container, false);

        listView = (ListView) rootView.findViewById(R.id.reminderList);
        View empty = rootView.findViewById(R.id.emptyNotificationList);
        listView.setEmptyView(empty);
        notificationAdapter = new NotificationAdapter();
        getNotifications();
        listView.setAdapter(notificationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Notification reminder = (Notification) listView.getAdapter().getItem(position);

                Intent pushIntent = new Intent(getActivity(), NotificationActivity.class);
                pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pushIntent.putExtra("notification_id", Long.parseLong(reminder.getId()));
                getActivity().startActivity(pushIntent);
            }
        });

        return rootView;
    }

    @Override
    int getSectionNumber()
    {
        return 2;
    }

    public void getNotifications()
    {
        // GET NOTIFICATIONS
        NotificationDBHelper db = new NotificationDBHelper(getActivity());
        this.notificationAdapter.setNotificationList(db.getAllNotifications());
    }
}
