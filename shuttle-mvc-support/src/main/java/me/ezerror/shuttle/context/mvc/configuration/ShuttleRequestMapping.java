package me.ezerror.shuttle.context.mvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


@Configuration
public class ShuttleRequestMapping {
    
    @Bean
    public RequestMappingHandlerMapping shuttleMapping() {
        return new RequestMappingHandlerMapping();
    }
}
