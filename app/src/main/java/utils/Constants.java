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
    public static final String CHAT_CONTACT_NAME = "CHAT_CONTACT_NAME";
    public static final String INSTA_ACCESS_TOKEN = "INSTA_ACCESS_TOKEN";
    public static final String USER_PROFILE_PICTURE = "USER_PROFILE_PICTURE";
    public static final String NEW_NOTIFICATION_RECEIVED = "NEW_NOTIFICATION_RECEIVED";

    public enum Gender
    {
        MALE("M", "Male"), FEMALE("F", "Female"), UNSPECIFIED("U", "Unspecified"), NONE("", "Not Specified");

        final String value;
        final String label;

        Gender(final String value, final String label)
        {
            this.value = value;
            this.label = label;
        }

        public String getValue()
        {
            return value;
        }

        public String getLabel()
        {
            return label;
        }
    }

    public enum Status
    {
        WALK("walk", "Walk"), COFFEE("coffee", "Coffee"), DRINK("drink", "Drink"),
        LONG_DRIVE("long_drive", "Long Drive"), LUNCH("lunch", "Lunch"), DINNER("dinner", "Dinner"),
        DETOUR("detour", "Detour"), MOVIE("movie", "Movie"), NONE("", "Status Not Specified");

        final String value;
        final String label;

        Status(final String value, final String label)
        {
            this.value = value;
            this.label = label;
        }

        public String getValue()
        {
            return value;
        }

        public String getLabel()
        {
            return label;
        }
    }


    public enum EyeColor
    {
        BLACK("bl", "Black"), BROWN("br", "Brown"), BLUE("bu", "Blue"), NONE("", "Not Specified");
        final String value;
        final String label;

        EyeColor(final String value, final String label)
        {
            this.value = value;
            this.label = label;
        }

        public String getLabel()
        {
            return label;
        }

        public String getValue()
        {
            return value;
        }
    }

    public enum HairColor
    {
        BLACK("bl", "Black"), BROWN("br", "Brown"), BLUE("bu", "Blue"), NONE("", "Not Specified");
        final String value;
        final String label;

        HairColor(final String value, final String label)
        {
            this.value = value;
            this.label = label;
        }

        public String getLabel()
        {
            return label;
        }

        public String getValue()
        {
            return value;
        }
    }

    public enum Aquarius
    {
        Aries("ar", "Aries"), Taurus("ta", "Taurus"), Gemini("ge", "Gemini"),
        Cancer("ca", "Cancer"), Leo("le", "Leo"), Virgo("vi", "Virgo"), Libra("li", "Libra"),
        Scorpio("sc", "Scorpio"), Sagittarius("sa", "Sagittarius"), Capricorn("cp", "Capricorn"),
        Aquarius("aq", "Aquarius"), Pisces("pi", "Pisces"), NONE("", "Not Specified");

        final String value;
        final String label;

        Aquarius(final String value, final String label)
        {
            this.value = value;
            this.label = label;
        }

        public String getValue()
        {
            return value;
        }

        public String getLabel()
        {
            return label;
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


    public class NotificationType
    {
        public static final String FJ_TEAM = "fj_team";
        public static final String VIEW = "view";
        public static final String COINS = "coins";
        public static final String CRUSH_OR_SUPERLIKE = "crush";
        public static final String LIKE = "like";
        public static final String USER_MATCH = "match";
        public static final String GIFT = "gift";
    }
}
