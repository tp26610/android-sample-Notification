package brianyeh.notificationtest;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

public class NotificationDismissService extends IntentService {

    public NotificationDismissService() {
        super(NotificationDismissService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.cancel(MainActivity.NOTIFICATION_ID);
    }
}
