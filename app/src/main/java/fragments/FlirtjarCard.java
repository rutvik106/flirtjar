package fragments;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.flirtjar.ActivityProfileView;
import com.app.flirtjar.R;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import apimodels.Cards;

/**
 * Created by Admin on 2/7/2017.
 */


@Layout(R.layout.flirtjar_card_view)
public class FlirtjarCard
{

    final private Cards.ResultBean singleCardUser;
    final private FlirtCardEventListener flirtCardEventListener;
    @View(R.id.iv_cardUserImage)
    private ImageView ivCardUserImage;
    @View(R.id.tv_cardUserNameAndAge)
    private TextView tvCardUserNameAndAge;
    @View(R.id.tv_cardUserFrom)
    private TextView tvCardUserFrom;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    private CardResolveListener cardResolveListener;

    public FlirtjarCard(Context context,
                        Cards.ResultBean singleCardUser,
                        SwipePlaceHolderView swipeView,
                        FlirtCardEventListener flirtCardEventListener,
                        CardResolveListener cardResolveListener)
    {
        mContext = context;
        this.singleCardUser = singleCardUser;
        mSwipeView = swipeView;
        this.flirtCardEventListener = flirtCardEventListener;
        this.cardResolveListener = cardResolveListener;
    }

    @Resolve
    public void onResolved()
    {
        //Toast.makeText(mContext, "onResolved()", Toast.LENGTH_SHORT).show();
        Glide.with(mContext).load(singleCardUser.getProfilePicture()).into(ivCardUserImage);
        tvCardUserNameAndAge.setText(singleCardUser.getFirstName() + ", " + singleCardUser.getAge());
        tvCardUserFrom.setText(singleCardUser.getCountry());
        cardResolveListener.onResolve(singleCardUser);
    }


    @Click(R.id.iv_cardUserImage)
    private void onClick()
    {
        ActivityProfileView.start(mContext, false, singleCardUser.getId());
    }

    @SwipeOut
    private void onSwipedOut()
    {
        Log.d("EVENT", "onSwipedOut");
        flirtCardEventListener.onSwipeOut(this);
    }

    @SwipeIn
    private void onSwipeIn()
    {
        Log.d("EVENT", "onSwipedIn");
        flirtCardEventListener.onSwipeIn(this);
    }

    @SwipeInState
    private void onSwipeInState()
    {
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState()
    {
        Log.d("EVENT", "onSwipeOutState");
    }

    @SwipeCancelState
    private void onSwipeCancelState()
    {
        Log.d("EVENT", "onSwipeCancelState");
    }

    public Cards.ResultBean getSingleCardUser()
    {
        return singleCardUser;
    }

    public interface FlirtCardEventListener
    {
        void onSwipeIn(FlirtjarCard card);

        void onSwipeOut(FlirtjarCard card);
    }

    public interface CardResolveListener
    {
        void onResolve(Cards.ResultBean card);
    }

}