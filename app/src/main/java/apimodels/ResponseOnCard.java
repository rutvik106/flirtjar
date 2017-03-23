package apimodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rutvik on 3/23/2017 at 12:21 AM.
 */

public class ResponseOnCard
{

    public static final int LIKE = 0;
    public static final int SKIPPED = 1;
    public static final int SUPERLIKE = 2;

    /**
     * user_from : 18
     * user_to : 8
     * response : 1
     */

    @SerializedName("user_from")
    private int userFrom;
    @SerializedName("user_to")
    private int userTo;
    @SerializedName("response")
    private int response;

    public int getUserFrom()
    {
        return userFrom;
    }

    public void setUserFrom(int userFrom)
    {
        this.userFrom = userFrom;
    }

    public int getUserTo()
    {
        return userTo;
    }

    public void setUserTo(int userTo)
    {
        this.userTo = userTo;
    }

    public int getResponse()
    {
        return response;
    }

    public void setResponse(int response)
    {
        this.response = response;
    }
}
