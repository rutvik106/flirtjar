package dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.flirtjar.App;
import com.app.flirtjar.R;

import adapters.GiftGridListAdapter;
import api.API;
import api.RetrofitCallback;
import apimodels.Coins;
import apimodels.Gift;
import apimodels.SendGift;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import utils.Display;
import utils.SharedPreferences;
import viewholders.VHSingleGift;

/**
 * Created by rutvik on 3/10/2017 at 8:59 AM.
 */

public class DialogSendGifts extends Dialog implements VHSingleGift.OnGiftItemClickListener
{
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.rv_gifts)
    RecyclerView rvGifts;

    GiftGridListAdapter adapter;

    Call<Gift> call;

    Call<Coins> getCoins;

    int toUserId;
    private Call<SendGift> sendingGift;

    public DialogSendGifts(@NonNull Context context, int toUserId)
    {
        super(context);
        this.toUserId = toUserId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_send_gift);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        ButterKnife.bind(this);

        adapter = new GiftGridListAdapter(getContext(), this);

        int noOfColumns = Display.calculateNoOfColumns(getContext(), 100);

        rvGifts.setLayoutManager(new GridLayoutManager(getContext(), noOfColumns));
        rvGifts.setHasFixedSize(true);
        rvGifts.setAdapter(adapter);

        getGifts();

    }

    private void getGifts()
    {

        final RetrofitCallback<Gift> onGetGifts = new RetrofitCallback<Gift>(getContext())
        {
            @Override
            public void onResponse(Call<Gift> call, Response<Gift> response)
            {
                super.onResponse(call, response);
                if (response.isSuccessful())
                {
                    for (Gift.ResultBean singleGift : response.body().getResult())
                    {
                        adapter.addItem(singleGift);
                    }
                }
            }
        };

        call = API.Gifts.getGifts(1, SharedPreferences.getFlirtjarUserToken(getContext()), onGetGifts);

    }

    @OnClick(R.id.ib_back)
    public void onClick()
    {
        dismiss();
    }

    @Override
    public void dismiss()
    {
        if (call != null)
        {
            call.cancel();
        }
        if (getCoins != null)
        {
            getCoins.cancel();
        }
        if (sendingGift != null)
        {
            sendingGift.cancel();
        }
        super.dismiss();
    }

    @Override
    public void onGiftItemClicked(final Gift.ResultBean gift)
    {
        getUserCoins(gift);
    }

    private void getUserCoins(final Gift.ResultBean gift)
    {
        getCoins = API.Profile.getCoins(SharedPreferences.getFlirtjarUserToken(getContext()),
                new RetrofitCallback<Coins>(getContext())
                {
                    @Override
                    public void onResponse(Call<Coins> call, Response<Coins> response)
                    {
                        super.onResponse(call, response);
                        if (response.isSuccessful())
                        {
                            if (response.body().getResult().getCoins() >= gift.getPrice())
                            {
                                //USER CAN PURCHASE THIS GIFT
                                promptUserToSendGift(gift);
                            } else
                            {
                                //USER CANNOT PURCHASE THIS GIFT
                                new PurchaseDialog(getContext()).show();
                            }
                        } else
                        {
                            Toast.makeText(getContext(), "Something went wrong, Try again later.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Coins> call, Throwable t)
                    {
                        super.onFailure(call, t);
                        Toast.makeText(getContext(), "Something went wrong, Try again later.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void promptUserToSendGift(final Gift.ResultBean gift)
    {
        new AlertDialog.Builder(getContext())
                .setMessage("Are you sure, You want to send this Gift?")
                .setPositiveButton("Send", new OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        trySendingGift(gift);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void trySendingGift(final Gift.ResultBean gift)
    {
        final SendGift.ResultBean sendGift = new SendGift.ResultBean();
        sendGift.setGift(gift.getId());
        sendGift.setUserFrom(App.getInstance().getUser().getResult().getId());
        sendGift.setUserTo(toUserId);
        sendingGift = API.Profile.sendGift(sendGift, SharedPreferences.getFlirtjarUserToken(getContext()),
                new RetrofitCallback<SendGift>(getContext())
                {
                    @Override
                    public void onResponse(Call<SendGift> call, Response<SendGift> response)
                    {
                        super.onResponse(call, response);
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Gift Sent Successfully", Toast.LENGTH_SHORT).show();
                        } else
                        {
                            Toast.makeText(getContext(), "Failed to send gift", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SendGift> call, Throwable t)
                    {
                        super.onFailure(call, t);
                        Toast.makeText(getContext(), "Failed to send gift", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
