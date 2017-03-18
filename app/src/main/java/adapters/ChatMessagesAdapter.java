package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apimodels.SentMessage;
import viewholders.VHSingleChatMessage;

/**
 * Created by rutvik on 3/18/2017 at 7:31 PM.
 */

public class ChatMessagesAdapter extends RecyclerView.Adapter
{

    final Context context;

    final List<SentMessage.ResultBean> itemList;

    public ChatMessagesAdapter(final Context context)
    {
        this.context = context;
        itemList = new ArrayList<>();
    }

    public void addItem(SentMessage.ResultBean item)
    {
        itemList.add(0, item);
        notifyItemInserted(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return VHSingleChatMessage.create(context, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        VHSingleChatMessage.bind((VHSingleChatMessage) holder, itemList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }


}
