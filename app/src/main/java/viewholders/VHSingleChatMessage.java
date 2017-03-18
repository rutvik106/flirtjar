package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.flirtjar.App;
import com.app.flirtjar.R;

import apimodels.SentMessage;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rutvik on 3/18/2017 at 8:13 PM.
 */

public class VHSingleChatMessage extends RecyclerView.ViewHolder
{
    final Context context;
    @BindView(R.id.tv_chatUserName)
    TextView tvChatUserName;
    @BindView(R.id.tv_chatBodyContent)
    TextView tvChatBodyContent;
    @BindView(R.id.rl_messageContentContainer)
    RelativeLayout rlMessageContentContainer;
    @BindView(R.id.ll_chatMessageContainer)
    LinearLayout llChatMessageContainer;

    SentMessage.ResultBean sentMessage;

    public VHSingleChatMessage(Context context, View itemView)
    {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public static VHSingleChatMessage create(final Context context, final ViewGroup parent)
    {
        return new VHSingleChatMessage(context, LayoutInflater.from(context)
                .inflate(R.layout.single_self_chat_row, parent, false));
    }

    public static void bind(final VHSingleChatMessage vh, SentMessage.ResultBean sentMessage)
    {
        vh.sentMessage = sentMessage;
        vh.llChatMessageContainer.setGravity(Gravity.RIGHT);
        vh.tvChatUserName.setText(App.getInstance().getUser().getResult().getFirstName());
        vh.tvChatBodyContent.setText(sentMessage.getMessageText());
    }

}
