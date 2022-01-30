package ocho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ocho.entity.User;
import ocho.service.UserService;

@RestController
@RequestMapping("/api/demo")
public class DemoController {
    
    @Value("${ocho.email}")
    private String email;

    @Autowired
    private UserService userService;

    @PostMapping("/admin")
    public User getAdminToDemo(){
        return userService.findByEmail(email).get();
    }
}
