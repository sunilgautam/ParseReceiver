package utility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.receiver.parse.parsereceiver.R;
import pojo.AppData;

public class AppDataAdapter extends BaseAdapter
{
    private List<AppData> appDataList = new ArrayList<AppData>();

    public AppDataAdapter()
    {

    }

    @Override
    public int getCount()
    {
        return getAppDataList().size();
    }

    @Override
    public Object getItem(int index)
    {
        return getAppDataList().get(index);
    }

    @Override
    public long getItemId(int index)
    {
        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup parent)
    {
        System.out.println(">>>>>>>>>> # " + index);
        ViewHolder holder = null;
        if (view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.app_data_single_row, parent, false);

            holder = new ViewHolder(view);

            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }

        AppData appData = getAppDataList().get(index);

        holder.lblKey.setText(appData.getKey());
        holder.lblValue.setText(appData.getValue());
        return view;
    }

    public List<AppData> getAppDataList()
    {
        return this.appDataList;
    }

    public void setAppDataList(List<AppData> appDataList)
    {
        this.appDataList = appDataList;
    }
}

class ViewHolder
{
    TextView lblKey;
    TextView lblValue;

    ViewHolder(View v)
    {
        this.lblKey = (TextView) v.findViewById(R.id.lblKey);
        this.lblValue = (TextView) v.findViewById(R.id.lblValue);
    }
}