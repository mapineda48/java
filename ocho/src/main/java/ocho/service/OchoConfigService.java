package ocho.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ocho.entity.OchoConfig;
import ocho.entity.User;
import ocho.repository.OchoConfigRepository;

@Slf4j
@Service
public class OchoConfigService {

    private OchoConfigRepository ochoConfigRepository;

    @Autowired
    OchoConfigService(OchoConfigRepository ochoConfigRepository,UserService userService, @Value("${app.email}") String email,
            @Value("${app.password}") String password) {
        this.ochoConfigRepository = ochoConfigRepository;
        prepareConfig();
        setAdminUser(userService, email, password);
    }

    private void setAdminUser(UserService userService, String email, String password) {
        var res = userService.findByEmail(email);

        if (res.isPresent()) {
            var current = res.get();
            log.info("found admin: {}", current);
            return;
        }

        var admin = new User();

        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRole("ROLE_ADMIN");
        admin.setUrlAvatar("/admin.png");
        admin.setName("SuperUsuario Sistema");

        userService.save(admin);

        log.info("save admin: {}", admin);
    }

    private void prepareConfig() {
        var existsConfig = !ochoConfigRepository.findAll().isEmpty();

        if (existsConfig) {
            log.info("config exists");
            return;
        }

        var ochoConfig = new OchoConfig();

        ochoConfig.setBucketname(UUID.randomUUID().toString());

        ochoConfigRepository.save(ochoConfig);
    }

    public String getBucketName() {
        return ochoConfigRepository.findAll().get(0).getBucketname();
    }
}
