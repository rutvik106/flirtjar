package com.app.flirtjar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivitySetting extends AppCompatActivity
{

    @BindView(R.id.sw_man)
    Switch swMan;
    @BindView(R.id.sw_woman)
    Switch swWoman;
    @BindView(R.id.rangebar1)
    RangeBar rangebar1;
    @BindView(R.id.sw_show_me_on_jar)
    Switch swShowMeOnJar;
    @BindView(R.id.sw_show_me_near_by)
    Switch swShowMeNearBy;
    @BindView(R.id.btn_km)
    Button btnKm;
    @BindView(R.id.btn_meter)
    Button btnMeter;
    @BindView(R.id.layout_help_support)
    LinearLayout layoutHelpSupport;
    @BindView(R.id.layout_share)
    LinearLayout layoutShare;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Bold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setTitle("Settings");

        tvVersion.setText("Version " + BuildConfig.VERSION_NAME + " (android)");

    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick({R.id.btn_km, R.id.btn_meter, R.id.layout_help_support, R.id.layout_share})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_km:
                break;
            case R.id.btn_meter:
                break;
            case R.id.layout_help_support:
                break;
            case R.id.layout_share:
                break;
        }
    }
}
