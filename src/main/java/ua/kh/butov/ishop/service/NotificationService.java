package ua.kh.butov.ishop.service;

import ua.kh.butov.ishop.entity.Order;

public interface NotificationService {
	void sendNewOrderCreatedNotification(String notificationAddress, Order order);
}
