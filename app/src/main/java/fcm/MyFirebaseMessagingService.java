package fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.flirtjar.ActivityNavDrawer;
import com.app.flirtjar.App;
import com.app.flirtjar.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import utils.Constants;

/**
 * Created by rutvik on 12/30/2016 at 3:13 PM.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = App.APP_TAG + MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage message)
    {
        String from = message.getFrom();
        Map data = message.getData();

        Log.i(TAG, "DATA: " + data.toString() + " FROM: " + from);
        if (message.getNotification() != null)
        {
            sendNotification(message.getNotification().getBody());
        }

        /*new NotificationHandler(this, message)
                .handleNotification();*/

    }

    private void sendNotification(String messageBody)
    {
        Intent intent = new Intent(this, ActivityNavDrawer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_jar_active)
                .setContentTitle("Flirtjar")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        sendBroadcast(new Intent(Constants.NEW_NOTIFICATION_RECEIVED));
    }


}
