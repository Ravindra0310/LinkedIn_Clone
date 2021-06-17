package com.example.jobedin.RecyclerViewComponant.NotificationRecyclerView;

public class NotificationModel {
    private int imageId;
    private String notificationDescription;
    private String notificationTiming;

    public NotificationModel(int imageId, String notificationDescription, String notificationTiming) {
        this.imageId = imageId;
        this.notificationDescription = notificationDescription;
        this.notificationTiming = notificationTiming;
    }

    public int getImageId() {
        return imageId;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public String getNotificationTiming() {
        return notificationTiming;
    }
}
