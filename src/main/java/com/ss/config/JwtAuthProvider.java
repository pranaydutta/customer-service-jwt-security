package com.ss.config;


import com.ss.config.model.UserDetailsImpl;
import com.ss.config.model.UsernamePasswordAuthenticationTokenImpl;
import com.ss.model.ValidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private com.ss.config.ConfigUtils configUtils;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        //No Opp required
    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        UsernamePasswordAuthenticationTokenImpl token=(UsernamePasswordAuthenticationTokenImpl)usernamePasswordAuthenticationToken;
        MultiValueMap<String,String> headers=new LinkedMultiValueMap<>();
        headers.add("x-auth-token",token.getToken());
        headers.add(HttpHeaders.AUTHORIZATION, configUtils.getBasicauth());
        HttpEntity<UsernamePasswordAuthenticationTokenImpl> entity=new HttpEntity<>(headers);
        ValidateResponse response=new RestTemplate().exchange(configUtils.getValidateurl(), HttpMethod.GET,entity, ValidateResponse.class).getBody();
        List< GrantedAuthority> grantedAuthorities= AuthorityUtils.commaSeparatedStringToAuthorityList(response.getRoles());
        return  new UserDetailsImpl(response.getUserName(),response.getUserId(),grantedAuthorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationTokenImpl.class.isAssignableFrom(authentication);
    }
}
