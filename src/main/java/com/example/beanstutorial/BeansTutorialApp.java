package com.example.springbootdemo.beanTutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BeansTutorialApp {

    public interface SaySomnService{
        public String saySomn();
    }

    @Component
    @Qualifier("sayHelloService")
    public class SayHelloService implements SaySomnService{

        @Override
        public String saySomn() {
            return "Sup Nackers";
        }
    }



    @Configuration
    public class SaySomnConfig{
        @Bean
        @Primary
        public SaySomnConfigService saySomnConfigService(){
            SaySomnConfigService saySomnConfigService = new SaySomnConfigService();
            saySomnConfigService.setWhat2Say("Goodbye");
            return saySomnConfigService;
        }
    }
    public class SaySomnConfigService implements SaySomnService{
        private String what2Say = "";
        @Override
        public String saySomn() {
            return what2Say;
        }

        public String getWhat2Say() {
            return what2Say;
        }

        public void setWhat2Say(String what2Say) {
            this.what2Say = what2Say;
        }
    }

    @RestController
    public class SaySomnController{
        @Autowired
        @Qualifier
        SaySomnService saySomnService;

        @GetMapping("/")
        public String home(){
            return saySomnService.saySomn();
        }

    }


    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(BeansTutorialApp.class, args);
        SaySomnService saySomnService = applicationContext.getBean(SaySomnService.class);
        System.out.println(saySomnService.saySomn());
    }

}
