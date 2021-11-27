package ocho.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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

import ocho.entity.User;
import ocho.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/id/{id}")
    public Optional<User> findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }

    @GetMapping("/{email}")
    public boolean findByEmail(@PathVariable("email") String email) {
        var res = userService.findByEmail(email);

        if (res.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/{email}/{password}")
    public User login(@PathVariable("email") String email, @PathVariable("password") String password) {
        return userService.login(email, password);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody User user) {
        try {
            return userService.save(user);
        } catch (Exception e) {
            System.out.println(e);
            return new User();
        }
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable("id") String id) {
        return userService.delete(id);
    }

}