package instagram.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.flirtjar.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.API;
import api.RetrofitCallback;
import apimodels.OnAddPicturesResponse;
import apimodels.Picture;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import instagram.InstaPhotosAdapter;
import instagram.apimodels.RecentMedia;
import retrofit2.Call;
import retrofit2.Response;
import utils.Display;
import utils.SharedPreferences;

/**
 * Created by rutvik on 3/20/2017 at 4:07 PM.
 */

public class UserPhotosDialog extends Dialog implements RecentMedia.DataBean.ImagesBean.OnImageCheckUncheckListener
{
    final Map<Integer, Picture> pictureMap = new HashMap<>();
    @BindView(R.id.pb_loadingInstaUser)
    ProgressBar pbLoadingInstaUser;
    @BindView(R.id.rv_instaPhotos)
    RecyclerView rvInstaPhotos;
    @BindView(R.id.rl_instaUserDetailsContainer)
    RelativeLayout rlInstaUserDetailsContainer;
    InstaPhotosAdapter adapter;
    Call<RecentMedia> call;
    @BindView(R.id.btn_instaPhotosCancel)
    Button btnInstaPhotosCancel;
    @BindView(R.id.btn_instaPhotosDone)
    Button btnInstaPhotosDone;

    public UserPhotosDialog(@NonNull Context context)
    {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_insta_user_photos);

        ButterKnife.bind(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

        rvInstaPhotos.setLayoutManager(new GridLayoutManager(getContext(),
                Display.calculateNoOfColumns(getContext(), 150)));

        rvInstaPhotos.setHasFixedSize(true);

        adapter = new InstaPhotosAdapter(getContext());

        rvInstaPhotos.setAdapter(adapter);

        getInstaPhotos();

    }

    private void getInstaPhotos()
    {
        RetrofitCallback<RecentMedia> onGetRecentMedia = new RetrofitCallback<RecentMedia>(getContext())
        {
            @Override
            public void onResponse(Call<RecentMedia> call, Response<RecentMedia> response)
            {
                super.onResponse(call, response);
                if (response.isSuccessful())
                {
                    bindMediaToUi(response.body().getData());
                } else
                {
                    Toast.makeText(getContext(), "Unable to get media.", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<RecentMedia> call, Throwable t)
            {
                super.onFailure(call, t);
                Toast.makeText(getContext(), "Unable to get media.", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        };

        call = API.Instagram
                .getRecentMedia(SharedPreferences.getInstaAccessToken(getContext()), onGetRecentMedia);

    }

    private void bindMediaToUi(List<RecentMedia.DataBean> data)
    {
        pbLoadingInstaUser.setVisibility(View.GONE);
        rlInstaUserDetailsContainer.setVisibility(View.VISIBLE);

        for (RecentMedia.DataBean singleData : data)
        {
            singleData.getImages().setOnImageCheckUncheckListener(this);
            adapter.addItem(singleData.getImages());
        }
    }

    @Override
    public void onDetachedFromWindow()
    {
        if (call != null)
        {
            call.cancel();
        }
        super.onDetachedFromWindow();
    }

    @Override
    public void onCheckChanged(boolean isChecked, RecentMedia.DataBean.ImagesBean imagesBean)
    {
        if (isChecked)
        {
            final Picture picture = new Picture();
            picture.setImage(imagesBean.getStandardResolution().getUrl());
            pictureMap.put(imagesBean.hashCode(), picture);
        } else
        {
            pictureMap.remove(imagesBean.hashCode());
        }
    }

    @OnClick({R.id.btn_instaPhotosCancel, R.id.btn_instaPhotosDone})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_instaPhotosCancel:
                dismiss();
                break;
            case R.id.btn_instaPhotosDone:
                addImagesToServer();
                break;
        }
    }

    private void addImagesToServer()
    {
        if (pictureMap.isEmpty())
        {
            Toast.makeText(getContext(), "Please select photos to upload.", Toast.LENGTH_SHORT).show();
            return;
        }
        btnInstaPhotosDone.setEnabled(false);

        RetrofitCallback<OnAddPicturesResponse> onAddPicture =
                new RetrofitCallback<OnAddPicturesResponse>(getContext())
                {
                    @Override
                    public void onResponse(Call<OnAddPicturesResponse> call, Response<OnAddPicturesResponse> response)
                    {
                        super.onResponse(call, response);
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Instagram Photos Added.", Toast.LENGTH_SHORT).show();
                            dismiss();
                        } else
                        {
                            Toast.makeText(getContext(), "Failed to add images.", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<OnAddPicturesResponse> call, Throwable t)
                    {
                        super.onFailure(call, t);
                        Toast.makeText(getContext(), "Failed to add images.", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                };

        final List<Picture> pictures = new ArrayList<>(pictureMap.values());

        API.Profile.addPictures(pictures,
                SharedPreferences.getFlirtjarUserToken(getContext()), onAddPicture);
    }
}
