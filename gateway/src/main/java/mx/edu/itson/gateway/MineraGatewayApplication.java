package mx.edu.itson.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MineraGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MineraGatewayApplication.class, args);
	}

}
