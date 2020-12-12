package com.sj.todolist.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;

import com.sj.todolist.R;
import com.sj.todolist.activities.TodoDetailsActivity;
import com.sj.todolist.services.TodoDoneIntentService;
import com.sj.todolist.utils.Constants;
import com.sj.todolist.utils.NotificationHelper;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);

        long id = intent.getLongExtra("id", 0);
        String title = "Todo Pending...";
        String body = intent.getStringExtra("body");
        Intent notificationIntent = new Intent(context, TodoDetailsActivity.class);
        notificationIntent.putExtra(Constants.TODO_ID, id);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(context, (int) id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent notificationActionIntent = new Intent(context, TodoDoneIntentService.class);
        notificationActionIntent.putExtra("id", id);
        PendingIntent notificationActionPendingIntent = PendingIntent.getService(context, (int) id, notificationActionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Action action = new Notification.Action.Builder(Icon.createWithResource(context, R.mipmap.ic_done_white_24dp),
                "Done!",
                notificationActionPendingIntent).build();

        notificationHelper.notify((int) id, title, body, notificationPendingIntent, action);
    }
}