package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.flirtjar.R;
import com.bumptech.glide.Glide;

import apimodels.NotificationList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rutvik on 3/13/2017 at 1:09 PM.
 */

public class VHSingleNotification extends RecyclerView.ViewHolder
{
    final Context context;
    @BindView(R.id.iv_notificationIcon)
    ImageView ivNotificationIcon;
    @BindView(R.id.tv_notificationText)
    TextView tvNotificationText;

    NotificationList.ResultBean model;

    public VHSingleNotification(Context context, View itemView)
    {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public static VHSingleNotification create(final Context context, final ViewGroup parent)
    {
        return new VHSingleNotification(context, LayoutInflater.from(context)
                .inflate(R.layout.single_notification_row_item, parent, false));
    }

    public static void bind(final VHSingleNotification vh, NotificationList.ResultBean model)
    {
        vh.model = model;
        if (model.getNotificationType().equalsIgnoreCase("gift"))
        {
            Glide.with(vh.context)
                    .load(R.drawable.ic_gift)
                    .into(vh.ivNotificationIcon);
        } else
        {

        }
    }

}
