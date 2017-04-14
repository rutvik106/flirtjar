package com.app.flirtjar;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import api.API;
import api.RetrofitCallback;
import apimodels.NotificationDeviceDetails;
import apimodels.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import utils.Constants;
import utils.SharedPreferences;
import utils.TypeFaceUtil;

/**
 * Created by rutvik on 1/22/2017 at 7:25 PM.
 */

public class App extends Application
{
    public static final String APP_TAG = "FTJ ";

    private static App instance;

    private User user;

    public static App getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        TypeFaceUtil.setDefaultFont(this, "DEFAULT", "Pacifico-Regular.ttf");
        TypeFaceUtil.setDefaultFont(this, "MONOSPACE", "Montserrat-Regular.ttf");
        //TypeFaceUtil.setDefaultFont(this, "SERIF", "montserrat_bold.ttf");
        //TypeFaceUtil.setDefaultFont(this, "SANS_SERIF", "montserrat_bold.ttf");

        /**try
         {
         PackageInfo info = getPackageManager().getPackageInfo(
         "com.app.flirtjar",
         PackageManager.GET_SIGNATURES);
         for (Signature signature : info.signatures)
         {
         MessageDigest md = MessageDigest.getInstance("SHA");
         md.update(signature.toByteArray());
         Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
         }
         } catch (PackageManager.NameNotFoundException e)
         {

         } catch (NoSuchAlgorithmException e)
         {

         }*/

    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
        sendFcmInstanceIdToServer();
    }

    private void sendFcmInstanceIdToServer()
    {
        final String instanceId = SharedPreferences.getFcmInstanceId(this);
        final String token = SharedPreferences.getFlirtjarUserToken(this);
        if (instanceId != null && token != null)
        {
            final NotificationDeviceDetails deviceDetails =
                    new NotificationDeviceDetails();
            deviceDetails.setDeviceType(Constants.DEVICE_TYPE_ANDROID);
            deviceDetails.setRegistrationId(instanceId);

            API.Notifications.registerNotificationDevice(deviceDetails,
                    token, new RetrofitCallback<ResponseBody>(this)
                    {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                        {
                            super.onResponse(call, response);
                        }
                    });
        }

    }

}
