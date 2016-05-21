package com.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import controllers.PurchaseController;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackageClasses = PurchaseController.class)
public class MicroPurchasingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroPurchasingSystemApplication.class, args);
	}
}
