package ocho.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ocho.entity.Laptop;

public interface LaptopRepository extends MongoRepository<Laptop, BigInteger> {
    @Query(value = "{ price: ?0 }")
    public List<Laptop> findByPrice(Double price);

    @Query(value = "{description : {$regex : '?0'}}")
    public List<Laptop> findByDescription(String description);

}
