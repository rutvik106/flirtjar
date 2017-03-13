package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apimodels.NotificationList;
import viewholders.VHSingleNotification;

/**
 * Created by rutvik on 3/9/2017 at 1:37 PM.
 */

public class NotificationListAdapter extends RecyclerView.Adapter
{


    final Context context;

    final List<NotificationList.ResultBean> itemList;

    public NotificationListAdapter(final Context context)
    {
        this.context = context;
        itemList = new ArrayList<>();
    }

    public void addItem(NotificationList.ResultBean item)
    {
        itemList.add(item);
        notifyItemInserted(itemList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return VHSingleNotification.create(context, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        VHSingleNotification.bind((VHSingleNotification) holder,
                itemList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }

}
