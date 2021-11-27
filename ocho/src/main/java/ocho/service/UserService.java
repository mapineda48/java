package ocho.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ocho.entity.User;
import ocho.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User login(String email, String password) {
        return userRepository.login(email, password);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        Optional<User> res = userRepository.findById(user.getId());

        if (!res.isEmpty()) {
            User record = res.get();

            String name = user.getName();
            String email = user.getEmail();
            String password = user.getPassword();

            if (name != null) {
                record.setName(name);
            }

            if (email != null) {
                record.setEmail(email);
            }

            if (password != null) {
                record.setPassword(password);
            }

            userRepository.save(record);

            return record;
        } else {
            return user;
        }

    }

    public boolean delete(String id) {
        Boolean aBoolean = findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);

        return aBoolean;
    }

}