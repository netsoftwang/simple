package com.palace.memories.test.main;

import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@ComponentScan(basePackages="com.palace")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
         ApplicationContext context = SpringApplication.run(App.class, args);
         MappingJackson2HttpMessageConverter converter = context.getBean(MappingJackson2HttpMessageConverter.class);
         ObjectMapper mapper = new ObjectMapper();
         mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
         converter.setObjectMapper(mapper);
     }
}
