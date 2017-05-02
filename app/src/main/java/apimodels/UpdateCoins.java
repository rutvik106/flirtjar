package apimodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rutvik on 3/25/2017 at 2:04 PM.
 */

public class UpdateCoins
{

    /**
     * coins : 0
     */

    @SerializedName("coins")
    private int coins;
    /**
     * user : 1
     * operation : add
     */

    @SerializedName("user")
    private int user;
    @SerializedName("operation")
    private String operation;


    public int getCoins()
    {
        return coins;
    }

    public void setCoins(int coins)
    {
        this.coins = coins;
    }

    public int getUser()
    {
        return user;
    }

    public void setUser(int user)
    {
        this.user = user;
    }

    public String getOperation()
    {
        return operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }
}
