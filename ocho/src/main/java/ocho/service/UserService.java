package ocho.service;

import java.math.BigInteger;
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

    public List<User> findByMonthDay(String monthDay) {
        return userRepository.findByMonthDay(monthDay);
    };

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(BigInteger id) {
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

            var name = user.getName();
            var email = user.getEmail();
            var password = user.getPassword();
            var address = user.getAddress();
            var identification = user.getIdentification();
            var type = user.getType();
            var zone = user.getZone();
            var cellPhone = user.getCellPhone();

            record.setName(name);
            record.setEmail(email);
            record.setPassword(password);
            record.setAddress(address);
            record.setCellPhone(cellPhone);
            record.setIdentification(identification);
            record.setType(type);
            record.setZone(zone);

            userRepository.save(record);

            return record;
        } else {
            return user;
        }

    }

    public boolean delete(BigInteger id) {
        Boolean aBoolean = findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);

        return aBoolean;
    }

}