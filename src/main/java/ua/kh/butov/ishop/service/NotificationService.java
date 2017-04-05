package ua.kh.butov.ishop.service;

public interface NotificationService {
	void sendNotificationMessage(String notificationAddress, String content);
}
