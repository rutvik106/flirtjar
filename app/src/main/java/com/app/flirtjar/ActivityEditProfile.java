package com.app.flirtjar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import api.API;
import api.RetrofitCallback;
import apimodels.UpdateUser;
import apimodels.User;
import butterknife.BindView;
import butterknife.OnClick;
import instagram.dialogs.UserPhotosDialog;
import retrofit2.Call;
import retrofit2.Response;
import utils.Constants;
import utils.SharedPreferences;

public class ActivityEditProfile extends BaseActivity
{
    final User.ResultBean userToBeUpdated = new User.ResultBean();
    final List<Constants.HairColor> hairColor = new LinkedList<>();
    final List<Constants.EyeColor> eyeColors = new LinkedList<>();
    final List<Constants.Aquarius> aquariusList = new LinkedList<>();
    final List<Constants.Gender> genderList = new LinkedList<>();
    final List<Constants.Status> statusList = new LinkedList<>();
    @BindView(R.id.cl_activityEditProfile)
    CoordinatorLayout clActivityEditProfile;
    @BindView(R.id.btn_saveProfile)
    Button btnSaveProfile;
    @BindView(R.id.tv_height)
    EditText tvHeight;
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
    @BindView(R.id.spin_hairColor)
    AppCompatSpinner spinHairColor;
    @BindView(R.id.spin_eyeColor)
    AppCompatSpinner spinEyeColor;
    Constants.EyeColor selectedEyeColor;

    Constants.HairColor selectedHairColor;

    Constants.Gender selectedLookingFor;

    Constants.Aquarius selectedAquarius;

    Constants.Status selectedStatus;

    @BindView(R.id.spin_aquarius)
    AppCompatSpinner spinAquarius;
    @BindView(R.id.spin_lookingFor)
    AppCompatSpinner spinLookingFor;
    @BindView(R.id.spin_status)
    AppCompatSpinner spinStatus;

    @BindView(R.id.tv_tagLine)
    EditText tvTagLine;
    @BindView(R.id.btn_addInstagramPhotos)
    Button btnAddInstagramPhotos;

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

        setupRadioListeners();

        setupSpinnersAndAdapters();

        setDetailsFromUser();

    }

    @Override
    protected int getLayoutResourceId()
    {
        return R.layout.activity_edit_profile;
    }

    private void setupRadioListeners()
    {
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
    }

    private void setupSpinnersAndAdapters()
    {
        setupStatusSpinner();
        setupAquariusSpinner();
        setupLookingForSpinner();
        setupEyeColorSpinner();
        setupHairColorSpinner();
    }

    private void setupStatusSpinner()
    {

        if (userToBeUpdated.getStatus().equals(""))
        {
            statusList.add(Constants.Status.NONE);
        }

        statusList.add(Constants.Status.COFFEE);
        statusList.add(Constants.Status.DETOUR);
        statusList.add(Constants.Status.DINNER);
        statusList.add(Constants.Status.LONG_DRIVE);
        statusList.add(Constants.Status.LUNCH);
        statusList.add(Constants.Status.MOVIE);
        statusList.add(Constants.Status.WALK);

        spinStatus.setAdapter(new ArrayAdapter<Constants.Status>(this, android.R.layout.simple_list_item_1, statusList)
        {

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setText(statusList.get(position).getLabel());

                return convertView;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setText(statusList.get(position).getLabel());

                return convertView;
            }
        });

        spinStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedStatus = (Constants.Status) adapterView.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        //[END]*************************************************************************************

    }

    private void setupAquariusSpinner()
    {

        //AQUARIUS SPINNER ADAPTER AND LISTENER***************************************************

        if (userToBeUpdated.getAquarius().equals(""))
        {
            aquariusList.add(Constants.Aquarius.NONE);
        }

        aquariusList.add(Constants.Aquarius.Aquarius);
        aquariusList.add(Constants.Aquarius.Aries);
        aquariusList.add(Constants.Aquarius.Cancer);
        aquariusList.add(Constants.Aquarius.Capricorn);
        aquariusList.add(Constants.Aquarius.Gemini);
        aquariusList.add(Constants.Aquarius.Leo);
        aquariusList.add(Constants.Aquarius.Libra);
        aquariusList.add(Constants.Aquarius.Pisces);
        aquariusList.add(Constants.Aquarius.Sagittarius);
        aquariusList.add(Constants.Aquarius.Scorpio);
        aquariusList.add(Constants.Aquarius.Taurus);
        aquariusList.add(Constants.Aquarius.Virgo);


        spinAquarius.setAdapter(new ArrayAdapter<Constants.Aquarius>(this, android.R.layout.simple_list_item_1, aquariusList)
        {

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setText(aquariusList.get(position).getLabel());

                return convertView;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setText(aquariusList.get(position).getLabel());

                return convertView;
            }
        });

        spinAquarius.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedAquarius = (Constants.Aquarius) adapterView.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        //[END]*************************************************************************************

    }

    private void setupHairColorSpinner()
    {
        //HAIR COLOR SPINNER ADAPTER AND LISTENER***************************************************

        if (userToBeUpdated.getHairColor().equals(""))
        {
            hairColor.add(Constants.HairColor.NONE);
        }

        hairColor.add(Constants.HairColor.BLACK);
        hairColor.add(Constants.HairColor.BROWN);
        hairColor.add(Constants.HairColor.BLUE);


        spinHairColor.setAdapter(new ArrayAdapter<Constants.HairColor>(this, android.R.layout.simple_list_item_1, hairColor)
        {

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setText(hairColor.get(position).getLabel());

                return convertView;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setText(hairColor.get(position).getLabel());

                return convertView;
            }
        });

        spinHairColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedHairColor = (Constants.HairColor) adapterView.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        //[END]*************************************************************************************
    }

    private void setupEyeColorSpinner()
    {
        //EYE COLOR SPINNER ADAPTER AND LISTENER****************************************************

        if (userToBeUpdated.getEyeColor().equals(""))
        {
            eyeColors.add(Constants.EyeColor.NONE);
        }

        eyeColors.add(Constants.EyeColor.BLACK);
        eyeColors.add(Constants.EyeColor.BROWN);
        eyeColors.add(Constants.EyeColor.BLUE);

        spinEyeColor.setAdapter(new ArrayAdapter<Constants.EyeColor>(this, android.R.layout.simple_list_item_1, eyeColors)
        {

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setText(eyeColors.get(position).getLabel());

                return convertView;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setText(eyeColors.get(position).getLabel());

                return convertView;
            }
        });

        spinEyeColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedEyeColor = (Constants.EyeColor) adapterView.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        //[END]*************************************************************************************
    }

    private void setupLookingForSpinner()
    {
        //LOOKING FOR SPINNER ADAPTER AND LISTENER****************************************************
        if (userToBeUpdated.getLookingFor().equals(""))
        {
            genderList.add(Constants.Gender.NONE);
        }
        genderList.add(Constants.Gender.MALE);
        genderList.add(Constants.Gender.FEMALE);
        genderList.add(Constants.Gender.UNSPECIFIED);


        spinLookingFor.setAdapter(new ArrayAdapter<Constants.Gender>(this, android.R.layout.simple_list_item_1, genderList)
        {

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setText(genderList.get(position).getLabel());

                return convertView;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
                text1.setText(genderList.get(position).getLabel());

                return convertView;
            }
        });

        spinLookingFor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedLookingFor = (Constants.Gender) adapterView.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    private void setDetailsFromUser()
    {

        for (Constants.Status single : statusList)
        {
            if (userToBeUpdated.getStatus().equals(single.getValue()))
            {
                spinStatus.setSelection(statusList.indexOf(single));
            }
        }

        for (Constants.Aquarius single : aquariusList)
        {
            if (userToBeUpdated.getAquarius().equals(single.getValue()))
            {
                spinAquarius.setSelection(aquariusList.indexOf(single));
            }
        }

        for (Constants.Gender single : genderList)
        {
            if (userToBeUpdated.getLookingFor().equals(single.getValue()))
            {
                spinLookingFor.setSelection(genderList.indexOf(single));
            }
        }

        for (Constants.EyeColor single : eyeColors)
        {
            if (userToBeUpdated.getEyeColor().equals(single.getValue()))
            {
                spinEyeColor.setSelection(eyeColors.indexOf(single));
            }
        }

        for (Constants.HairColor single : hairColor)
        {
            if (userToBeUpdated.getHairColor().equals(single.getValue()))
            {
                spinHairColor.setSelection(hairColor.indexOf(single));
            }
        }

        tvTagLine.setText(userToBeUpdated.getTagline());
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
    protected void internetNotAvailable()
    {

    }

    @Override
    protected void internetAvailable()
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
                .setMessage("Save Profile Details?")
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
        if (!tvHeight.getText().toString().trim().isEmpty())
        {
            userToBeUpdated.setHeight(Double.valueOf(tvHeight.getText().toString()));
        }
        userToBeUpdated.setStatus(selectedStatus.getValue());
        userToBeUpdated.setAquarius(selectedAquarius.getValue());
        userToBeUpdated.setLookingFor(selectedLookingFor.getValue());
        userToBeUpdated.setHairColor(selectedHairColor.getValue());
        userToBeUpdated.setEyeColor(selectedEyeColor.getValue());
        userToBeUpdated.setOccupation(tvOccupation.getText().toString());
        userToBeUpdated.setTagline(tvTagLine.getText().toString());
        if (!tvSalary.getText().toString().trim().isEmpty())
        {
            userToBeUpdated.setSalary(Integer.valueOf(tvSalary.getText().toString()));
        }

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
            promptUserForExitConfirmation();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        promptUserForExitConfirmation();
    }

    private void promptUserForExitConfirmation()
    {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to go back? Any unsaved changes will be lost.")
                .setPositiveButton("Go Back", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

    }

    @OnClick(R.id.btn_addInstagramPhotos)
    public void addInstagramPhotos()
    {
        new UserPhotosDialog(this).show();
    }
}
