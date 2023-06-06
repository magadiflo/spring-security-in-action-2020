package com.magadiflo.book.security.app.controllers;

import com.magadiflo.book.security.app.entities.Otp;
import com.magadiflo.book.security.app.entities.User;
import com.magadiflo.book.security.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/user/add")
    public void addUser(@RequestBody User user) {
        this.userService.addUser(user);
    }

    @PostMapping(path = "/user/auth")
    public void auth(@RequestBody User user) {
        this.userService.auth(user);
    }

    @PostMapping(path = "/otp/check")
    public void check(@RequestBody Otp otp, HttpServletResponse response) {
        if (this.userService.check(otp)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
