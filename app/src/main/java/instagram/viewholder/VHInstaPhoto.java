package instagram.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.app.flirtjar.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import instagram.apimodels.RecentMedia;

/**
 * Created by rutvik on 3/20/2017 at 4:16 PM.
 */

public class VHInstaPhoto extends RecyclerView.ViewHolder
{
    final Context context;
    @BindView(R.id.iv_instaPhoto)
    ImageView ivInstaPhoto;
    @BindView(R.id.fl_instaSelectedPhoto)
    FrameLayout flInstaSelectedPhoto;

    RecentMedia.DataBean.ImagesBean instaImage;

    public VHInstaPhoto(Context context, View itemView)
    {
        super(itemView);
        this.context = context;

        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                instaImage.setImageSelected(!instaImage.isImageSelected());

                if (instaImage.isImageSelected())
                {
                    flInstaSelectedPhoto.setVisibility(View.VISIBLE);
                } else
                {
                    flInstaSelectedPhoto.setVisibility(View.GONE);
                }

                if (instaImage.getOnImageCheckUncheckListener() != null)
                {
                    instaImage.getOnImageCheckUncheckListener()
                            .onCheckChanged(instaImage.isImageSelected(), instaImage);
                }

            }
        });
    }

    public static VHInstaPhoto create(final Context context, final ViewGroup parent)
    {
        return new VHInstaPhoto(context, LayoutInflater.from(context)
                .inflate(R.layout.single_insta_photo_row_item, parent, false));
    }

    public static void bind(final VHInstaPhoto vh, RecentMedia.DataBean.ImagesBean instaImage)
    {
        vh.instaImage = instaImage;

        Glide.with(vh.context)
                .load(instaImage.getLowResolution().getUrl())
                .crossFade()
                .placeholder(R.drawable.place_holder)
                .into(vh.ivInstaPhoto);

        if (instaImage.isImageSelected())
        {
            vh.flInstaSelectedPhoto.setVisibility(View.VISIBLE);
        } else
        {
            vh.flInstaSelectedPhoto.setVisibility(View.GONE);
        }

    }

}
