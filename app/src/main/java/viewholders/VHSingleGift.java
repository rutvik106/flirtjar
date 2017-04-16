package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.flirtjar.R;
import com.bumptech.glide.Glide;

import apimodels.Gift;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rutvik on 3/10/2017 at 9:20 AM.
 */

public class VHSingleGift extends RecyclerView.ViewHolder
{
    final Context context;

    @BindView(R.id.iv_giftImage)
    ImageView ivGiftImage;
    @BindView(R.id.tv_giftPrice)
    TextView tvGiftPrice;
    @BindView(R.id.ll_gift)
    LinearLayout llGift;

    OnGiftItemClickListener listener;

    Gift.ResultBean singleGift;

    public VHSingleGift(Context context, View itemView, OnGiftItemClickListener listener)
    {
        super(itemView);
        this.context = context;
        this.listener = listener;
        ButterKnife.bind(this, itemView);
    }

    public static VHSingleGift create(final Context context, final ViewGroup parent,
                                      OnGiftItemClickListener listener)
    {
        return new VHSingleGift(context, LayoutInflater.from(context)
                .inflate(R.layout.single_gift_row_item, parent, false), listener);
    }

    public static void bind(final VHSingleGift vh, Gift.ResultBean singleGift)
    {
        vh.singleGift = singleGift;
        vh.tvGiftPrice.setText(singleGift.getPrice() + "");

        Glide.with(vh.context)
                .load(singleGift.getIcon())
                .into(vh.ivGiftImage);
    }

    @OnClick(R.id.ll_gift)
    public void onClick()
    {
        listener.onGiftItemClicked(singleGift);
    }

    public interface OnGiftItemClickListener
    {
        void onGiftItemClicked(Gift.ResultBean gift);
    }

}
