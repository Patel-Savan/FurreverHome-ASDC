package com.furreverhome.Furrever_Home;

import com.furreverhome.Furrever_Home.config.FrontendConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FrontendConfigurationProperties.class})
public class FurreverHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FurreverHomeApplication.class, args);
	}

}
