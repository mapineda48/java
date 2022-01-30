package ocho.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ocho.entity.User;

public interface UserRepository extends MongoRepository<User, BigInteger> {
    @Query(value = "{ username: ?0 }")
    public Optional<User> findByUserName(String username);

    @Query(value = "{ monthBirthtDay: ?0 }")
    public List<User> findByMonthDay(String monthDay);

    @Query(value = "{ email: ?0 }")
    public Optional<User> findByEmail(String email);

    @Query(value = "{ email:?0, password:?1 }")
    public Optional<User> login(String email, String password);
}
