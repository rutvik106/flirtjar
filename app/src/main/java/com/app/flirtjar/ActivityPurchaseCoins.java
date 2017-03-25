package com.app.flirtjar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;
import utils.BillingDetailsConstants;

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
                        mIabHelper.consumeAsync(inv.getPurchase(BillingDetailsConstants.ITEM_SKU),
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
                    } else if (purchase.getSku().equals(BillingDetailsConstants.ITEM_SKU))
                    {
                        consumeItem();

                    }
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mIabHelper = new IabHelper(this, BillingDetailsConstants.base64EncodedPublicKey);

        mIabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener()
        {
            @Override
            public void onIabSetupFinished(IabResult result)
            {
                if (result.isSuccess())
                {
                    Log.i(TAG, "IN APP BILLING SETUP OK");
                    mIabHelper.launchPurchaseFlow(ActivityPurchaseCoins.this, BillingDetailsConstants.ITEM_SKU
                            , 10001, onIabPurchaseFinishedListener, "mypurchasetoken");
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

}
