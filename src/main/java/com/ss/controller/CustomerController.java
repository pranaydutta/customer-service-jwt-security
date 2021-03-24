package com.ss.controller;

import com.ss.config.ConfigUtils;
import com.ss.model.AuthToken;
import com.ss.model.CustomerRegistrationRequest;
import com.ss.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ConfigUtils configUtils;

    private RestTemplate template;

    @Autowired
    public CustomerController(RestTemplateBuilder builder)
    {
        this.template=builder.build();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthToken register(@RequestBody CustomerRegistrationRequest registrationRequest)
    {
        HttpHeaders headers=new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION,configUtils.getBasicauth());
        HttpEntity<CustomerRegistrationRequest> entity=new HttpEntity<>(registrationRequest,headers);
        return  template.exchange(configUtils.getRegistrationurl(), HttpMethod.POST,entity,AuthToken.class).getBody();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthToken login(@RequestBody LoginRequest loginDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, configUtils.getBasicauth());
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginDto, headers);
        return template.exchange(configUtils.getLoginurl(), HttpMethod.POST, entity, AuthToken.class).getBody();
    }

    @GetMapping("/wallet-balance")
    @ResponseStatus(HttpStatus.OK)
    public double getWalletBalance() {
        // dummy logic
        return 5000.00;
    }

    @GetMapping("/walletOffer")
    @ResponseStatus(HttpStatus.OK)
    public String getWalletOffer() {
        // dummy logic
        return "Currently no offer is available";
    }

}
