package com.app.flirtjar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;
import utils.InAppPurchaseConstants;

public class ActivityPurchaseCoins extends Activity
{

    private static final String TAG = App.APP_TAG + ActivityPurchaseCoins.class.getSimpleName();

    IabHelper mIabHelper;
    IabHelper.OnConsumeFinishedListener onConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener()
            {
                @Override
                public void onConsumeFinished(Purchase purchase, IabResult result)
                {
                    if (result.isSuccess())
                    {

                    } else
                    {

                    }
                }
            };
    IabHelper.QueryInventoryFinishedListener queryInventoryFinishedListener =
            new IabHelper.QueryInventoryFinishedListener()
            {
                @Override
                public void onQueryInventoryFinished(IabResult result, Inventory inv)
                {
                    if (result.isFailure())
                    {

                    } else
                    {
                        mIabHelper.consumeAsync(inv.getPurchase(InAppPurchaseConstants.ItemSku.COINS_30),
                                onConsumeFinishedListener);
                    }
                }
            };
    IabHelper.OnIabPurchaseFinishedListener onIabPurchaseFinishedListener =
            new IabHelper.OnIabPurchaseFinishedListener()
            {
                @Override
                public void onIabPurchaseFinished(IabResult result, Purchase purchase)
                {
                    if (result.isFailure())
                    {
                        return;
                    } else if (purchase.getSku().equals(InAppPurchaseConstants.ItemSku.COINS_30))
                    {
                        consumeItem();

                    }
                }
            };

    public static void start(final Context context, String packageType)
    {
        final Intent i = new Intent(context, ActivityPurchaseCoins.class);
        i.putExtra(IntentExtra.PACKAGE_TYPE, packageType);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mIabHelper = new IabHelper(this, InAppPurchaseConstants.base64EncodedPublicKey);

        mIabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener()
        {
            @Override
            public void onIabSetupFinished(IabResult result)
            {
                if (result.isSuccess())
                {
                    Log.i(TAG, "IN APP BILLING SETUP OK");

                    if (getIntent().getStringExtra(IntentExtra.PACKAGE_TYPE)
                            .equals(IntentExtra.THIRTY_COINS))
                    {
                        mIabHelper.launchPurchaseFlow(ActivityPurchaseCoins.this,
                                InAppPurchaseConstants.ItemSku.COINS_30,
                                10001, onIabPurchaseFinishedListener, "mypurchasetoken");
                    } else if (getIntent().getStringExtra(IntentExtra.PACKAGE_TYPE)
                            .equals(IntentExtra.HUNDRED_COINS))
                    {
                        mIabHelper.launchPurchaseFlow(ActivityPurchaseCoins.this,
                                InAppPurchaseConstants.ItemSku.COINS_100,
                                10001, onIabPurchaseFinishedListener, "mypurchasetoken");
                    } else if (getIntent().getStringExtra(IntentExtra.PACKAGE_TYPE)
                            .equals(IntentExtra.TWOFIFTY_COINS))
                    {
                        mIabHelper.launchPurchaseFlow(ActivityPurchaseCoins.this,
                                InAppPurchaseConstants.ItemSku.COINS_250,
                                10001, onIabPurchaseFinishedListener, "mypurchasetoken");
                    }

                } else
                {
                    Log.i(TAG, "IN APP BILLING SETUP FAILED");
                }
            }
        });


    }

    private void consumeItem()
    {
        mIabHelper.queryInventoryAsync(queryInventoryFinishedListener);
    }

    @Override
    protected void onDestroy()
    {
        if (mIabHelper != null)
        {
            mIabHelper.dispose();
            mIabHelper = null;
        }
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i(TAG, "onActivityResult: REQUEST CODE: " + requestCode);
        Log.i(TAG, "onActivityResult: RESULT CODE: " + resultCode);
        if (!mIabHelper.handleActivityResult(requestCode, resultCode, data))
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == 10001)
        {
            if (resultCode == 0)
            {
                finish();
            }
        }
    }

    public static class IntentExtra
    {
        public static final String PACKAGE_TYPE = "PACKAGE_TYPE";
        public static final String HUNDRED_COINS = "HUNDRED_COINS";
        public static final String THIRTY_COINS = "THIRTY_COINS";
        public static final String TWOFIFTY_COINS = "TWOFIFTY_COINS";
    }

}
