package com.app.flirtjar;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import api.API;
import api.RetrofitCallback;
import apimodels.User;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import utils.Constants;
import utils.SharedPreferences;

public class ActivityProfileView extends BaseActivity implements ImageListener
{

    @BindView(R.id.carouselView)
    CarouselView carouselView;

    @BindView(R.id.tv_userName)
    TextView tvUserName;

    @BindView(R.id.tv_userAge)
    TextView tvUserAge;

    @BindView(R.id.tv_userGender)
    TextView tvUserGender;

    @BindView(R.id.tv_userBioType)
    TextView tvUserBioType;

    @BindView(R.id.tv_userStateCountry)
    TextView tvUserStateCountry;

    @BindView(R.id.tv_userStatus)
    TextView tvUserStatus;

    @BindView(R.id.ll_jarOptionsContainer)
    LinearLayout llJarOptionsContainer;

    Call<User> call;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final boolean isViewingSelfProfile = getIntent()
                .getBooleanExtra(Constants.IS_VIEWING_SELF_PROFILE, false);


        final String token = SharedPreferences.getFlirtjarUserToken(this);

        if (isViewingSelfProfile)
        {
            llJarOptionsContainer.setVisibility(View.GONE);
            getProfileDetails(token);
        } else
        {
            llJarOptionsContainer.setVisibility(View.VISIBLE);
            getOtherUserProfileDetails(getIntent().getStringExtra(Constants.USER_ID));
        }
    }

    private void getOtherUserProfileDetails(String userId)
    {
        call = API.User.getUser(userId, new OnGetUserDetails(this));
    }

    private void getProfileDetails(final String token)
    {

        call = API.User.getCurrentUser(token, new OnGetUserDetails(this));

    }

    @Override
    protected int getLayoutResourceId()
    {
        return R.layout.activity_profile_view;
    }

    @Override
    protected void showNoInternetView()
    {
        Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImageForPosition(int position, ImageView imageView)
    {
        if (user != null)
        {
            Glide.with(this)
                    .load(user.getResult().getProfilePicture())
                    .into(imageView);
        }
    }

    private void bindUserDetailsToUi(User user)
    {
        this.user = user;

        carouselView.setImageListener(this);

        carouselView.setPageCount(2);

        final User.ResultBean result = user.getResult();

        tvUserName.setText(result.getFirstName() + " " + result.getLastName());

        if (result.getGender().equalsIgnoreCase(Constants.Gender.MALE.getValue()))
        {
            tvUserGender.setText("MALE / ");
        } else if (result.getGender().equalsIgnoreCase(Constants.Gender.FEMALE.getValue()))
        {
            tvUserGender.setText("FEMALE / ");
        } else
        {
            tvUserGender.setText(result.getGender() + " / ");
        }
        tvUserAge.setText(result.getDob() + " / ");

        tvUserStateCountry.setText(result.getCountry());

        tvUserStatus.setText(result.getStatus());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        if (call != null)
        {
            call.cancel();
        }
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    class OnGetUserDetails extends RetrofitCallback<User>
    {
        public OnGetUserDetails(Context context)
        {
            super(context);
        }

        @Override
        public void onResponse(Call<User> call, final Response<User> response)
        {
            super.onResponse(call, response);

            if (response.isSuccessful())
            {
                bindUserDetailsToUi(response.body());
            }

        }
    }

}
