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

    public List<User> findAllWhereNotEmail(String email){
        return userRepository.findAllWhereNotEmail(email);
    };


    public User login(String email, String password) {
        var res = userRepository.login(email, password);

        if (res.isPresent()) {
            return res.get();
        } else {
            User user = new User();

            // user.setPassword(password);
            // user.setEmail(email);
            // user.setName("NO DEFINIDO");

            return user;
        }

    }

    public List<User> findByMonthDay(String monthDay) {
        return userRepository.findByMonthDay(monthDay);
    };

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(BigInteger id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        var ms = System.currentTimeMillis();

        var id = BigInteger.valueOf(ms);

        user.setId(id);

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
            var role = user.getRole();
            var zone = user.getZone();
            var cellPhone = user.getCellPhone();
            var urlAvatar = user.getUrlAvatar();

            record.setName(name);
            record.setEmail(email);
            record.setPassword(password);
            record.setAddress(address);
            record.setCellPhone(cellPhone);
            record.setIdentification(identification);
            record.setRole(role);
            record.setZone(zone);
            record.setUrlAvatar(urlAvatar);

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