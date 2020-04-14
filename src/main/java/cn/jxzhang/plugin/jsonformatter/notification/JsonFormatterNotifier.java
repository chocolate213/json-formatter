package cn.jxzhang.plugin.jsonformatter.notification;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;

/**
 * JsonFormatterNotifier
 *
 * @author zhangjiaxing created on 2020-04-15
 */
public class JsonFormatterNotifier {

    private static final NotificationGroup NOTIFICATION_GROUP = new NotificationGroup("Json Formatter Information", NotificationDisplayType.BALLOON, false);

    private JsonFormatterNotifier() {
    }

    public static void notify(String content) {
        final Notification notification = NOTIFICATION_GROUP.createNotification("Json Formatter", content, NotificationType.INFORMATION, null);
        notification.notify(null);
    }
}