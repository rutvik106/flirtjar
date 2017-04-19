package com.app.flirtjar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapters.NotificationListAdapter;
import api.API;
import api.RetrofitCallback;
import apimodels.Coins;
import apimodels.CreateUser;
import apimodels.CreatedUser;
import apimodels.NotificationList;
import apimodels.ResponseOnCard;
import apimodels.UpdateUser;
import apimodels.User;
import apimodels.Views;
import butterknife.BindView;
import fragments.FlirtjarCard;
import fragments.FragmentChat;
import fragments.FragmentJar;
import fragments.FragmentMap;
import instagram.dialogs.InstagramLoginDialog;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import utils.Constants;
import utils.DateTime;
import utils.Dialog;
import utils.FusedLocation;
import utils.Logger;
import utils.SharedPreferences;

public class ActivityNavDrawer extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, GoogleApiClient.OnConnectionFailedListener,
        FlirtjarCard.FlirtCardEventListener, FragmentJar.FragmentJarCallbacks
{

    private static final String TAG = App.APP_TAG + ActivityNavDrawer.class.getSimpleName();
    final List<ResponseOnCard> responseOnCards = new ArrayList<>();
    private final Handler mHandler = new Handler();
    FragmentManager fragmentManager;
    @BindView(R.id.btn_nearby)
    ImageButton btnNearby;
    @BindView(R.id.btn_jar)
    ImageButton btnJar;
    @BindView(R.id.btn_chat)
    ImageButton btnChat;
    @BindView(R.id.ll_nearby)
    LinearLayout llNearby;
    @BindView(R.id.ll_jar)
    LinearLayout llJar;
    @BindView(R.id.ll_chat)
    LinearLayout llChat;
    TextView tvUsername;
    ImageView ivProfilePicture;
    int currentPage = -1;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    ActionBarDrawerToggle toggleLeft;
    View navigationViewHeaderLeft;
    View navigationViewHeaderRight;
    ActionBarDrawerToggle toggleRight;
    TextView tvLikeCount;
    TextView tvVisitedCount;
    TextView tvSuperlikeCount;
    TextView tvSkipCount;
    TextView tvUserCoins;
    FusedLocation fusedLocation;
    NotificationListAdapter notificationListAdapter;
    FragmentJar fragmentJar = null;
    FragmentMap fragmentMap = null;
    FragmentChat fragmentChat = null;
    Call<ResponseBody> callSaveResponseOnCard;
    NewNotificationReceiver newNotificationReceiver;
    private boolean fragmentsNotAdded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();

        btnJar.setSelected(true);

        llNearby.setOnClickListener(this);
        llJar.setOnClickListener(this);
        llChat.setOnClickListener(this);


        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        setupNavigationDrawer();

        getFacebookAuthToken();

    }

    private void setupNavigationDrawer()
    {
        toggleRight = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggleRight);
        toggleRight.syncState();

        NavigationView navigationViewRight = (NavigationView) findViewById(R.id.nav_viewRight);

        navigationViewHeaderRight = navigationViewRight.getHeaderView(0);

        navigationViewHeaderRight.setNestedScrollingEnabled(true);

        final RecyclerView rvNotifications = (RecyclerView) navigationViewHeaderRight
                .findViewById(R.id.rv_notifications);

        setupRecyclerViewForNotifications(rvNotifications);

        toggleLeft = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggleLeft);
        toggleLeft.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationViewHeaderLeft = navigationView.getHeaderView(0);

        navigationViewHeaderLeft.findViewById(R.id.ll_connectInsta)
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        tryLinkingInstagramAccount();
                    }
                });

        navigationViewHeaderLeft.findViewById(R.id.ib_settings)
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        startActivity(new Intent(ActivityNavDrawer.this, ActivitySetting.class));
                    }
                });

        tvLikeCount = (TextView) navigationViewHeaderLeft.findViewById(R.id.tv_likeCount);
        navigationViewHeaderLeft.findViewById(R.id.ll_viewLikes).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ActivityUsersGrid.start(ActivityNavDrawer.this, ActivityUsersGrid.Purpose.VIEW_LIKES);
            }
        });


        tvVisitedCount = (TextView) navigationViewHeaderLeft.findViewById(R.id.tv_visitedCount);
        navigationViewHeaderLeft.findViewById(R.id.ll_viewVisited)
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        ActivityUsersGrid.start(ActivityNavDrawer.this, ActivityUsersGrid.Purpose.VIEW_VISITED);
                    }
                });

        tvSuperlikeCount = (TextView) navigationViewHeaderLeft.findViewById(R.id.tv_superlikeCount);
        navigationViewHeaderLeft.findViewById(R.id.ll_viewSuperlike).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ActivityUsersGrid.start(ActivityNavDrawer.this, ActivityUsersGrid.Purpose.VIEW_SUPERLIKES);
            }
        });


        tvSkipCount = (TextView) navigationViewHeaderLeft.findViewById(R.id.tv_skipCount);
        tvUsername = (TextView) navigationViewHeaderLeft.findViewById(R.id.tv_username);
        tvUserCoins = (TextView) navigationViewHeaderLeft.findViewById(R.id.tv_userFlirtCoins);

        navigationViewHeaderLeft.findViewById(R.id.image_btn_edit)
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        ActivityEditProfile.start(ActivityNavDrawer.this);
                    }
                });

        ivProfilePicture = (ImageView) navigationViewHeaderLeft.findViewById(R.id.iv_profilePicture);

        ivProfilePicture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ActivityProfileView.start(ActivityNavDrawer.this, true, 0);
            }
        });

        toggleLeft.setDrawerIndicatorEnabled(false);

        toggleLeft.setHomeAsUpIndicator(R.drawable.ic_account_circle_white_24dp);

        toggleLeft.setToolbarNavigationClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (drawer.isDrawerVisible(GravityCompat.START))
                {
                    drawer.closeDrawer(GravityCompat.START);
                } else
                {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void tryLinkingInstagramAccount()
    {
        if (App.getInstance().getUser().getResult().isIsInstagramActivated() &&
                SharedPreferences.getInstaAccessToken(this) != null)
        {
            new AlertDialog.Builder(this)
                    .setMessage("You have already Linked Instagram, Do you want to Unlink?")
                    .setPositiveButton("Unlink", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            unlinkInstagram(dialogInterface);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        } else
        {
            new InstagramLoginDialog(ActivityNavDrawer.this).show();
        }
    }

    private void unlinkInstagram(final DialogInterface dialogInterface)
    {
        App.getInstance().getUser().getResult().setIsInstagramActivated(false);
        API.User.updateUserDetails(App.getInstance().getUser().getResult(),
                SharedPreferences.getFlirtjarUserToken(this),
                new RetrofitCallback<UpdateUser>(this)
                {
                    @Override
                    public void onResponse(Call<UpdateUser> call, Response<UpdateUser> response)
                    {
                        super.onResponse(call, response);
                        if (response.isSuccessful())
                        {
                            SharedPreferences.setInstaAccessToken(ActivityNavDrawer.this, null);
                            Toast.makeText(ActivityNavDrawer.this, "Successfully Unlinked.", Toast.LENGTH_SHORT).show();
                        } else
                        {
                            App.getInstance().getUser().getResult().setIsInstagramActivated(true);
                            Toast.makeText(ActivityNavDrawer.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        }
                        dialogInterface.dismiss();
                    }

                    @Override
                    public void onFailure(Call<UpdateUser> call, Throwable t)
                    {
                        super.onFailure(call, t);
                        App.getInstance().getUser().getResult().setIsInstagramActivated(true);
                        Toast.makeText(ActivityNavDrawer.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });
    }

    private void setupRecyclerViewForNotifications(RecyclerView rvNotifications)
    {
        notificationListAdapter = new NotificationListAdapter(this);
        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        //rvNotifications.setHasFixedSize(true);
        rvNotifications.setAdapter(notificationListAdapter);
        rvNotifications.setItemAnimator(new DefaultItemAnimator());
        rvNotifications.setNestedScrollingEnabled(true);
    }

    @Override
    protected int getLayoutResourceId()
    {
        return R.layout.activity_nav_drawer;
    }

    @Override
    protected void internetNotAvailable()
    {

    }

    @Override
    protected void internetAvailable()
    {

    }

    private void getFacebookAuthToken()
    {
        if (AccessToken.getCurrentAccessToken() == null || Profile.getCurrentProfile() == null)
        {
            Intent i = new Intent(this, ActivityLogin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            return;
        }


        getFacebookProfileDetails();
    }

    private void getFacebookProfileDetails()
    {
        FacebookProfileTracker fpt = new FacebookProfileTracker();
        fpt.startTracking();

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback()
                {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);
                        System.out.println(bFacebookData);

                        final String flirtjarUserToken = SharedPreferences
                                .getFlirtjarUserToken(ActivityNavDrawer.this);

                        if (flirtjarUserToken == null)
                        {
                            createNewFlirtjarUser(bFacebookData);
                        } else
                        {
                            setupBottomBar(1);
                            getCurrentUser(flirtjarUserToken);
                            getNotifications();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields",
                "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
        request.setParameters(parameters);
        request.executeAsync();

        Glide.with(this)
                .load(Profile.getCurrentProfile().getProfilePictureUri(200, 200))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(ivProfilePicture);

        Glide.with(this)
                .load(Profile.getCurrentProfile().getProfilePictureUri(90, 90))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(new SimpleTarget<GlideDrawable>()
                {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation)
                    {
                        toggleLeft.setHomeAsUpIndicator(resource);
                    }
                });

        tvUsername.setText(Profile.getCurrentProfile().getFirstName());

    }

    private void getNotifications()
    {
        final RetrofitCallback<NotificationList> onGetNotificationList =
                new RetrofitCallback<NotificationList>(this)
                {
                    @Override
                    public void onResponse(Call<NotificationList> call,
                                           Response<NotificationList> response)
                    {
                        super.onResponse(call, response);
                        if (response.isSuccessful())
                        {
                            if (notificationListAdapter != null)
                            {
                                notificationListAdapter.clear();
                                for (NotificationList.ResultBean notificationItem :
                                        response.body().getResult())
                                {
                                    notificationListAdapter.addItem(notificationItem);
                                    Log.i(TAG, "ADDING NOTIFICATION ITEM: " + notificationItem.getId());
                                }
                            }
                        }
                    }
                };

        API.Notifications.getNotifications(1, SharedPreferences.getFlirtjarUserToken(this),
                onGetNotificationList);
    }


    private void getCurrentUser(final String flirtjarUserToken)
    {
        final RetrofitCallback<User> onGetUser = new RetrofitCallback<User>(this)
        {
            @Override
            public void onResponse(Call<User> call, Response<User> response)
            {
                super.onResponse(call, response);
                if (response.isSuccessful())
                {
                    App.getInstance().setUser(response.body());
                    getViews(App.getInstance().getUser().getResult().getId(),
                            flirtjarUserToken);

                    getUserFlirtCoins();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t)
            {
                super.onFailure(call, t);
            }
        };

        API.User.getCurrentUser(flirtjarUserToken, onGetUser);
    }

    private void getUserFlirtCoins()
    {
        API.Profile.getCoins(SharedPreferences.getFlirtjarUserToken(this), new RetrofitCallback<Coins>(this)
        {
            @Override
            public void onResponse(Call<Coins> call, Response<Coins> response)
            {
                super.onResponse(call, response);
                if (response.isSuccessful())
                {
                    tvUserCoins.setText(response.body().getResult().getCoins() + "");
                }
            }
        });
    }

    private void getViews(int userId, String flirtjarUserToken)
    {
        final RetrofitCallback<Views> onGetViews = new RetrofitCallback<Views>(this)
        {
            @Override
            public void onResponse(Call<Views> call, final Response<Views> response)
            {
                super.onResponse(call, response);
                if (response.isSuccessful())
                {
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            tvLikeCount.setText(response.body().getResult().getLikes() + " >");
                            tvVisitedCount.setText(response.body().getResult().getPk() + " >");
                            tvSuperlikeCount.setText(response.body().getResult().getSuperlikes() + " >");
                            tvSkipCount.setText(response.body().getResult().getSkipped() + " >");

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Views> call, Throwable t)
            {
                super.onFailure(call, t);
            }
        };

        API.Profile.getViews(userId + "", flirtjarUserToken, onGetViews);
    }

    private void createNewFlirtjarUser(final Bundle profile)
    {
        Dialog.showLoadingDialog(this, "Please Wait...", "Creating account...", false, false, null);

        //CREATE MODEL FOR NEW USER

        final CreateUser.LocationBean locationDetails =
                new CreateUser.LocationBean();

        fusedLocation = new FusedLocation(this, new FusedLocation.ApiConnectionCallbacks(null)
        {
            @Override
            public void onConnected(@Nullable Bundle bundle)
            {
                fusedLocation.startGettingLocation(new FusedLocation.GetLocation()
                {
                    @Override
                    public void onLocationChanged(Location location)
                    {
                        final List<Double> lngLat = new ArrayList<Double>();
                        lngLat.add(location.getLongitude());
                        lngLat.add(location.getLatitude());
                        locationDetails.setCoordinates(lngLat);
                        locationDetails.setType("Point");
                        fusedLocation.stopGettingLocation();

                        createUser(profile, locationDetails);

                    }
                });
            }
        }, this);


    }

    public void createUser(Bundle profile, CreateUser.LocationBean locationDetails)
    {
        final CreateUser user = new CreateUser();
        user.setOauthId(AccessToken.getCurrentAccessToken().getUserId());
        user.setFirstName(profile.getString("first_name"));
        user.setLastName(profile.getString("last_name"));
        user.setLocation(locationDetails);

        final String gender = profile.getString("gender");
        if (gender != null)
        {
            if (gender.equalsIgnoreCase("male"))
            {
                user.setGender(Constants.Gender.MALE.getValue());
            } else if (gender.equalsIgnoreCase("female"))
            {
                user.setGender(Constants.Gender.FEMALE.getValue());
            } else
            {
                user.setGender(Constants.Gender.UNSPECIFIED.getValue());
            }
        }
        if (profile.getString("birthday") != null)
        {
            user.setDob(DateTime.convertDate("dd/MM/yyyy", "yyyy-MM-dd",
                    profile.getString("birthday")));
        }
        user.setEmail(profile.getString("email"));
        user.setProfilePicture(profile.getString("profile_pic"));
        //[CREATING MODEL ENDS HERE]


        //CREATE USER API METHOD CALLBACK
        final RetrofitCallback<CreatedUser> onUserCreated = new RetrofitCallback<CreatedUser>(this)
        {
            @Override
            public void onResponse(Call<CreatedUser> call, Response<CreatedUser> response)
            {
                super.onResponse(call, response);
                Dialog.hideLoadingDialog();

                if (response.code() == HttpURLConnection.HTTP_CREATED || response.isSuccessful())
                {
                    SharedPreferences.setFlirtjarUserToken(ActivityNavDrawer.this,
                            response.body().getResult().getToken());

                    getCurrentUser(SharedPreferences.getFlirtjarUserToken(ActivityNavDrawer.this));

                    setupBottomBar(1);
                }
            }

            @Override
            public void onFailure(Call<CreatedUser> call, Throwable t)
            {
                super.onFailure(call, t);
                Log.i(TAG, t.getMessage());
                Toast.makeText(ActivityNavDrawer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Dialog.hideLoadingDialog();
            }
        };

        //CALL API METHOD
        API.User.createUser(user, onUserCreated);
    }

    private void setupBottomBar(int position)
    {
        if (fragmentMap == null)
        {
            fragmentMap = new FragmentMap();
        }
        if (fragmentJar == null)
        {
            fragmentJar = FragmentJar.newInstance(this, this);
        }
        if (fragmentChat == null)
        {
            fragmentChat = FragmentChat.newInstance();
        }

        if (fragmentsNotAdded)
        {
            fragmentManager.beginTransaction()
                    .add(R.id.fl_fragmentContainer, fragmentMap)
                    .add(R.id.fl_fragmentContainer, fragmentJar)
                    .add(R.id.fl_fragmentContainer, fragmentChat)
                    .commitAllowingStateLoss();
            fragmentsNotAdded = false;
        }

        switch (position)
        {
            case 0:
                if (currentPage != position)
                {
                    currentPage = position;
                    fragmentManager.beginTransaction()
                            .show(fragmentMap)
                            .hide(fragmentJar)
                            .hide(fragmentChat)
                            .commitAllowingStateLoss();
                }
                break;

            case 1:
                if (currentPage != position)
                {
                    currentPage = position;
                    fragmentManager.beginTransaction()
                            .show(fragmentJar)
                            .hide(fragmentMap)
                            .hide(fragmentChat)
                            .commitNowAllowingStateLoss();

                }
                break;

            case 2:
                if (currentPage != position)
                {
                    currentPage = position;
                    fragmentManager.beginTransaction()
                            .show(fragmentChat)
                            .hide(fragmentJar)
                            .hide(fragmentMap)
                            .commitAllowingStateLoss();
                }
                break;

        }
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_notifications)
        {
            if (drawer.isDrawerVisible(GravityCompat.END))
            {
                drawer.closeDrawer(GravityCompat.END);
            } else
            {
                drawer.openDrawer(GravityCompat.END);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {
            // Handle the camera action
        } else if (id == R.id.nav_gallery)
        {

        } else if (id == R.id.nav_slideshow)
        {

        } else if (id == R.id.nav_manage)
        {

        } else if (id == R.id.nav_share)
        {

        } else if (id == R.id.nav_send)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ll_nearby:
                setupBottomBar(0);
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        btnNearby.setSelected(true);
                        btnJar.setSelected(false);
                        btnChat.setSelected(false);
                    }
                });
                break;

            case R.id.ll_jar:
                setupBottomBar(1);
                btnNearby.setSelected(false);
                btnJar.setSelected(true);
                btnChat.setSelected(false);
                break;

            case R.id.ll_chat:
                setupBottomBar(2);
                btnNearby.setSelected(false);
                btnJar.setSelected(false);
                btnChat.setSelected(true);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private Bundle getFacebookData(JSONObject object)
    {
        if (object == null)
        {
            return null;
        }
        try
        {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try
            {
                URL profile_pic = new URL("https://graph.facebook.com/" + id +
                        "/picture?width=500&height=500");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e)
            {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
            {
                bundle.putString("first_name", object.getString("first_name"));
            }
            if (object.has("last_name"))
            {
                bundle.putString("last_name", object.getString("last_name"));
            }
            if (object.has("email"))
            {
                bundle.putString("email", object.getString("email"));
            }
            if (object.has("gender"))
            {
                bundle.putString("gender", object.getString("gender"));
            }
            if (object.has("birthday"))
            {
                bundle.putString("birthday", object.getString("birthday"));
            }
            if (object.has("location"))
            {
                bundle.putString("location", object.getJSONObject("location").getString("name"));
            }

            return bundle;
        } catch (JSONException e)
        {
            Log.d(TAG, "Error parsing JSON");
            return null;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    @Override
    public void onSwipeIn(FlirtjarCard card)
    {
        fragmentJar.cardWasDisliked = false;
        fragmentJar.cardWasSuperliked = false;
        final ResponseOnCard responseOnCard = new ResponseOnCard();
        responseOnCard.setUserFrom(App.getInstance().getUser().getResult().getId());
        responseOnCard.setUserTo(card.getSingleCardUser().getId());
        if (fragmentJar.cardWasSuperliked)
        {
            responseOnCard.setResponse(ResponseOnCard.SUPERLIKE);
        } else
        {
            responseOnCard.setResponse(ResponseOnCard.LIKE);
        }
        responseOnCards.add(responseOnCard);
        if (responseOnCards.size() > 8)
        {
            trySavingResponseOnCards();
        }
    }

    @Override
    public void onSwipeOut(FlirtjarCard card)
    {
        fragmentJar.cardWasDisliked = true;
        fragmentJar.cardWasSuperliked = false;
        final ResponseOnCard responseOnCard = new ResponseOnCard();
        responseOnCard.setUserFrom(App.getInstance().getUser().getResult().getId());
        responseOnCard.setUserTo(card.getSingleCardUser().getId());
        responseOnCard.setResponse(ResponseOnCard.SKIPPED);
        responseOnCards.add(responseOnCard);
        if (responseOnCards.size() > 8)
        {
            trySavingResponseOnCards();
        }
    }

    private void trySavingResponseOnCards()
    {
        if (callSaveResponseOnCard != null)
        {
            callSaveResponseOnCard.cancel();
        }
        final RetrofitCallback<ResponseBody> onSaveCardResponses = new RetrofitCallback<ResponseBody>(this)
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                super.onResponse(call, response);
                if (response.isSuccessful())
                {
                    responseOnCards.clear();
                }
            }
        };

        callSaveResponseOnCard = API.Profile.saveResponseOnCards(App.getInstance()
                .getUser().getResult().getId(), responseOnCards, SharedPreferences
                .getFlirtjarUserToken(ActivityNavDrawer.this), onSaveCardResponses);

    }

    @Override
    public void onDestroyView()
    {
        if (!responseOnCards.isEmpty())
        {
            trySavingResponseOnCards();
        }
    }

    @Override
    public void onDetach()
    {
        if (!responseOnCards.isEmpty())
        {
            trySavingResponseOnCards();
        }
    }

    @Override
    protected void onStop()
    {
        if (newNotificationReceiver != null)
        {
            unregisterReceiver(newNotificationReceiver);
        }
        if (!responseOnCards.isEmpty())
        {
            trySavingResponseOnCards();
        }
        super.onStop();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (newNotificationReceiver == null)
        {
            newNotificationReceiver = new NewNotificationReceiver();
            registerReceiver(newNotificationReceiver, new IntentFilter(Constants.NEW_NOTIFICATION_RECEIVED));
        }
    }

    class FacebookProfileTracker extends ProfileTracker
    {
        @Override
        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile)
        {
            Toast.makeText(ActivityNavDrawer.this, "Profile Tracker Current Profile changed",
                    Toast.LENGTH_SHORT).show();
            Logger.log(TAG, currentProfile.getFirstName());
            Logger.log(TAG, currentProfile.getLastName());
            Logger.log(TAG, currentProfile.getProfilePictureUri(200, 200).toString());
        }
    }

    class NewNotificationReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            getNotifications();
        }
    }
}
