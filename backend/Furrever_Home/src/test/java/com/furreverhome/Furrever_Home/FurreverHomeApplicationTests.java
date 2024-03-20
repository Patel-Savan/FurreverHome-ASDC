package com.furreverhome.Furrever_Home;

import com.furreverhome.Furrever_Home.services.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FurreverHomeApplicationTests {


	@Autowired
	private NotificationService notificationService;

	@Test
	void contextLoads() {
	}

}
