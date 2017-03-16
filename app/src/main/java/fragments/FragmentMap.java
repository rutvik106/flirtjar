package fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.flirtjar.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import api.API;
import api.ApiInterface;
import api.RetrofitCallback;
import apimodels.NearByUser;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
import utils.Constants;
import utils.FusedLocation;
import utils.SharedPreferences;

/**
 * Created by rutvik on 2/1/2017 at 6:26 PM.
 */

public class FragmentMap extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener
{

    SyncedMapFragment syncedMapFragment;
    @BindView(R.id.tv_meetUpTo)
    TextView tvMeetUpTo;
    @BindView(R.id.fam_meetUpTo)
    FloatingActionMenu famMeetUpTo;
    @BindView(R.id.fab_detour)
    FloatingActionButton fabDetour;
    @BindView(R.id.fl_blackBackDrop)
    FrameLayout flBlackBackDrop;
    Call<NearByUser> call;
    FusedLocation fusedLocation;
    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);

        syncedMapFragment = (SyncedMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.frag_map);

        syncedMapFragment.getMapAsync(this);

        final Typeface pacifico = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Pacifico-Regular.ttf");

        tvMeetUpTo.setTypeface(pacifico);

        famMeetUpTo.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener()
        {
            @Override
            public void onMenuToggle(boolean opened)
            {
                if (opened)
                {
                    tvMeetUpTo.setVisibility(View.VISIBLE);
                    flBlackBackDrop.setVisibility(View.VISIBLE);
                } else
                {
                    tvMeetUpTo.setVisibility(View.GONE);
                    flBlackBackDrop.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(true);

        fusedLocation = new FusedLocation(getActivity(), new FusedLocation.ApiConnectionCallbacks(null)
        {
            @Override
            public void onConnected(@Nullable Bundle bundle)
            {
                fusedLocation.startGettingLocation(new FusedLocation.GetLocation()
                {
                    @Override
                    public void onLocationChanged(Location location)
                    {
                        LatLng gujarat = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraPosition cp = new CameraPosition(gujarat, 18f, 60f, 0f);
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));

                        getNearByUsers();

                        fusedLocation.stopGettingLocation();
                    }
                });
            }
        }, this);

    }

    private void getNearByUsers()
    {
        if (getActivity() == null)
        {
            return;
        }
        if (call != null)
        {
            call.cancel();
        }
        final int distance = SharedPreferences
                .Settings.getDistanceSettings(getActivity());

        final ApiInterface.Location.LocationUnit locationUnit = SharedPreferences
                .Settings.getLocationUnit(getActivity());

        final String token = SharedPreferences.getFlirtjarUserToken(getActivity());

        call = API.Location
                .getNearByUsers(distance, locationUnit, token, new OnGetNearByUsers(getActivity()));

    }

    @Override
    public void onDestroyView()
    {
        if (call != null)
        {
            call.cancel();
        }
        super.onDestroyView();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Toast.makeText(getActivity(), "Please check internet connection.", Toast.LENGTH_SHORT).show();
    }

    public void setPushPinWithPic(final NearByUser.ResultBean nearByUser)
    {
        Glide.with(getActivity())
                .load(nearByUser.getProfilePicture())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(100, 100)
                {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
                    {
                        MarkerOptions mo = new MarkerOptions();
                        mo.title(nearByUser.getFirstName());

                        mo.position(new LatLng(nearByUser.getLocation().getCoordinates().get(1),
                                nearByUser.getLocation().getCoordinates().get(0)));

                        mo.icon(BitmapDescriptorFactory
                                .fromBitmap(createDrawableFromView(getActivity(), resource, nearByUser)));

                        mMap.addMarker(mo);
                    }
                });
    }

    // Convert a view to bitmap
    public Bitmap createDrawableFromView(Context context, Bitmap bmp, NearByUser.ResultBean nearByUser)
    {
        if (context == null)
        {
            return null;
        }
        final View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        final ImageView ivPushPinImage = (ImageView) view.findViewById(R.id.iv_pushPinImage);
        ivPushPinImage.setImageBitmap(bmp);

        final ImageView pushPin = (ImageView) view.findViewById(R.id.iv_pushPin);
        final String status = nearByUser.getStatus();
        if (!status.isEmpty())
        {
            if (status.equals(Constants.Status.DETOUR.getValue()))
            {
                pushPin.setImageResource(R.drawable.ic_map_pointer_detour);
            } else if (status.contains("out"))
            {
                pushPin.setImageResource(R.drawable.ic_map_pointer_go_out);
            } else if (status.equals(Constants.Status.MOVIE.getValue()))
            {
                pushPin.setImageResource(R.drawable.ic_map_pointer_movie);
            } else if (status.equals(Constants.Status.DRINK.getValue()))
            {
                pushPin.setImageResource(R.drawable.ic_map_pointer_drunk);
            } else if (status.contains("run"))
            {
                pushPin.setImageResource(R.drawable.ic_map_pointer_run);
            } else if (status.contains("bite"))
            {
                pushPin.setImageResource(R.drawable.ic_map_pointer_bite);
            }
        } else
        {
            pushPin.setImageResource(R.drawable.ic_map_pointer_default);
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    class OnGetNearByUsers extends RetrofitCallback<NearByUser>
    {

        public OnGetNearByUsers(Context context)
        {
            super(context);
        }

        @Override
        public void onResponse(Call<NearByUser> call, Response<NearByUser> response)
        {
            super.onResponse(call, response);
            if (response.isSuccessful())
            {
                for (NearByUser.ResultBean nearByUser : response.body().getResult())
                {
                    setPushPinWithPic(nearByUser);
                }
            }
        }

    }


}
