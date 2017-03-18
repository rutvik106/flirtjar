package dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.app.flirtjar.R;

import adapters.GiftGridListAdapter;
import api.API;
import api.RetrofitCallback;
import apimodels.Gift;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import utils.Display;
import utils.SharedPreferences;

/**
 * Created by rutvik on 3/10/2017 at 8:59 AM.
 */

public class DialogSendGifts extends Dialog
{
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.rv_gifts)
    RecyclerView rvGifts;

    GiftGridListAdapter adapter;

    Call<Gift> call;

    public DialogSendGifts(@NonNull Context context)
    {
        super(context);
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

        adapter = new GiftGridListAdapter(getContext());

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
        super.dismiss();
    }

}
