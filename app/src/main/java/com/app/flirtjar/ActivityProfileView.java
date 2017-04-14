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

    int userId;

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
            userId = App.getInstance().getUser().getResult().getId();
            getProfileDetails(token);
        } else
        {
            llJarOptionsContainer.setVisibility(View.VISIBLE);
            userId = getIntent().getIntExtra(Constants.USER_ID, 0);
            getOtherUserProfileDetails(userId);
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
        lookingFor.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_looking_for, 0, 0, 0);
        lookingFor.setCompoundDrawablePadding(15);
        for (Constants.Gender g : Constants.Gender.values())
        {
            if (result.getLookingFor().equals(g.getValue()))
            {
                lookingFor.setText("Looking For " + g.getLabel());
            }
        }
        llUserOtherDetailsContainer.addView(lookingFor);


        TextView height = new TextView(this);
        height.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_height, 0, 0, 0);
        height.setCompoundDrawablePadding(15);
        if (result.getHeight() == 0.0)
        {
            height.setText("Height Not Specified");
        } else
        {
            height.setText("Height " + result.getHeight() + "");
        }
        llUserOtherDetailsContainer.addView(height);


        TextView hairColor = new TextView(this);
        hairColor.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_hair, 0, 0, 0);
        hairColor.setCompoundDrawablePadding(15);
        for (Constants.HairColor h : Constants.HairColor.values())
        {
            if (result.getHairColor().equals(h.getValue()))
            {
                hairColor.setText("Hair Color " + h.getLabel());
            }
        }
        llUserOtherDetailsContainer.addView(hairColor);


        TextView eyeColor = new TextView(this);
        eyeColor.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_eye_colour, 0, 0, 0);
        eyeColor.setCompoundDrawablePadding(15);
        for (Constants.EyeColor e : Constants.EyeColor.values())
        {
            if (result.getEyeColor().equals(e.getValue()))
            {
                eyeColor.setText("Eye Color " + e.getLabel());
            }
        }
        llUserOtherDetailsContainer.addView(eyeColor);


        TextView occupation = new TextView(this);
        occupation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_occupation, 0, 0, 0);
        occupation.setCompoundDrawablePadding(15);
        occupation.setText(result.getOccupation());
        llUserOtherDetailsContainer.addView(occupation);


        TextView salary = new TextView(this);
        salary.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dollar, 0, 0, 0);
        salary.setCompoundDrawablePadding(15);
        salary.setText(result.getSalary() + "");
        llUserOtherDetailsContainer.addView(salary);


        TextView aquarius = new TextView(this);
        aquarius.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_zodiac, 0, 0, 0);
        aquarius.setCompoundDrawablePadding(15);
        for (Constants.Aquarius a : Constants.Aquarius.values())
        {
            if (result.getAquarius().equals(a.getValue()))
            {
                aquarius.setText(a.getLabel());
            }
        }
        llUserOtherDetailsContainer.addView(aquarius);

        TextView smoking = new TextView(this);
        smoking.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_smoking, 0, 0, 0);
        smoking.setCompoundDrawablePadding(15);
        if (result.getSmoking())
        {
            smoking.setText("Smokes");
        } else
        {
            smoking.setText("Don't Smoke");
        }
        llUserOtherDetailsContainer.addView(smoking);


        TextView drinks = new TextView(this);
        drinks.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bear, 0, 0, 0);
        drinks.setCompoundDrawablePadding(15);
        if (result.getDrink())
        {
            drinks.setText("Drinks");
        } else
        {
            drinks.setText("Don't Drink");
        }
        llUserOtherDetailsContainer.addView(drinks);

        TextView weed = new TextView(this);
        weed.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_weed, 0, 0, 0);
        weed.setCompoundDrawablePadding(15);
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
        API.Profile.getOtherPictures(userId, SharedPreferences.getFlirtjarUserToken(this),
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
