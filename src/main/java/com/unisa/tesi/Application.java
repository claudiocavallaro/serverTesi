package com.unisa.tesi;

import com.unisa.tesi.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@SpringBootApplication
public class Application {


    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }


}
