package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.flirtjar.ActivityChat;
import com.app.flirtjar.R;
import com.bumptech.glide.Glide;

import apimodels.MatchedProfiles;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by rutvik on 2/8/2017 at 11:01 PM.
 */

public class VHSingleChatUser extends RecyclerView.ViewHolder
{
    final Context context;

    @BindView(R.id.iv_chatUserImage)
    ImageView ivChatUserImage;

    @BindView(R.id.tv_chatUserName)
    TextView tvChatUserName;

    @BindView(R.id.tv_chatUserLastMessage)
    TextView tvChatUserLastMessage;

    MatchedProfiles.ResultBean matchedUser;

    public VHSingleChatUser(final Context context, View itemView)
    {
        super(itemView);
        this.context = context;

        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ActivityChat.start(context, matchedUser.getId(), matchedUser.getFirstName(), matchedUser.getProfilePicture());
            }
        });

    }

    public static VHSingleChatUser create(final Context context, final ViewGroup parent)
    {
        return new VHSingleChatUser(context, LayoutInflater.from(context)
                .inflate(R.layout.single_chat_user_item, parent, false));
    }

    public static void bind(final VHSingleChatUser vh, MatchedProfiles.ResultBean matchedUser)
    {
        vh.matchedUser = matchedUser;

        Glide.with(vh.context)
                .load(matchedUser.getProfilePicture())
                .bitmapTransform(new CropCircleTransformation(vh.context))
                .crossFade()
                .into(vh.ivChatUserImage);

        vh.tvChatUserName.setText(matchedUser.getFirstName());
    }

}
