package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.flirtjar.App;
import com.app.flirtjar.R;
import com.bumptech.glide.Glide;

import java.util.Calendar;

import apimodels.NotificationList;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.Constants;

/**
 * Created by rutvik on 3/13/2017 at 1:09 PM.
 */

public class VHSingleNotification extends RecyclerView.ViewHolder
{
    public static final String TAG = App.APP_TAG + VHSingleNotification.class.getSimpleName();

    final Context context;
    @BindView(R.id.iv_notificationIcon)
    ImageView ivNotificationIcon;
    @BindView(R.id.tv_notificationText)
    TextView tvNotificationText;

    NotificationList.ResultBean model;
    @BindView(R.id.tv_notificationTime)
    TextView tvNotificationTime;

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
        Log.i(TAG, "BINDING NOTIFICATION ITEM TYPE: " + model.getNotificationType());

        vh.model = model;

        vh.tvNotificationText.setText(model.getNotificationText());

        vh.tvNotificationTime.setText(DateUtils.getRelativeTimeSpanString(model.getTimestamp().getTime(),
                Calendar.getInstance().getTimeInMillis(), 0));

        switch (model.getNotificationType())
        {
            case Constants.NotificationType.FJ_TEAM:
                Glide.with(vh.context)
                        .load(R.drawable.ic_chat_active)
                        .into(vh.ivNotificationIcon);
                break;

            case Constants.NotificationType.COINS:
                Glide.with(vh.context)
                        .load(R.drawable.ic_filrt_money)
                        .into(vh.ivNotificationIcon);
                break;

            case Constants.NotificationType.CRUSH_OR_SUPERLIKE:
                Glide.with(vh.context)
                        .load(R.drawable.ic_jar_active)
                        .into(vh.ivNotificationIcon);
                break;

            case Constants.NotificationType.LIKE:
                Glide.with(vh.context)
                        .load(R.drawable.ic_jar_active)
                        .into(vh.ivNotificationIcon);
                break;

            case Constants.NotificationType.USER_MATCH:
                Glide.with(vh.context)
                        .load(R.drawable.ic_jar_active)
                        .into(vh.ivNotificationIcon);
                break;

            case Constants.NotificationType.VIEW:
                Glide.with(vh.context)
                        .load(R.drawable.ic_jar_active)
                        .into(vh.ivNotificationIcon);
                break;

            case Constants.NotificationType.GIFT:
                Glide.with(vh.context)
                        .load(R.drawable.ic_gift)
                        .into(vh.ivNotificationIcon);
                break;
        }
    }
}
