package ua.kh.butov.ishop.service;

import ua.kh.butov.ishop.entity.Order;

public interface NotificationContentBuilderService {
	 String buildNewOrderCreatedNotificationMessage(Order order);
}
