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

    public enum Gender
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

    public enum Status
    {
        WALK("walk"), COFFEE("coffee"), DRINK("drink"),
        LONG_DRIVE("long_drive"), LUNCH("lunch"), DINNER("dinner"), DETOUR("detour"), MOVIE("movie");

        final String value;

        Status(final String value)
        {
            this.value = value;
        }
    }


    public enum EyeColor
    {
        BLACK("bl"), BROWN("br"), BLUE("bu");
        final String value;

        EyeColor(final String value)
        {
            this.value = value;
        }
    }

    public enum HairColor
    {
        BLACK("bl"), BROWN("br"), BLUE("bu");
        final String value;

        HairColor(final String value)
        {
            this.value = value;
        }
    }

    public enum Aquarius
    {
        Aries("ar"), Taurus("ta"), Gemini("ge"),
        Cancer("ca"), Leo("le"), Virgo("vi"), Libra("li"),
        Scorpio("sc"), Sagittarius("sa"), Capricorn("cp"),
        Aquarius("aq"), Pisces("pi");

        final String value;

        Aquarius(final String value)
        {
            this.value = value;
        }
    }

    public enum RelationshipStatus
    {
        Single("S"), Married("m");
        final String value;

        RelationshipStatus(final String value)
        {
            this.value = value;
        }
    }

    public class Settings
    {
        public static final String DISTANCE = "DISTANCE";
        public static final String LOCATION_UNIT = "LOCATION_UNIT";
    }


}
