package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.app.flirtjar.App;

import java.util.ArrayList;
import java.util.List;

import apimodels.NotificationList;
import viewholders.VHSingleNotification;

/**
 * Created by rutvik on 3/9/2017 at 1:37 PM.
 */

public class NotificationListAdapter extends RecyclerView.Adapter
{
    public static final String TAG = App.APP_TAG + NotificationListAdapter.class.getSimpleName();

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
        Log.i(TAG, "ON CREATE VIEW HOLDER");
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
        Log.i(TAG, "NOTIFICATION MODEL LIST ITEM COUNT: " + itemList.size());
        return itemList.size();
    }

    public void clear()
    {
        itemList.clear();
        notifyDataSetChanged();
    }
}
