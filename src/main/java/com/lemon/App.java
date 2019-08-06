package com.lemon;

import com.lemon.dao.UserDOMapper;
import com.lemon.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */

/*@EnableAutoConfiguration //将该启动类当成一个自动化可以配置的一个bean，并且能够开启整个工程类的基于springboot一个自动化的配置*/
//SpringBootApplication和 EnableAutoConfiguration功能类似
@SpringBootApplication(scanBasePackages = {"com.lemon"})
@RestController   // @RestController  = @Controller +  @RequestBody
@MapperScan("com.lemon.dao")   //dao存放
public class App 
{
    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping("/")
    public String home(){

        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if(userDO == null){
            return "用户不存在";
        }else{
            return userDO.getName();
        }
    }
    public static void main( String[] args )
    {
        System.out.println( "Hello world!" );
        //启动springboot项目、
        SpringApplication.run(App.class,args);
    }
}
