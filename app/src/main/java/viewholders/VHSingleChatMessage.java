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
import utils.DateTime;

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
    @BindView(R.id.tv_chatMsgTime)
    TextView tvChatMsgTime;

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
        if (sentMessage.getUserFrom() == App.getInstance().getUser().getResult().getId())
        {
            vh.llChatMessageContainer.setGravity(Gravity.RIGHT);
            vh.rlMessageContentContainer.setGravity(Gravity.RIGHT);
            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            vh.tvChatMsgTime.setLayoutParams(params);
            vh.tvChatUserName.setText(App.getInstance().getUser().getResult().getFirstName());
            vh.rlMessageContentContainer.setBackground(vh.context.getDrawable(R.drawable.chat_bubble_self));
        } else
        {
            vh.llChatMessageContainer.setGravity(Gravity.LEFT);
            vh.rlMessageContentContainer.setGravity(Gravity.LEFT);
            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT;
            vh.tvChatMsgTime.setLayoutParams(params);
            vh.tvChatUserName.setText(sentMessage.getFirstName());
            vh.rlMessageContentContainer.setBackground(vh.context.getDrawable(R.drawable.chat_bubble_friend));
        }
        vh.tvChatBodyContent.setText(sentMessage.getMessageText());
        vh.tvChatMsgTime.setText(DateTime.convertDate("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'",
                "EEE, d MMM h:mm a", sentMessage.getSentAt()));
    }

}
