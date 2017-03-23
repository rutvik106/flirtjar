package fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.flirtjar.App;
import com.app.flirtjar.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import api.API;
import api.RetrofitCallback;
import apimodels.Cards;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dialogs.DialogSendGifts;
import retrofit2.Call;
import retrofit2.Response;
import utils.SharedPreferences;

/**
 * Created by rutvik on 2/5/2017 at 4:13 PM.
 */

public class FragmentJar extends Fragment
{

    public static final String TAG = App.APP_TAG + FragmentJar.class.getSimpleName();
    private final Handler mHandler = new Handler();
    public boolean cardWasDisliked = false;
    public boolean cardWasSuperliked = false;
    @BindView(R.id.ibtn_like)
    ImageButton ibtnLike;
    @BindView(R.id.ibtn_dislike)
    ImageButton ibtnDislike;
    @BindView(R.id.ibtn_super_like)
    ImageButton ibtnSuperLike;
    @BindView(R.id.ib_return)
    ImageButton ibReturn;
    @BindView(R.id.ibtn_gift)
    ImageButton ibtnGift;
    @BindView(R.id.iv_loadingGif)
    ImageView ivLoadingGif;
    Call<Cards> call;
    FlirtjarCard.SwipeEventListener swipeEventListener;
    FragmentJarCallbacks fragmentJarCallbacks;
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    private Cards cards;

    public static FragmentJar newInstance(FlirtjarCard.SwipeEventListener swipeEventListener,
                                          FragmentJarCallbacks fragmentJarCallbacks)
    {
        FragmentJar fragmentJar = new FragmentJar();
        fragmentJar.swipeEventListener = swipeEventListener;
        fragmentJar.fragmentJarCallbacks = fragmentJarCallbacks;
        return fragmentJar;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_jar, container, false);

        ButterKnife.bind(this, view);

        mSwipeView = (SwipePlaceHolderView) view.findViewById(R.id.swipeview);
        mContext = getContext();

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(ivLoadingGif);
        Glide.with(this).load(R.raw.loading_cards).into(imageViewTarget);

        /*view.findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });*/

        /*view.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });*/

        setupSwipeView();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        getFlirtCards();

    }

    public void getFlirtCards()
    {
        final String token = SharedPreferences.getFlirtjarUserToken(getActivity());
        if (token == null)
        {
            return;
        }
        if (call != null)
        {
            call.cancel();
        }
        final RetrofitCallback<Cards> onGetCards = new RetrofitCallback<Cards>(getActivity())
        {
            @Override
            public void onResponse(Call<Cards> call, final Response<Cards> response)
            {
                super.onResponse(call, response);
                if (response.isSuccessful())
                {
                    if (response.body().getResult() != null)
                    {
                        if (response.body().getResult().size() > 0)
                        {
                            if (cards == null)
                            {
                                cards = response.body();
                            } else
                            {
                                cards.getResult().addAll(response.body().getResult());
                            }
                            for (Cards.ResultBean singleCard : cards.getResult())
                            {
                                mSwipeView.addView(new FlirtjarCard(getActivity(),
                                        singleCard, mSwipeView, swipeEventListener));
                            }

                        }
                    }
                }
            }
        };

        call = API.Profile.getCards(1, token, onGetCards);

    }

    public void setupSwipeView()
    {
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setIsUndoEnabled(true)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setSwipeRotationAngle(5)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.flirtjar_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.flirtjar_swipe_out_msg_view));
    }

    @OnClick(R.id.ibtn_like)
    public void like()
    {
        cardWasDisliked = false;
        cardWasSuperliked = false;
        mSwipeView.doSwipe(true);
    }

    @OnClick(R.id.ibtn_dislike)
    public void dislike()
    {
        cardWasDisliked = true;
        cardWasSuperliked = false;
        mSwipeView.doSwipe(false);
    }

    @OnClick(R.id.ib_return)
    public void putBack()
    {
        if (cardWasDisliked)
        {
            mSwipeView.undoLastSwipe();
        }
    }

    @OnClick(R.id.ibtn_super_like)
    public void superLike()
    {
        cardWasDisliked = false;
        cardWasSuperliked = true;
        Toast.makeText(mContext, "Superliked", Toast.LENGTH_SHORT).show();
        mSwipeView.doSwipe(true);
    }

    @OnClick(R.id.ibtn_gift)
    public void sendGift()
    {
        final DialogSendGifts sendGifts = new DialogSendGifts(getActivity());
        sendGifts.show();
    }

    @Override
    public void onDestroy()
    {
        if (call != null)
        {
            call.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView()
    {
        fragmentJarCallbacks.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onDetach()
    {
        fragmentJarCallbacks.onDetach();
        super.onDetach();
    }

    public interface FragmentJarCallbacks
    {
        void onDestroyView();

        void onDetach();
    }

}
