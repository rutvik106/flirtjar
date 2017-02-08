package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.flirtjar.R;

import butterknife.ButterKnife;

/**
 * Created by rutvik on 2/5/2017 at 4:13 PM.
 */

public class FragmentChat extends Fragment {

    public static FragmentChat newInstance() {
        return new FragmentChat();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        ButterKnife.bind(this, view);


        return view;
    }
}
