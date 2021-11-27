package ocho.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ocho.entity.User;

@Repository
public class UserRepository {
    @Autowired
    private UserCrudRepository userCrudRepository;

    public List<User> findAll() {
        return (List<User>) userCrudRepository.findAll();
    }

    public Optional<User> findById(String id) {
        return userCrudRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userCrudRepository.findByEmail(email);
    }

    public User login(String email, String password) {
        var res = userCrudRepository.login(email, password);

        if (res.isPresent()) {
            return res.get();
        } else {
            User user = new User();

            user.setPassword(password);
            user.setEmail(email);
            user.setName("NO DEFINIDO");

            return user;
        }

    }

    public User save(User user) {
        return userCrudRepository.save(user);
    }

    public void delete(User user) {
        userCrudRepository.delete(user);
    }
}