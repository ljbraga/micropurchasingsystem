package com.micro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import com.micro.actions.PurchaseActions;
import com.micro.data.DAOFactory;
import com.micro.utils.InvalidDaoImplementation;

@SpringBootApplication
@EnableCaching
public class MicroPurchasingSystemApplication {
    private static final Logger log = LoggerFactory.getLogger(MicroPurchasingSystemApplication.class);

    
     @Component
     static class Runner implements CommandLineRunner {
         public void run (String... args) throws Exception {
            for (int i=0; i < 3; i++) {
             try {
             log.info("get purchase 1 -->");
             PurchaseActions.getPurchase(1);
            
         
            } catch (Exception e) {
                log.info("\t failed");
                
            } 
             }
         }
     }
     

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
