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

    public int getCoins()
    {
        return coins;
    }

    public void setCoins(int coins)
    {
        this.coins = coins;
    }
}
