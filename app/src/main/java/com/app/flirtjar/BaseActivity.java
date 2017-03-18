package com.app.flirtjar;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import utils.NetworkConnectionDetector;

/**
 * Created by rutvik on 3/3/2017 at 11:49 PM.
 */

public abstract class BaseActivity extends AppCompatActivity implements NetworkConnectionDetector.ConnectivityReceiverListener
{

    private static final String TAG = App.APP_TAG + BaseActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_navLogoText)
    TextView tvNavLogoText;

    private NetworkConnectionDetector networkConnectionDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        ButterKnife.bind(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Righteous-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        final Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico-Regular.ttf");

        tvNavLogoText.setTypeface(pacifico);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        IntentFilter i = new IntentFilter();
        i.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkConnectionDetector = new NetworkConnectionDetector();
        registerReceiver(networkConnectionDetector, i);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        NetworkConnectionDetector.connectivityReceiverListener = this;
        checkInternet();
    }


    protected abstract int getLayoutResourceId();

    @Override
    protected void onStop()
    {
        if (networkConnectionDetector != null)
        {
            unregisterReceiver(networkConnectionDetector);
        }
        super.onStop();
    }

    public void checkInternet()
    {
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
        {
            internetAvailable();
        } else
        {
            internetNotAvailable();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected)
    {
        if (isConnected)
        {
            Log.i(TAG, "CONNECTED>>>");
            internetAvailable();
        } else
        {
            Log.i(TAG, "<<<DISCONNECTED");
            internetNotAvailable();
        }
    }

    protected abstract void internetNotAvailable();

    protected abstract void internetAvailable();

}
