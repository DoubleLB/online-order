package org.lbw.story;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //本应用启用为eureka服务器
public class StoryEurekaApp {
    public static void main(String[] args) {
        SpringApplication.run(StoryEurekaApp.class,args);
    }
}
