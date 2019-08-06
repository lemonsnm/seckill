package com.lemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration //将该启动类当成一个自动化可以配置的一个bean，并且能够开启整个工程类的基于springboot一个自动化的配置
@RestController   // @RestController  = @Controller +  @RequestBody
public class App 
{

    @RequestMapping("/")
    public String home(){

        return "hello world!";
    }
    public static void main( String[] args )
    {
        System.out.println( "Hello world!" );
        //启动springboot项目、
        SpringApplication.run(App.class,args);
    }
}
