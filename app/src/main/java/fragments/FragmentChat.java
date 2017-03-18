package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.flirtjar.R;

import adapters.ChatListAdapter;
import api.API;
import api.RetrofitCallback;
import apimodels.MatchedProfiles;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
import utils.SharedPreferences;

/**
 * Created by rutvik on 2/5/2017 at 4:13 PM.
 */

public class FragmentChat extends Fragment
{

    @BindView(R.id.rv_chatList)
    RecyclerView rvChatList;

    @BindView(R.id.sv_searchChat)
    SearchView svSearchChat;

    ChatListAdapter adapter;

    Call<MatchedProfiles> call;

    public static FragmentChat newInstance()
    {
        return new FragmentChat();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        ButterKnife.bind(this, view);

        rvChatList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvChatList.setHasFixedSize(true);

        adapter = new ChatListAdapter(getActivity());

        rvChatList.setAdapter(adapter);

        getChatList();

        return view;
    }

    private void getChatList()
    {
        final RetrofitCallback<MatchedProfiles> onGetChatList =
                new RetrofitCallback<MatchedProfiles>(getContext())
                {
                    @Override
                    public void onResponse(Call<MatchedProfiles> call, Response<MatchedProfiles> response)
                    {
                        super.onResponse(call, response);
                        if (response.isSuccessful())
                        {
                            for (MatchedProfiles.ResultBean user : response.body().getResult())
                            {
                                adapter.addChatUser(user);
                            }
                        }
                    }
                };

        call = API.Profile.getMatchedProfiles(1, SharedPreferences.getFlirtjarUserToken(getContext()),
                onGetChatList);
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
    public void onDetach()
    {
        if (call != null)
        {
            call.cancel();
        }
        super.onDetach();
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
}
