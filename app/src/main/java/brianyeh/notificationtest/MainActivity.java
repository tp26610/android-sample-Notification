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

    private CheckBox cbHeadUp, cbDismiss, cbOngoing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbHeadUp = (CheckBox) findViewById(R.id.cbHeadUp);
        cbDismiss = (CheckBox) findViewById(R.id.cbDismiss);
        cbOngoing = (CheckBox) findViewById(R.id.cbOngoing);
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
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
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

    public void addNotificationOptions(NotificationCompat.Builder builder) {
        if(cbHeadUp.isChecked()) {
            PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, new Intent(), 0);
            builder.setFullScreenIntent(pIntent, true);
        }
        if(cbOngoing.isChecked()) {
            builder.setOngoing(true);
        }
        if(cbDismiss.isChecked()) {
            Intent serviceIntent = new Intent(this, NotificationDismissService.class);
            PendingIntent pIntent = PendingIntent.getService(this, 0, serviceIntent, 0);
            builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "Dismiss", pIntent);
        }
    }
}
