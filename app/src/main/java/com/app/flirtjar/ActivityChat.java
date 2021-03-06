package com.app.flirtjar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.HttpURLConnection;

import adapters.ChatMessagesAdapter;
import api.API;
import api.RetrofitCallback;
import apimodels.ChatMessages;
import apimodels.SendChatMessage;
import apimodels.SentMessage;
import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Response;
import utils.Constants;
import utils.SharedPreferences;

public class ActivityChat extends BaseActivity
{

    String chatContactName, chatContactProfilePicture;
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

    @BindView(R.id.iv_chatUserProfilePicture)
    ImageView ivChatUserProfilePicture;

    ChatMessagesAdapter adapter;

    Call<SentMessage> call;
    @BindView(R.id.ll_chatUserDetailsHeader)
    LinearLayout llChatUserDetailsHeader;

    public static void start(final Context context, final int userId, final String chatContactName,
                             final String profilePicture)
    {
        final Intent i = new Intent(context, ActivityChat.class);
        i.putExtra(Constants.CHAT_CONTACT_NAME, chatContactName);
        i.putExtra(Constants.USER_ID, userId);
        i.putExtra(Constants.USER_PROFILE_PICTURE, profilePicture);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        chatContactName = getIntent().getStringExtra(Constants.CHAT_CONTACT_NAME);
        chatContactId = getIntent().getIntExtra(Constants.USER_ID, 0);
        chatContactProfilePicture = getIntent().getStringExtra(Constants.USER_PROFILE_PICTURE);

        Glide.with(this)
                .load(chatContactProfilePicture)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(ivChatUserProfilePicture);

        tvNavLogoText.setText(chatContactName);

        llChatUserDetailsHeader.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ActivityProfileView.start(ActivityChat.this, false, chatContactId);
            }
        });

        rvChatConversation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        rvChatConversation.setHasFixedSize(true);
        adapter = new ChatMessagesAdapter(this);
        rvChatConversation.setAdapter(adapter);

        getChatMessages();

    }

    private void getChatMessages()
    {
        final RetrofitCallback<ChatMessages> onGetChatMessages =
                new RetrofitCallback<ChatMessages>(this)
                {
                    @Override
                    public void onResponse(Call<ChatMessages> call, Response<ChatMessages> response)
                    {
                        super.onResponse(call, response);
                        if (response.isSuccessful())
                        {
                            for (ChatMessages.ResultBean chatMessage : response.body().getResult())
                            {
                                final SentMessage.ResultBean msg = new SentMessage.ResultBean();
                                msg.setId(chatMessage.getId());
                                msg.setMessageText(chatMessage.getMessageText());
                                msg.setSentAt(chatMessage.getSentAt());
                                msg.setUserFrom(chatMessage.getUserFrom().getId());
                                msg.setUserTo(chatMessage.getUserTo().getId());
                                if (chatMessage.getUserFrom().getId() == App.getInstance().getUser()
                                        .getResult().getId())
                                {
                                    msg.setFirstName(App.getInstance().getUser().getResult().getFirstName());
                                } else
                                {
                                    msg.setFirstName(chatContactName);
                                }
                                if (chatMessage.getUserTo().getId() == App.getInstance().getUser()
                                        .getResult().getId())
                                {
                                    msg.setFirstName(App.getInstance().getUser().getResult().getFirstName());
                                } else
                                {
                                    msg.setFirstName(chatContactName);
                                }
                                adapter.addItem(msg);
                            }
                        } else
                        {
                            Toast.makeText(ActivityChat.this, "Failed to get chat messages.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChatMessages> call, Throwable t)
                    {
                        super.onFailure(call, t);
                        Toast.makeText(ActivityChat.this, "Failed to get chat messages.", Toast.LENGTH_SHORT).show();
                    }
                };

        API.Chat.getChatMessages(chatContactId, SharedPreferences.getFlirtjarUserToken(this),
                onGetChatMessages);
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
        } else if (item.getItemId() == R.id.action_deleteChat)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Delete chat conversations?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            Toast.makeText(ActivityChat.this, "Messages deleted successfully.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_chat_menu, menu);
        return true;
    }

}
