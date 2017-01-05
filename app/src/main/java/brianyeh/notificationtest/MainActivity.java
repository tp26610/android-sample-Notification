package brianyeh.notificationtest;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_ID = 100;

    private CheckBox cbHeadUp, cbDismiss, cbOngoing, cbAutoCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbHeadUp = (CheckBox) findViewById(R.id.cbHeadUp);
        cbDismiss = (CheckBox) findViewById(R.id.cbDismiss);
        cbOngoing = (CheckBox) findViewById(R.id.cbOngoing);
        cbAutoCancel = (CheckBox) findViewById(R.id.cbAutoCancel);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onButtonShow(View v) {
        showNotification();
    }

    public void onButtonCancel(View v) {
        cancelNotification();
    }

    public void onButtonShowDelayedThreeSeconds(View v) {
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                showNotification();
            }
        };
        getWindow().getDecorView().postDelayed(r, 3000);
    }

    private void showNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("this is content title")
                .setContentText("this is content text.");

        addNotificationOptions(builder);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    private void cancelNotification() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.cancel(NOTIFICATION_ID);
    }

    private PendingIntent createNullPendingIntent() {
        return PendingIntent.getActivity(this, 0, new Intent(), 0);
    }

    public void addNotificationOptions(NotificationCompat.Builder builder) {
        if(cbHeadUp.isChecked()) {
            builder.setFullScreenIntent(createNullPendingIntent(), true);
        }
        if(cbOngoing.isChecked()) {
            builder.setOngoing(true);
        }
        if(cbDismiss.isChecked()) {
            Intent serviceIntent = new Intent(this, NotificationDismissService.class);
            PendingIntent pIntent = PendingIntent.getService(this, 0, serviceIntent, 0);
            builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "Dismiss", pIntent);
        }
        if(cbAutoCancel.isChecked()) {
            builder.setAutoCancel(true);

            // If the ContentIntent is not set, the AutoCancel won't work.
            builder.setContentIntent(createNullPendingIntent());
        }
    }
}
