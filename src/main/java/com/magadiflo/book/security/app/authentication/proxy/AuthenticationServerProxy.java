package com.magadiflo.book.security.app.authentication.proxy;

import com.magadiflo.book.security.app.authentication.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationServerProxy {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${auth.server.base.url}")
    private String baseUrl;

    public void sendAuth(String username, String password) {
        String url = this.baseUrl + "/user/auth";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        HttpEntity<User> request = new HttpEntity<>(user);

        this.restTemplate.postForEntity(url, request, Void.class);
    }

    public boolean sendOTP(String username, String code) {
        String url = this.baseUrl + "/otp/check";

        User user = new User();
        user.setUsername(username);
        user.setCode(code);

        HttpEntity<User> request = new HttpEntity<>(user);

        ResponseEntity<Void> response = this.restTemplate.postForEntity(url, request, Void.class);
        return response.getStatusCode().equals(HttpStatus.OK);
    }
}
