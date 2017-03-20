package instagram.apimodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rutvik on 3/20/2017 at 11:48 AM.
 */

public class AuthorizedUser
{


    /**
     * user : {"username":"flirtjar","id":"4576663497","website":"http://www.flirtjar.com/","profile_picture":"https://scontent.cdninstagram.com/t51.2885-19/s150x150/16228674_432778277064232_2963228968372666368_a.jpg","full_name":"Flirtjar","bio":"Download Flirtjar for free on iPhone and Android."}
     * access_token : 4576663497.b021650.ca0eebea50d54c6fb2a743073f77a34d
     */

    @SerializedName("user")
    private UserBean user;
    @SerializedName("access_token")
    private String accessToken;

    public UserBean getUser()
    {
        return user;
    }

    public void setUser(UserBean user)
    {
        this.user = user;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public static class UserBean
    {
        /**
         * username : flirtjar
         * id : 4576663497
         * website : http://www.flirtjar.com/
         * profile_picture : https://scontent.cdninstagram.com/t51.2885-19/s150x150/16228674_432778277064232_2963228968372666368_a.jpg
         * full_name : Flirtjar
         * bio : Download Flirtjar for free on iPhone and Android.
         */

        @SerializedName("username")
        private String username;
        @SerializedName("id")
        private String id;
        @SerializedName("website")
        private String website;
        @SerializedName("profile_picture")
        private String profilePicture;
        @SerializedName("full_name")
        private String fullName;
        @SerializedName("bio")
        private String bio;

        public String getUsername()
        {
            return username;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getWebsite()
        {
            return website;
        }

        public void setWebsite(String website)
        {
            this.website = website;
        }

        public String getProfilePicture()
        {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture)
        {
            this.profilePicture = profilePicture;
        }

        public String getFullName()
        {
            return fullName;
        }

        public void setFullName(String fullName)
        {
            this.fullName = fullName;
        }

        public String getBio()
        {
            return bio;
        }

        public void setBio(String bio)
        {
            this.bio = bio;
        }
    }
}
