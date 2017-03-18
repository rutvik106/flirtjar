package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apimodels.GridUsers;
import viewholders.VHSingleGridUser;

/**
 * Created by rutvik on 3/18/2017 at 2:45 PM.
 */

public class UserGridAdapter extends RecyclerView.Adapter
{

    final Context context;

    final List<GridUsers.ResultBean> itemList;

    public UserGridAdapter(final Context context)
    {
        this.context = context;
        itemList = new ArrayList<>();
    }

    public void addItem(GridUsers.ResultBean item)
    {
        itemList.add(item);
        notifyItemInserted(itemList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return VHSingleGridUser.create(context, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        VHSingleGridUser.bind((VHSingleGridUser) holder, itemList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }

}
