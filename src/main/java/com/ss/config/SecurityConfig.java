package com.ss.config;

import com.ss.config.JwtAuthFilter;
import com.ss.config.JwtAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthProvider provider;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/customers/register", "/customers/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        OrRequestMatcher securedUrls=new OrRequestMatcher(new AntPathRequestMatcher("/customers/**"));
        JwtAuthFilter jwtAuthFilter=new JwtAuthFilter(securedUrls);
        jwtAuthFilter.setAuthenticationManager(authenticationManager());
        jwtAuthFilter.setAuthenticationSuccessHandler(new com.ss.config.SucessHandler());

        http.csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }

//@Autowired
//    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
//        builder.inMemoryAuthentication()
//                .withUser("admin")
//                .password("{noop}admin")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{noop}user")
//                .roles("USER");
//
//    }
}
