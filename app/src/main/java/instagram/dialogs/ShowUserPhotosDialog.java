package instagram.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.flirtjar.R;
import com.bumptech.glide.Glide;

import api.API;
import api.RetrofitCallback;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import instagram.apimodels.AuthorizedUser;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by rutvik on 3/20/2017 at 11:50 AM.
 */

public class ShowUserPhotosDialog extends Dialog
{
    final String requestToken;
    @BindView(R.id.iv_instaUserProfilePic)
    ImageView ivInstaUserProfilePic;
    @BindView(R.id.tv_instaUserFullName)
    TextView tvInstaUserFullName;
    @BindView(R.id.tv_instaUserBio)
    TextView tvInstaUserBio;
    Call<AuthorizedUser> call;
    @BindView(R.id.btn_instaDone)
    Button btnInstaDone;
    @BindView(R.id.pb_loadingInstaUser)
    ProgressBar pbLoadingInstaUser;
    @BindView(R.id.rl_instaUserDetailsContainer)
    RelativeLayout rlInstaUserDetailsContainer;

    public ShowUserPhotosDialog(@NonNull Context context, String requestToken)
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

        getInstaUserDetails();

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
                            bindUserDetailsToUi(response.body());
                        } else
                        {
                            Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthorizedUser> call, Throwable t)
                    {
                        super.onFailure(call, t);
                        Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
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
                .into(ivInstaUserProfilePic);

        tvInstaUserFullName.setText(instaUser.getUser().getFullName());

        tvInstaUserBio.setText(instaUser.getUser().getBio());
    }

    @OnClick(R.id.btn_instaDone)
    public void onClick()
    {
        dismiss();
    }
}
