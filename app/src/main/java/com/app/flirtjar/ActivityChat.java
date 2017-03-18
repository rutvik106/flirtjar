package com.app.flirtjar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.net.HttpURLConnection;

import adapters.ChatMessagesAdapter;
import api.API;
import api.RetrofitCallback;
import apimodels.SendChatMessage;
import apimodels.SentMessage;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import utils.Constants;
import utils.SharedPreferences;

public class ActivityChat extends BaseActivity
{

    String chatContactName;
    int chatContactId;
    @BindView(R.id.et_chatMessageBody)
    EditText etChatMessageBody;
    @BindView(R.id.ib_chatMessageSend)
    ImageButton ibChatMessageSend;
    @BindView(R.id.ll_chatMessageBoxContainer)
    LinearLayout llChatMessageBoxContainer;
    @BindView(R.id.rv_chatConversation)
    RecyclerView rvChatConversation;
    @BindView(R.id.cl_activityEditProfile)
    CoordinatorLayout clActivityEditProfile;

    ChatMessagesAdapter adapter;

    Call<SentMessage> call;

    public static void start(final Context context, final int userId, final String chatContactName)
    {
        final Intent i = new Intent(context, ActivityChat.class);
        i.putExtra(Constants.CHAT_CONTACT_NAME, chatContactName);
        i.putExtra(Constants.USER_ID, userId);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        chatContactName = getIntent().getStringExtra(Constants.CHAT_CONTACT_NAME);
        chatContactId = getIntent().getIntExtra(Constants.USER_ID, 0);

        tvNavLogoText.setText(chatContactName);

        rvChatConversation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        rvChatConversation.setHasFixedSize(true);
        adapter = new ChatMessagesAdapter(this);
        rvChatConversation.setAdapter(adapter);

    }

    @Override
    protected int getLayoutResourceId()
    {
        return R.layout.activity_chat;
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.ib_chatMessageSend)
    public void onClick()
    {
        if (etChatMessageBody.getText().toString().trim().length() <= 0)
        {
            Toast.makeText(this, "Enter Message to Send.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (call != null)
        {
            call.cancel();
        }

        final SendChatMessage message = new SendChatMessage();
        message.setMessageText(etChatMessageBody.getText().toString());
        message.setUserFrom(App.getInstance().getUser().getResult().getId());
        message.setUserTo(chatContactId);

        final RetrofitCallback<SentMessage> onMessageSent = new RetrofitCallback<SentMessage>(this)
        {
            @Override
            public void onResponse(Call<SentMessage> call, Response<SentMessage> response)
            {
                super.onResponse(call, response);
                if (response.code() == HttpURLConnection.HTTP_CREATED || response.isSuccessful())
                {
                    etChatMessageBody.setText("");
                    adapter.addItem(response.body().getResult());
                    rvChatConversation.smoothScrollToPosition(0);
                }
            }
        };

        call = API.Chat.sendChatMessage(message,
                SharedPreferences.getFlirtjarUserToken(this), onMessageSent);

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
}
