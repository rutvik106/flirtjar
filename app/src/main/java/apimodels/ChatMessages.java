package apimodels;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by rutvik on 3/22/2017 at 9:01 AM.
 */

public class ChatMessages
{


    /**
     * errors : {}
     * result : [{"id":29,"user_from":{"id":1,"first_name":"","last_name":"","profile_picture":""},"user_to":{"id":18,"first_name":"Rutvik","last_name":"Mehta","profile_picture":"https://graph.facebook.com/1467849723226234/picture?width=500&height=500"},"message_text":"string","sent_at":"2017-03-15T11:08:00.162141Z"},{"id":30,"user_from":{"id":1,"first_name":"","last_name":"","profile_picture":""},"user_to":{"id":18,"first_name":"Rutvik","last_name":"Mehta","profile_picture":"https://graph.facebook.com/1467849723226234/picture?width=500&height=500"},"message_text":"Hiiiii Rutvik","sent_at":"2017-03-18T14:04:17.983516Z"},{"id":28,"user_from":{"id":18,"first_name":"Rutvik","last_name":"Mehta","profile_picture":"https://graph.facebook.com/1467849723226234/picture?width=500&height=500"},"user_to":{"id":1,"first_name":"","last_name":"","profile_picture":""},"message_text":"string","sent_at":"2017-03-15T11:07:47.065601Z"},{"id":31,"user_from":{"id":18,"first_name":"Rutvik","last_name":"Mehta","profile_picture":"https://graph.facebook.com/1467849723226234/picture?width=500&height=500"},"user_to":{"id":1,"first_name":"","last_name":"","profile_picture":""},"message_text":"Hiiiii Rutvik","sent_at":"2017-03-18T14:05:48.344447Z"},{"id":32,"user_from":{"id":18,"first_name":"Rutvik","last_name":"Mehta","profile_picture":"https://graph.facebook.com/1467849723226234/picture?width=500&height=500"},"user_to":{"id":1,"first_name":"","last_name":"","profile_picture":""},"message_text":"Hiiiii Deepak","sent_at":"2017-03-18T14:07:14.552453Z"},{"id":33,"user_from":{"id":18,"first_name":"Rutvik","last_name":"Mehta","profile_picture":"https://graph.facebook.com/1467849723226234/picture?width=500&height=500"},"user_to":{"id":1,"first_name":"","last_name":"","profile_picture":""},"message_text":"Hiiiii Deepak","sent_at":"2017-03-18T15:47:33.816188Z"},{"id":34,"user_from":{"id":18,"first_name":"Rutvik","last_name":"Mehta","profile_picture":"https://graph.facebook.com/1467849723226234/picture?width=500&height=500"},"user_to":{"id":1,"first_name":"","last_name":"","profile_picture":""},"message_text":"Hiiiii Deepak","sent_at":"2017-03-18T15:47:59.280034Z"}]
     */

    @SerializedName("errors")
    private ErrorsBean errors;
    @SerializedName("result")
    private List<ResultBean> result;

    public ErrorsBean getErrors()
    {
        return errors;
    }

    public void setErrors(ErrorsBean errors)
    {
        this.errors = errors;
    }

    public List<ResultBean> getResult()
    {
        return result;
    }

    public void setResult(List<ResultBean> result)
    {
        this.result = result;
    }

    public static class ErrorsBean
    {
    }

    public static class ResultBean implements Comparable<ResultBean>
    {
        /**
         * id : 29
         * user_from : {"id":1,"first_name":"","last_name":"","profile_picture":""}
         * user_to : {"id":18,"first_name":"Rutvik","last_name":"Mehta","profile_picture":"https://graph.facebook.com/1467849723226234/picture?width=500&height=500"}
         * message_text : string
         * sent_at : 2017-03-15T11:08:00.162141Z
         * setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'") in retrofit
         */

        @SerializedName("id")
        private int id;
        @SerializedName("user_from")
        private UserFromBean userFrom;
        @SerializedName("user_to")
        private UserToBean userTo;
        @SerializedName("message_text")
        private String messageText;
        @SerializedName("sent_at")
        private Date sentAt;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public UserFromBean getUserFrom()
        {
            return userFrom;
        }

        public void setUserFrom(UserFromBean userFrom)
        {
            this.userFrom = userFrom;
        }

        public UserToBean getUserTo()
        {
            return userTo;
        }

        public void setUserTo(UserToBean userTo)
        {
            this.userTo = userTo;
        }

        public String getMessageText()
        {
            return messageText;
        }

        public void setMessageText(String messageText)
        {
            this.messageText = messageText;
        }

        public Date getSentAt()
        {
            return sentAt;
        }

        public void setSentAt(Date sentAt)
        {
            this.sentAt = sentAt;
        }

        @Override
        public int compareTo(@NonNull ResultBean resultBean)
        {
            return sentAt.compareTo(resultBean.sentAt);
        }

        public static class UserFromBean
        {
            /**
             * id : 1
             * first_name :
             * last_name :
             * profile_picture :
             */

            @SerializedName("id")
            private int id;
            @SerializedName("first_name")
            private String firstName;
            @SerializedName("last_name")
            private String lastName;
            @SerializedName("profile_picture")
            private String profilePicture;

            public int getId()
            {
                return id;
            }

            public void setId(int id)
            {
                this.id = id;
            }

            public String getFirstName()
            {
                return firstName;
            }

            public void setFirstName(String firstName)
            {
                this.firstName = firstName;
            }

            public String getLastName()
            {
                return lastName;
            }

            public void setLastName(String lastName)
            {
                this.lastName = lastName;
            }

            public String getProfilePicture()
            {
                return profilePicture;
            }

            public void setProfilePicture(String profilePicture)
            {
                this.profilePicture = profilePicture;
            }
        }

        public static class UserToBean
        {
            /**
             * id : 18
             * first_name : Rutvik
             * last_name : Mehta
             * profile_picture : https://graph.facebook.com/1467849723226234/picture?width=500&height=500
             */

            @SerializedName("id")
            private int id;
            @SerializedName("first_name")
            private String firstName;
            @SerializedName("last_name")
            private String lastName;
            @SerializedName("profile_picture")
            private String profilePicture;

            public int getId()
            {
                return id;
            }

            public void setId(int id)
            {
                this.id = id;
            }

            public String getFirstName()
            {
                return firstName;
            }

            public void setFirstName(String firstName)
            {
                this.firstName = firstName;
            }

            public String getLastName()
            {
                return lastName;
            }

            public void setLastName(String lastName)
            {
                this.lastName = lastName;
            }

            public String getProfilePicture()
            {
                return profilePicture;
            }

            public void setProfilePicture(String profilePicture)
            {
                this.profilePicture = profilePicture;
            }
        }
    }
}
