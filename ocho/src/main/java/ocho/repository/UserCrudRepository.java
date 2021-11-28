package ocho.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ocho.entity.User;

public interface UserCrudRepository extends MongoRepository<User, BigInteger> {

    @Query(value = "{ email: ?0 }")
    public Optional<User> findByEmail(String email);

    @Query(value = "{ email:?0, password:?1 }")
    public Optional<User> login(String email, String password);
}
