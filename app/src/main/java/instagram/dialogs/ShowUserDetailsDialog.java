package instagram.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.flirtjar.App;
import com.app.flirtjar.R;
import com.bumptech.glide.Glide;

import api.API;
import api.RetrofitCallback;
import apimodels.UpdateUser;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import instagram.apimodels.AuthorizedUser;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Response;
import utils.SharedPreferences;

/**
 * Created by rutvik on 3/20/2017 at 11:50 AM.
 */

public class ShowUserDetailsDialog extends Dialog
{
    private static final String TAG = App.APP_TAG + ShowUserDetailsDialog.class.getSimpleName();
    final String requestToken;
    @BindView(R.id.iv_instaUserProfilePic)
    ImageView ivInstaUserProfilePic;
    @BindView(R.id.tv_instaUserFullName)
    TextView tvInstaUserFullName;
    @BindView(R.id.tv_instaUserBio)
    TextView tvInstaUserBio;
    Call<AuthorizedUser> call;
    Call<UpdateUser> updateUser;
    @BindView(R.id.btn_instaDone)
    Button btnInstaDone;
    @BindView(R.id.pb_loadingInstaUser)
    ProgressBar pbLoadingInstaUser;
    @BindView(R.id.rl_instaUserDetailsContainer)
    RelativeLayout rlInstaUserDetailsContainer;
    @BindView(R.id.btn_linkInstaPhotos)
    Button btnLinkInstaPhotos;

    public ShowUserDetailsDialog(@NonNull Context context, String requestToken)
    {
        super(context);
        this.requestToken = requestToken;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_insta_user);

        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

        App.getInstance().getUser().getResult().setIsInstagramActivated(true);

        Log.i(TAG, App.getInstance().getUser().getResult().getLocation().getCoordinates().toString());
        Log.i(TAG, App.getInstance().getUser().getResult().getLocation().getType());

        updateUserOnServer();

    }

    private void updateUserOnServer()
    {
        RetrofitCallback<UpdateUser> onUpdateUser = new RetrofitCallback<UpdateUser>(getContext())
        {
            @Override
            public void onResponse(Call<UpdateUser> call, Response<UpdateUser> response)
            {
                super.onResponse(call, response);
                if (response.isSuccessful())
                {
                    getInstaUserDetails();
                } else
                {
                    App.getInstance().getUser().getResult().setIsInstagramActivated(false);
                    Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<UpdateUser> call, Throwable t)
            {
                super.onFailure(call, t);
                App.getInstance().getUser().getResult().setIsInstagramActivated(false);
                Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        };

        updateUser = API.User.updateUserDetails(App.getInstance().getUser().getResult(),
                SharedPreferences.getFlirtjarUserToken(getContext()), onUpdateUser);
    }

    private void getInstaUserDetails()
    {

        final RetrofitCallback<AuthorizedUser> onGetAccessToken =
                new RetrofitCallback<AuthorizedUser>(getContext())
                {
                    @Override
                    public void onResponse(Call<AuthorizedUser> call, Response<AuthorizedUser> response)
                    {
                        super.onResponse(call, response);
                        if (response.isSuccessful())
                        {
                            SharedPreferences.setInstaAccessToken(getContext(),
                                    response.body().getAccessToken());
                            bindUserDetailsToUi(response.body());
                        } else
                        {
                            App.getInstance().getUser().getResult().setIsInstagramActivated(false);
                            Toast.makeText(getContext(), "Trouble getting data from Instagram.", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthorizedUser> call, Throwable t)
                    {
                        super.onFailure(call, t);
                        App.getInstance().getUser().getResult().setIsInstagramActivated(false);
                        Toast.makeText(getContext(), "Trouble getting data from Instagram.", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                };

        call = API.Instagram.getAccessToken(requestToken, onGetAccessToken);

    }

    private void bindUserDetailsToUi(AuthorizedUser instaUser)
    {
        pbLoadingInstaUser.setVisibility(View.GONE);
        rlInstaUserDetailsContainer.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load(instaUser.getUser().getProfilePicture())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(ivInstaUserProfilePic);

        tvInstaUserFullName.setText(instaUser.getUser().getFullName());

        tvInstaUserBio.setText(instaUser.getUser().getBio());
    }

    @OnClick(R.id.btn_instaDone)
    public void onClick()
    {
        dismiss();
    }

    @Override
    public void onDetachedFromWindow()
    {
        if (call != null)
        {
            call.cancel();
        }
        if (updateUser != null)
        {
            updateUser.cancel();
        }
        super.onDetachedFromWindow();
    }

    @OnClick(R.id.btn_linkInstaPhotos)
    public void onClickAddPhotos()
    {
        new UserPhotosDialog(getContext()).show();
    }
}
