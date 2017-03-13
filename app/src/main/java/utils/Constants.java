package utils;

/**
 * Created by rutvik on 2/21/2017 at 11:21 PM.
 */

public class Constants
{
    public static final String BASE_URL = "http://139.59.44.13/api/";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String AUTHORIZATION = "Authorization";

    public static final String FLIRTJAR_USER_TOKEN = "FLIRTJAR_USER_TOKEN";

    public static final String FACEBOOK_USER_TOKEN = "FACEBOOK_USER_TOKEN";

    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String FCM_DEVICE_TOKEN = "FCM_DEVICE_TOKEN";

    public static final String DEVICE_TYPE_ANDROID = "android";
    public static final String IS_VIEWING_SELF_PROFILE = "IS_VIEWING_SELF_PROFILE";
    public static final String USER_ID = "USER_ID";

    public static enum Gender
    {
        MALE("M"), FEMALE("F"), UNSPECIFIED("U");

        final String value;

        Gender(final String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    public class Settings
    {
        public static final String DISTANCE = "DISTANCE";
        public static final String LOCATION_UNIT = "LOCATION_UNIT";
    }
}
