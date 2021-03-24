package com.ss.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.auth-service")
public class ConfigUtils {

    private String registrationurl;
    private String loginurl;
    private String validateurl;
    private String basicauth;

}
