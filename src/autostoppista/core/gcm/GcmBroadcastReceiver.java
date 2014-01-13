package autostoppista.core.gcm;

import java.util.Random;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import autostoppista.app.MainActivity;
import autostoppista.app.R;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
   
    public static MainActivity main;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    @Override
    public void onReceive(Context context, Intent intent) {
    	if (main!=null){
	    	main.HandleGCMMessage(intent.getExtras());
	    }
    	sendNotification("Received: " + intent.getExtras().getString("Type"), context);
    }
    private void sendNotification(String msg, Context context) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, GCM.class), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.common_signin_btn_icon_dark)           
        .setContentTitle("GCM Notification")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setContentText(msg);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify((new Random()).nextInt(), mBuilder.build());
    }
}
