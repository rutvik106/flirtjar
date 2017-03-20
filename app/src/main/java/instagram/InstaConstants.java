package instagram;

/**
 * Created by rutvik on 3/20/2017 at 10:33 AM.
 */

public class InstaConstants
{

    public static final String INSTA_BASE_URL = "https://api.instagram.com/";

    public static final String AUTHURL = INSTA_BASE_URL + "oauth/authorize/";
    public static final String TOKENURL = "oauth/access_token";
    public static final String APIURL = INSTA_BASE_URL + "v1";
    public static final String CALLBACKURL = "http://www.flirtjar.com";

    public static final String GRANT_TYPE = "authorization_code";

    public static final String CLIENT_ID = "b021650e6e7248fca3d0e41eb1281738";

    public static final String authURLString =
            AUTHURL + "?client_id=" + CLIENT_ID + "&redirect_uri=" +
                    CALLBACKURL + "&response_type=code&display=touch&scope=likes+comments+relationships";

    public static final String CLIENT_SECRET = "7933810d80f64264bf606ce1bf3fec47";

    public static final String tokenURLString =
            TOKENURL;// + "?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET +
    //"&redirect_uri=" + CALLBACKURL + "&grant_type=authorization_code";

}
