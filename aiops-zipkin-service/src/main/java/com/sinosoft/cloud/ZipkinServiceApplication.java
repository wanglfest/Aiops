package com.sinosoft.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@EnableZipkinStreamServer
@SpringBootApplication
@EnableDiscoveryClient
public class ZipkinServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipkinServiceApplication.class, args);
	}
}
