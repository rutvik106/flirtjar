package com.app.flirtjar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import adapters.UserGridAdapter;
import api.API;
import api.ApiInterface;
import api.RetrofitCallback;
import apimodels.GridUsers;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;
import utils.Display;
import utils.SharedPreferences;

public class ActivityUsersGrid extends BaseActivity
{

    @BindView(R.id.rv_usersGrid)
    RecyclerView rvUsersGrid;

    UserGridAdapter adapter;

    Call<GridUsers> call;

    public static void start(Context context, String purpose)
    {
        Intent i = new Intent(context, ActivityUsersGrid.class);
        i.putExtra(Purpose.PURPOSE, purpose);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        rvUsersGrid.setLayoutManager(new GridLayoutManager(this,
                Display.calculateNoOfColumns(this, 150)));
        rvUsersGrid.setHasFixedSize(true);

        adapter = new UserGridAdapter(this);

        rvUsersGrid.setAdapter(adapter);

        final String purpose = getIntent().getStringExtra(Purpose.PURPOSE);
        if (purpose.equals(Purpose.VIEW_LIKES))
        {
            tvNavLogoText.setText("Likes");
            getGridUsers(ApiInterface.Profile.ResponseType.LIKES);
        } else if (purpose.equals(Purpose.VIEW_SUPERLIKES))
        {
            tvNavLogoText.setText("Superlikes");
            getGridUsers(ApiInterface.Profile.ResponseType.SUPERLIKE);
        } else if (purpose.equals(Purpose.VIEW_VISITED))
        {
            tvNavLogoText.setText("Visitors");
            getGridUsers(ApiInterface.Profile.ResponseType.VIEWS);
        }

    }

    void getGridUsers(ApiInterface.Profile.ResponseType responseType)
    {
        if (call != null)
        {
            call.cancel();
        }

        final RetrofitCallback<GridUsers> onGetGridUsers = new RetrofitCallback<GridUsers>(this)
        {
            @Override
            public void onResponse(Call<GridUsers> call, Response<GridUsers> response)
            {
                super.onResponse(call, response);
                if (response.isSuccessful())
                {
                    for (GridUsers.ResultBean user : response.body().getResult())
                    {
                        adapter.addItem(user);
                    }
                }
            }
        };

        call = API.Profile.getViewUsers(App.getInstance().getUser().getResult().getId(),
                responseType, SharedPreferences.getFlirtjarUserToken(this), onGetGridUsers);

    }

    @Override
    protected int getLayoutResourceId()
    {
        return R.layout.activity_users_grid;
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
    protected void onDestroy()
    {
        if (call != null)
        {
            call.cancel();
        }
        super.onDestroy();
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

    public class Purpose
    {
        public static final String PURPOSE = "PURPOSE";
        public static final String VIEW_VISITED = "VIEW_LIKES";
        public static final String VIEW_SUPERLIKES = "VIEW_SUPERLIKES";
        public static final String VIEW_LIKES = "VIEW_LIKES";
    }
}
