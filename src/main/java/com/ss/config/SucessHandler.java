package com.ss.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SucessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
 

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Successfully Authenticated : with role " +authentication.getAuthorities());

        //redirectStrategy.sendRedirect(request,response,"http://localhost:8085/customers/wallet-balance");
    }
}
