package fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.flirtjar.R;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import butterknife.ButterKnife;

/**
 * Created by rutvik on 2/5/2017 at 4:13 PM.
 */

public class FragmentJar extends Fragment
{

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    public static FragmentJar newInstance()
    {
        return new FragmentJar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_jar, container, false);

        ButterKnife.bind(this, view);

        mSwipeView = (SwipePlaceHolderView) view.findViewById(R.id.swipeview);
        mContext = getContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.flirtjar_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.flirtjar_swipe_out_msg_view));


        for (Profile profile : Utils.loadProfiles(getActivity())) {
            mSwipeView.addView(new FlirtjarCard(getActivity(), profile, mSwipeView));
        }

        /*view.findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });
*/
        /*view.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });*/

        return view;
    }
}
