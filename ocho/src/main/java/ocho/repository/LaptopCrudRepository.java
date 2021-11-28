package ocho.repository;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;

import ocho.entity.Laptop;

public interface LaptopCrudRepository extends MongoRepository<Laptop, BigInteger> {
}
