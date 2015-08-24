package utility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.receiver.parse.parsereceiver.R;
import pojo.Notification;

public class NotificationAdapter extends BaseAdapter
{
    private List<Notification> notificationList = new ArrayList<Notification>();

    public NotificationAdapter()
    {
    }

    @Override
    public int getCount()
    {
        return getNotificationList().size();
    }

    @Override
    public Object getItem(int position)
    {
        return getNotificationList().get(position);
    }

    @Override
    public long getItemId(int index)
    {
        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup parent)
    {
        ViewHolder holder;
        if (view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.notification_single_row, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        Notification notification = getNotificationList().get(index);
        holder.lblAlert.setText(notification.getAlert());
        holder.lblCreatedAt.setText(notification.getCreated_at());

        return view;
    }

    public List<Notification> getNotificationList()
    {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList)
    {
        this.notificationList = notificationList;
    }

    class ViewHolder
    {
        TextView lblAlert;
        TextView lblCreatedAt;

        ViewHolder(View v)
        {
            this.lblAlert = (TextView) v.findViewById(R.id.notification_alert);
            this.lblCreatedAt = (TextView) v.findViewById(R.id.notification_created_at);
        }
    }
}
