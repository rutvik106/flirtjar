package utils;

import android.content.Context;
import android.preference.PreferenceManager;

import api.ApiInterface;

/**
 * Created by rutvik on 2/22/2017 at 9:16 AM.
 */

public class SharedPreferences
{

    public static String getFlirtjarUserToken(Context context)
    {
        final String token = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.FLIRTJAR_USER_TOKEN, null);
        if (token == null)
        {
            return null;
        } else
        {
            return "Token " + token;
        }
    }

    public static void setFacebookUserToken(Context context, String token)
    {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(Constants.FACEBOOK_USER_TOKEN, token)
                .apply();
    }

    public static String getFacebookUserToken(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.FACEBOOK_USER_TOKEN, null);
    }

    public static String getFcmInstanceId(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.FCM_DEVICE_TOKEN, null);
    }

    public static void setFlirtjarUserToken(Context context, String token)
    {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(Constants.FLIRTJAR_USER_TOKEN, token)
                .apply();
    }

    public static void setInstaAccessToken(Context context, String token)
    {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(Constants.INSTA_ACCESS_TOKEN, token)
                .apply();
    }

    public static String getInstaAccessToken(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constants.INSTA_ACCESS_TOKEN, null);
    }

    public static class Settings
    {
        public static int getDistanceSettings(Context context)
        {
            return PreferenceManager.getDefaultSharedPreferences(context)
                    .getInt(Constants.Settings.DISTANCE, 100);
        }


        public static ApiInterface.Location.LocationUnit getLocationUnit(Context context)
        {
            final int locationUnit =
                    PreferenceManager.getDefaultSharedPreferences(context)
                            .getInt(Constants.Settings.LOCATION_UNIT, 0);
            if (locationUnit == 0)
            {
                return ApiInterface.Location.LocationUnit.kilometer;
            } else
            {
                return ApiInterface.Location.LocationUnit.meter;
            }
        }
    }
}
