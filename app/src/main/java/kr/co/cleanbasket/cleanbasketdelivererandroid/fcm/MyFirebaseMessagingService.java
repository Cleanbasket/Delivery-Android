package kr.co.cleanbasket.cleanbasketdelivererandroid.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.activity.StartActivity;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 6. 20..
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.pdapp_logo)
                        .setContentTitle(remoteMessage.getFrom())
                        .setContentText(remoteMessage.getNotification().getBody());

        Intent resultIntent = new Intent(this, StartActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(StartActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());


        Log.d("", "From: " + remoteMessage.getFrom());
        Log.d("", "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }


}
