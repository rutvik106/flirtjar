package com.app.flirtjar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import api.API;
import api.RetrofitCallback;
import apimodels.OtherPictures;
import apimodels.User;
import butterknife.BindView;
import butterknife.OnClick;
import dialogs.DialogSendGifts;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import utils.Constants;
import utils.DateTime;
import utils.SharedPreferences;

public class ActivityProfileView extends BaseActivity implements ImageListener
{

    final List<OtherPictures.ResultBean> otherPictures = new ArrayList<>();
    @BindView(R.id.carouselView)
    CarouselView carouselView;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.tv_userAge)
    TextView tvUserAge;
    @BindView(R.id.tv_userGender)
    TextView tvUserGender;
    @BindView(R.id.tv_userStateCountry)
    TextView tvUserStateCountry;
    @BindView(R.id.tv_userTagLine)
    TextView tvUserTagLine;
    @BindView(R.id.ll_jarOptionsContainer)
    LinearLayout llJarOptionsContainer;
    Call<User> call;
    User user;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.ll_userOtherDetailsContainer)
    LinearLayout llUserOtherDetailsContainer;
    @BindView(R.id.fl_userInterests)
    FlowLayout flUserInterests;
    @BindView(R.id.fl_userSocial)
    FlowLayout flUserSocial;
    @BindView(R.id.ibtn_dislike)
    ImageButton ibtnDislike;
    @BindView(R.id.ibtn_gift)
    ImageButton ibtnGift;
    @BindView(R.id.ibtn_like)
    ImageButton ibtnLike;

    public static void start(Context context, boolean isViewingSelf, int userId)
    {
        Intent i = new Intent(context, ActivityProfileView.class);
        i.putExtra(Constants.IS_VIEWING_SELF_PROFILE, isViewingSelf);
        i.putExtra(Constants.USER_ID, userId);
        context.startActivity(i);
    }

    @Override
    protected int getLayoutResourceId()
    {
        return R.layout.activity_profile_view;
    }

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
            getOtherUserProfileDetails(getIntent().getIntExtra(Constants.USER_ID, 0));
        }
    }

    private void getOtherUserProfileDetails(int userId)
    {
        call = API.User.getUser(userId, new OnGetUserDetails(this));
    }

    private void getProfileDetails(final String token)
    {

        call = API.User.getCurrentUser(token, new OnGetUserDetails(this));

    }

    @Override
    protected void internetNotAvailable()
    {

    }

    @Override
    protected void internetAvailable()
    {

    }

    @Override
    public void setImageForPosition(int position, ImageView imageView)
    {
        if (user != null)
        {
            Glide.with(this)
                    .load(otherPictures.get(position).getImage())
                    .into(imageView);
        }
    }

    private void bindUserDetailsToUi(User user)
    {
        this.user = user;

        final OtherPictures.ResultBean img = new OtherPictures.ResultBean();
        img.setImage(user.getResult().getProfilePicture());

        otherPictures.add(img);

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

        tvUserTagLine.setText(result.getTagline());

        setStatus(result);

        TextView lookingFor = new TextView(this);
        /*Drawable lookingForImg = getResources().getDrawable(R.drawable.looking_for);
        lookingForImg.setBounds( 0, 0, 60, 60 );*/
        lookingFor.setCompoundDrawablesWithIntrinsicBounds(R.drawable.looking_for, 0, 0, 0);
        lookingFor.setCompoundDrawablePadding(10);
        for (Constants.Gender g : Constants.Gender.values())
        {
            if (result.getLookingFor().equals(g.getValue()))
            {
                lookingFor.setText("Looking For " + g.getLabel());
            }
        }
        llUserOtherDetailsContainer.addView(lookingFor);


        TextView height = new TextView(this);
        height.setText("Height " + result.getHeight() + "");
        llUserOtherDetailsContainer.addView(height);


        TextView hairColor = new TextView(this);
        for (Constants.HairColor h : Constants.HairColor.values())
        {
            if (result.getHairColor().equals(h.getValue()))
            {
                hairColor.setText("Hair Color " + h.getLabel());
            }
        }
        llUserOtherDetailsContainer.addView(hairColor);


        TextView eyeColor = new TextView(this);
        for (Constants.EyeColor e : Constants.EyeColor.values())
        {
            if (result.getEyeColor().equals(e.getValue()))
            {
                eyeColor.setText("Eye Color " + e.getLabel());
            }
        }
        llUserOtherDetailsContainer.addView(eyeColor);


        TextView occupation = new TextView(this);
        occupation.setText(result.getOccupation());
        llUserOtherDetailsContainer.addView(occupation);


        TextView salary = new TextView(this);
        salary.setText(result.getSalary() + "");
        llUserOtherDetailsContainer.addView(salary);


        TextView aquarius = new TextView(this);
        for (Constants.Aquarius a : Constants.Aquarius.values())
        {
            if (result.getAquarius().equals(a.getValue()))
            {
                aquarius.setText(a.getLabel());
            }
        }
        llUserOtherDetailsContainer.addView(aquarius);

        TextView smoking = new TextView(this);
        if (result.getSmoking())
        {
            smoking.setText("Smokes");
        } else
        {
            smoking.setText("Don't Smoke");
        }
        llUserOtherDetailsContainer.addView(smoking);


        TextView drinks = new TextView(this);
        if (result.getDrink())
        {
            drinks.setText("Drinks");
        } else
        {
            drinks.setText("Don't Drink");
        }
        llUserOtherDetailsContainer.addView(drinks);

        TextView weed = new TextView(this);
        if (result.getSmoking())
        {
            weed.setText("Takes Weed");
        } else
        {
            weed.setText("No Weed");
        }
        llUserOtherDetailsContainer.addView(weed);


        TextView joined = new TextView(this);
        joined.setText("Joined " + DateTime.convertDate("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'",
                "MMMM d, Y", result.getCreatedDate()));
        llUserOtherDetailsContainer.addView(joined);

        for (int i = 0; i < llUserOtherDetailsContainer.getChildCount(); i++)
        {
            llUserOtherDetailsContainer.getChildAt(i).setPadding(0, 0, 0, 10);
        }

    }

    private void setStatus(User.ResultBean result)
    {
        for (Constants.Status s : Constants.Status.values())
        {
            if (result.getStatus().equals(s.getValue()))
            {
                tvStatus.setText(s.getLabel());
            }
        }
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

    @OnClick({R.id.ibtn_dislike, R.id.ibtn_gift, R.id.ibtn_like})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ibtn_dislike:
                break;
            case R.id.ibtn_gift:
                DialogSendGifts gifts = new DialogSendGifts(this);
                gifts.show();
                break;
            case R.id.ibtn_like:
                break;
        }
    }

    private void getOtherImages()
    {
        API.Profile.getOtherPictures(1, SharedPreferences.getFlirtjarUserToken(this),
                new RetrofitCallback<OtherPictures>(this)
                {
                    @Override
                    public void onResponse(Call<OtherPictures> call, Response<OtherPictures> response)
                    {
                        super.onResponse(call, response);
                        if (response.isSuccessful())
                        {
                            for (OtherPictures.ResultBean image : response.body().getResult())
                            {
                                otherPictures.add(image);
                            }
                            carouselView.setImageListener(ActivityProfileView.this);
                            carouselView.setPageCount(otherPictures.size());
                        } else
                        {
                            carouselView.setImageListener(ActivityProfileView.this);
                            carouselView.setPageCount(otherPictures.size());
                        }
                    }

                    @Override
                    public void onFailure(Call<OtherPictures> call, Throwable t)
                    {
                        super.onFailure(call, t);
                        carouselView.setImageListener(ActivityProfileView.this);
                        carouselView.setPageCount(otherPictures.size());
                    }
                });
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
                getOtherImages();
                bindUserDetailsToUi(response.body());
            }

        }
    }

}
