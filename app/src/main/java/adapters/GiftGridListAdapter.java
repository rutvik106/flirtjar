package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apimodels.Gift;
import viewholders.VHSingleGift;

/**
 * Created by rutvik on 3/10/2017 at 9:31 AM.
 */

public class GiftGridListAdapter extends RecyclerView.Adapter
{

    final Context contest;

    final List<Gift.ResultBean> itemList;

    final VHSingleGift.OnGiftItemClickListener listener;

    public GiftGridListAdapter(final Context context, VHSingleGift.OnGiftItemClickListener listener)
    {
        this.contest = context;
        itemList = new ArrayList<>();
        this.listener = listener;
    }

    public void addItem(Gift.ResultBean item)
    {
        itemList.add(item);
        notifyItemInserted(itemList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return VHSingleGift.create(contest, parent, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        VHSingleGift.bind((VHSingleGift) holder, itemList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }

}
