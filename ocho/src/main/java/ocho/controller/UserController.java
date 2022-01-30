package ocho.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import ocho.entity.User;
import ocho.security.UserSecurity;
import ocho.service.UserService;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/birthday/{monthDay}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findByMonthDay(@PathVariable("monthDay") String monthDay) {
        return userService.findByMonthDay(monthDay);
    };

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<User> findById(@PathVariable("id") BigInteger id) {
        return userService.findById(id);
    }

    @GetMapping("/emailexist/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean findByEmail(@PathVariable("email") String email) {
        var res = userService.findByEmail(email);

        if (res.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody User user) {
        try {
            return userService.save(user);
        } catch (Exception e) {
            log.error("Cannot create user : {}", e);
            return new User();
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public User update(@RequestBody User user, Authentication authentication) {
        UserSecurity principal = (UserSecurity) authentication.getPrincipal();

        if (!principal.canDoCRUD(user)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable("id") BigInteger id) {
        return userService.delete(id);
    }
}