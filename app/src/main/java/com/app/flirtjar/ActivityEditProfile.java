package com.app.flirtjar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import api.API;
import api.RetrofitCallback;
import apimodels.UpdateUser;
import apimodels.User;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import utils.SharedPreferences;

public class ActivityEditProfile extends BaseActivity
{
    final User.ResultBean userToBeUpdated = new User.ResultBean();
    @BindView(R.id.cl_activityEditProfile)
    CoordinatorLayout clActivityEditProfile;
    @BindView(R.id.btn_saveProfile)
    Button btnSaveProfile;
    @BindView(R.id.tv_height)
    EditText tvHeight;
    @BindView(R.id.tv_locality)
    EditText tvLocality;
    @BindView(R.id.tv_hairColor)
    EditText tvHairColor;
    @BindView(R.id.tv_eyeColor)
    EditText tvEyeColor;
    @BindView(R.id.tv_occupation)
    EditText tvOccupation;
    @BindView(R.id.tv_salary)
    EditText tvSalary;
    @BindView(R.id.rb_drinkYes)
    RadioButton rbDrinkYes;
    @BindView(R.id.rb_drinkNo)
    RadioButton rbDrinkNo;
    @BindView(R.id.rb_drink)
    RadioGroup rbDrink;
    @BindView(R.id.rb_smokeYes)
    RadioButton rbSmokeYes;
    @BindView(R.id.rb_smokeNo)
    RadioButton rbSmokeNo;
    @BindView(R.id.rb_smoke)
    RadioGroup rbSmoke;
    @BindView(R.id.rb_weedYes)
    RadioButton rbWeedYes;
    @BindView(R.id.rb_weedNo)
    RadioButton rbWeedNo;
    @BindView(R.id.rb_weed)
    RadioGroup rbWeed;

    public static void start(Context context)
    {
        context.startActivity(new Intent(context, ActivityEditProfile.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final User.ResultBean user = App.getInstance().getUser().getResult();
        if (user == null)
        {
            Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show();
            finish();
        }

        User.ResultBean.copy(user, userToBeUpdated);

        rbDrink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedRadioButtonId)
            {
                switch (checkedRadioButtonId)
                {
                    case R.id.rb_drinkYes:
                        userToBeUpdated.setDrink(true);
                        break;
                    case R.id.rb_drinkNo:
                        userToBeUpdated.setDrink(false);
                        break;
                }
            }
        });

        rbWeed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedRadioButtonId)
            {
                switch (checkedRadioButtonId)
                {
                    case R.id.rb_weedYes:
                        userToBeUpdated.setWeed(true);
                        break;
                    case R.id.rb_weedNo:
                        userToBeUpdated.setWeed(false);
                        break;
                }
            }
        });

        rbSmoke.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedRadioButtonId)
            {
                switch (checkedRadioButtonId)
                {
                    case R.id.rb_smokeYes:
                        userToBeUpdated.setSmoking(true);
                        break;
                    case R.id.rb_smokeNo:
                        userToBeUpdated.setSmoking(false);
                        break;
                }
            }
        });

        setDetailsFromUser();

    }

    private void setDetailsFromUser()
    {

        tvEyeColor.setText(userToBeUpdated.getEyeColor());
        tvHairColor.setText(userToBeUpdated.getHairColor());
        tvHeight.setText(userToBeUpdated.getHeight() + "");
        tvOccupation.setText(userToBeUpdated.getOccupation());
        tvSalary.setText(userToBeUpdated.getSalary() + "");

        if (userToBeUpdated.getDrink())
        {
            rbDrinkYes.setChecked(true);
        } else
        {
            rbDrinkNo.setChecked(true);
        }

        if (userToBeUpdated.getSmoking())
        {
            rbSmokeYes.setChecked(true);
        } else
        {
            rbSmokeNo.setChecked(true);
        }

        if (userToBeUpdated.getWeed())
        {
            rbWeedYes.setChecked(true);
        } else
        {
            rbWeedNo.setChecked(true);
        }

    }

    @Override
    protected int getLayoutResourceId()
    {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void showNoInternetView()
    {

    }

    @OnClick(R.id.btn_saveProfile)
    public void onClick()
    {
        promptUserToSaveProfileDetails();
    }

    private void promptUserToSaveProfileDetails()
    {
        new AlertDialog.Builder(this)
                .setMessage("Save Profile Details")
                .setPositiveButton("Save", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        trySavingProfileDetails();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void trySavingProfileDetails()
    {
        userToBeUpdated.setHeight(Double.valueOf(tvHeight.getText().toString()));
        userToBeUpdated.setHairColor(tvHairColor.getText().toString());
        userToBeUpdated.setEyeColor(tvEyeColor.getText().toString());
        userToBeUpdated.setOccupation(tvOccupation.getText().toString());
        userToBeUpdated.setSalary(Integer.valueOf(tvSalary.getText().toString()));

        final RetrofitCallback<UpdateUser> onUserDetailsUpdated = new RetrofitCallback<UpdateUser>(this)
        {
            @Override
            public void onResponse(Call<UpdateUser> call, Response<UpdateUser> response)
            {
                super.onResponse(call, response);
                if (response.isSuccessful())
                {
                    User.ResultBean.copy(userToBeUpdated, App.getInstance().getUser().getResult());
                    Toast.makeText(ActivityEditProfile.this, "Profile Details Saved.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };

        API.User.updateUserDetails(userToBeUpdated, SharedPreferences.getFlirtjarUserToken(this), onUserDetailsUpdated);
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
}
