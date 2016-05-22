package com.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.micro.actions.PurchaseActions;
import com.micro.data.DAOFactory;
import com.micro.utils.InvalidDaoImplementation;

@SpringBootApplication
@EnableCaching
public class MicroPurchasingSystemApplication {
    public static void main(String[] args) {
        try {
            PurchaseActions.initDaoObjects(DAOFactory.IN_MEMORY_DAO);
        } catch (InvalidDaoImplementation e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SpringApplication.run(MicroPurchasingSystemApplication.class, args);
    }
}
