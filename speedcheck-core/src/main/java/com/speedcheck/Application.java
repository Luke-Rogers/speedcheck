package com.speedcheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
        HibernateJpaSessionFactoryBean fact = new HibernateJpaSessionFactoryBean();
        fact.setEntityManagerFactory(emf);
        return fact;
    }

}
