package ocho.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import ocho.entity.User;
import ocho.entity.UserDashboard;
import ocho.security.UserSecurity;
import ocho.service.MinioService;
import ocho.service.UserService;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MinioService minioService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAll(Authentication authentication) {
        UserSecurity principal = (UserSecurity) authentication.getPrincipal();

        var emailUser = principal.getUser().getEmail();

        return userService.findAllWhereNotEmail(emailUser);
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

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/new", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public User save(@RequestPart User user, @RequestPart(required = false) MultipartFile avatar) {
        try {
            if (avatar != null) {
                var urlAvatar = minioService.uploadFile(avatar);
                user.setUrlAvatar(urlAvatar);
            } else {
                user.setUrlAvatar("/avatar.jpg");
            }

            return userService.save(user);
        } catch (Exception e) {
            log.error("Cannot create user : {}", e);

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(path = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public User update(Authentication authentication, @RequestPart User user,
            @RequestPart(required = false) MultipartFile avatar) {
        UserSecurity principal = (UserSecurity) authentication.getPrincipal();

        if (!principal.canDoCRUD(user)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        if (avatar != null) {
            minioService.removeFile(user.getUrlAvatar());

            var urlAvatar = minioService.uploadFile(avatar);

            log.info("url update {}", urlAvatar);
            user.setUrlAvatar(urlAvatar);
        }

        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable("id") BigInteger id) {
        return userService.delete(id);
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDashboard getUserDashboardData(Authentication authentication) {
        UserSecurity principal = (UserSecurity) authentication.getPrincipal();

        var user = principal.getUser();

        var userDashboard = new UserDashboard();
        userDashboard.setFullName(user.getName());
        userDashboard.setUrlAvatar(user.getUrlAvatar());

        return userDashboard;
    }
}