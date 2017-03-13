package apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rutvik on 3/13/2017 at 10:25 AM.
 */

public class NotificationList
{


    /**
     * errors : {}
     * result : [{"id":46,"notification_text":"Deepak : 12345six2 sent you a gift.","timestamp":"2017-02-26T17:48:51.931475Z","notification_type":"gift","notification_icon":"fjhf"}]
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

    public static class ResultBean
    {
        /**
         * id : 46
         * notification_text : Deepak : 12345six2 sent you a gift.
         * timestamp : 2017-02-26T17:48:51.931475Z
         * notification_type : gift
         * notification_icon : fjhf
         */

        @SerializedName("id")
        private int id;
        @SerializedName("notification_text")
        private String notificationText;
        @SerializedName("timestamp")
        private String timestamp;
        @SerializedName("notification_type")
        private String notificationType;
        @SerializedName("notification_icon")
        private String notificationIcon;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getNotificationText()
        {
            return notificationText;
        }

        public void setNotificationText(String notificationText)
        {
            this.notificationText = notificationText;
        }

        public String getTimestamp()
        {
            return timestamp;
        }

        public void setTimestamp(String timestamp)
        {
            this.timestamp = timestamp;
        }

        public String getNotificationType()
        {
            return notificationType;
        }

        public void setNotificationType(String notificationType)
        {
            this.notificationType = notificationType;
        }

        public String getNotificationIcon()
        {
            return notificationIcon;
        }

        public void setNotificationIcon(String notificationIcon)
        {
            this.notificationIcon = notificationIcon;
        }
    }
}
