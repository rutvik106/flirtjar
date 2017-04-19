package fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.flirtjar.ActivityHome;
import com.app.flirtjar.App;
import com.app.flirtjar.R;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by rutvik on 2/22/2017 at 5:55 PM.
 */

public class NotificationHandler
{

    private static final String TAG = App.APP_TAG + NotificationHandler.class.getSimpleName();

    Context context;

    RemoteMessage remoteMessage;

    JSONObject data;

    JSONObject extra;

    public NotificationHandler(Context context, RemoteMessage remoteMessage)
    {
        this.context = context;
        this.remoteMessage = remoteMessage;
        try
        {
            if (remoteMessage != null)
            {
                if (remoteMessage.getData() != null)
                {
                    if (remoteMessage.getData().get("data") != null)
                    {
                        this.data = new JSONObject(remoteMessage.getData().get("data"));
                        this.extra = new JSONObject(remoteMessage.getData().get("extra"));
                    }
                }
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void handleNotification()
    {

        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d(TAG, "key, " + key + " value " + value);
        }

        try
        {
            switch (data.get("type").toString())
            {
                //create different cases for different notification here
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    private void showNotification(final int id, final String title, final String message)
    {
        Intent intent = new Intent(context, ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id /* ID of notification */, notificationBuilder.build());
    }

    private void showBigNotification(final int id, final String title, final String message)
    {
        Intent intent = new Intent(context, ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id /* ID of notification */, notificationBuilder.build());
    }

}
