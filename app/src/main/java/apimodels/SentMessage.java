package apimodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rutvik on 3/15/2017 at 4:21 PM.
 */

public class SentMessage
{

    /**
     * errors : {}
     * result : {"id":27,"message_text":"string","sent_at":"2017-03-15T10:50:11.249484Z","user_from":2,"user_to":1}
     */

    @SerializedName("errors")
    private ErrorsBean errors;
    @SerializedName("result")
    private ResultBean result;

    public ErrorsBean getErrors()
    {
        return errors;
    }

    public void setErrors(ErrorsBean errors)
    {
        this.errors = errors;
    }

    public ResultBean getResult()
    {
        return result;
    }

    public void setResult(ResultBean result)
    {
        this.result = result;
    }

    public static class ErrorsBean
    {
    }

    public static class ResultBean
    {
        /**
         * id : 27
         * message_text : string
         * sent_at : 2017-03-15T10:50:11.249484Z
         * user_from : 2
         * user_to : 1
         */

        @SerializedName("id")
        private int id;
        @SerializedName("message_text")
        private String messageText;
        @SerializedName("sent_at")
        private String sentAt;
        @SerializedName("user_from")
        private int userFrom;
        @SerializedName("user_to")
        private int userTo;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getMessageText()
        {
            return messageText;
        }

        public void setMessageText(String messageText)
        {
            this.messageText = messageText;
        }

        public String getSentAt()
        {
            return sentAt;
        }

        public void setSentAt(String sentAt)
        {
            this.sentAt = sentAt;
        }

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
    }
}
