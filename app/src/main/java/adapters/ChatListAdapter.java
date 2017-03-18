package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apimodels.MatchedProfiles;
import viewholders.VHSingleChatUser;

/**
 * Created by rutvik on 2/8/2017 at 11:09 PM.
 */

public class ChatListAdapter extends RecyclerView.Adapter
{

    private final Context context;

    private final List<MatchedProfiles.ResultBean> chatUserList;

    public ChatListAdapter(Context context)
    {
        this.context = context;
        chatUserList = new ArrayList<>();
    }

    public void addChatUser(MatchedProfiles.ResultBean chatUser)
    {
        chatUserList.add(chatUser);
        notifyItemInserted(chatUserList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return VHSingleChatUser.create(context, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        VHSingleChatUser.bind((VHSingleChatUser) holder, chatUserList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return chatUserList.size();
    }
}
