package com.karl.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author karl
 */
@SpringBootApplication(scanBasePackages = {"com.baomidou", "com.karl"})
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan({"com.karl.**.mapper"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
