package com.ss.config.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class UsernamePasswordAuthenticationTokenImpl extends UsernamePasswordAuthenticationToken {

    private String token;

    public UsernamePasswordAuthenticationTokenImpl(String token) {
        super(null,null);
        this.token = token;
    }
}
