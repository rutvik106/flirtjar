package dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.flirtjar.ActivityPurchaseCoins;
import com.app.flirtjar.App;
import com.app.flirtjar.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rutvik on 4/13/2017 at 11:15 PM.
 */

public class DialogCoinPackages extends Dialog
{

    private static final String TAG = App.APP_TAG + DialogCoinPackages.class.getSimpleName();

    public DialogCoinPackages(@NonNull Context context)
    {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_coin_packages);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_thirtyCoins, R.id.btn_hundredCoins, R.id.btn_twoFiftyCoins})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_thirtyCoins:
                dismiss();
                ActivityPurchaseCoins.start(getContext(), ActivityPurchaseCoins.IntentExtra.THIRTY_COINS);
                break;

            case R.id.btn_hundredCoins:
                dismiss();
                ActivityPurchaseCoins.start(getContext(), ActivityPurchaseCoins.IntentExtra.HUNDRED_COINS);
                break;

            case R.id.btn_twoFiftyCoins:
                dismiss();
                ActivityPurchaseCoins.start(getContext(), ActivityPurchaseCoins.IntentExtra.TWOFIFTY_COINS);
                break;
        }
    }
}
