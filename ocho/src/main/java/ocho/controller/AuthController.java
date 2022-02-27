package ocho.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import ocho.entity.Credential;
import ocho.security.JwtSecurity;
import ocho.security.UserSignIn;
import ocho.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtSecurity jwtSecurity;

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public UserSignIn login(@RequestBody Credential credential) {
        var user = userService.login(credential.getEmail(), credential.getPassword());

        var res = jwtSecurity.signin(user);

        return res;
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public String refresh(@RequestBody String token) {

        try {
            return jwtSecurity.refreshToken(token);
        } catch (TokenExpiredException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
    }

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public String valdiate() {
        return "OK";
    }
}
