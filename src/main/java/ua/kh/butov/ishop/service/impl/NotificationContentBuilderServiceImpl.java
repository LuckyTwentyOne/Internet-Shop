package ua.kh.butov.ishop.service.impl;

import ua.kh.butov.framework.annotation.Component;
import ua.kh.butov.framework.annotation.Value;
import ua.kh.butov.ishop.entity.Order;
import ua.kh.butov.ishop.service.NotificationContentBuilderService;

@Component
public class NotificationContentBuilderServiceImpl implements NotificationContentBuilderService {

	@Value("app.host")
	private String host;

	@Override
	public String buildNewOrderCreatedNotificationMessage(Order order) {
		return host + "/order?id=" + order.getId();
	}

}
