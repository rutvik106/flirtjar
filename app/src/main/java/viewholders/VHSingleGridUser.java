package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.app.flirtjar.R;
import com.bumptech.glide.Glide;

import apimodels.GridUsers;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by rutvik on 3/18/2017 at 4:22 PM.
 */

public class VHSingleGridUser extends RecyclerView.ViewHolder
{
    final Context context;
    @BindView(R.id.iv_gridUserImage)
    ImageButton ivGridUserImage;

    GridUsers.ResultBean user;

    public VHSingleGridUser(Context context, View itemView)
    {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public static VHSingleGridUser create(final Context context, final ViewGroup parent)
    {
        return new VHSingleGridUser(context, LayoutInflater.from(context)
                .inflate(R.layout.single_user_grid_item, parent, false));
    }

    public static void bind(final VHSingleGridUser vh, GridUsers.ResultBean user)
    {
        vh.user = user;
        Glide.with(vh.context)
                .load(user.getProfilePicture())
                .bitmapTransform(new CropCircleTransformation(vh.context))
                .into(vh.ivGridUserImage);
    }

    @OnClick(R.id.iv_gridUserImage)
    public void onClick()
    {
    }
}
