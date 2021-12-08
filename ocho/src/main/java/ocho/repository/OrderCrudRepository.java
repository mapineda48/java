package ocho.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ocho.entity.Order;

public interface OrderCrudRepository extends MongoRepository<Order, BigInteger> {
    @Query(value = "{ status: ?0, 'salesMan._id': '?1' }")
    public List<Order> findByStatusAndSalesMan(String status, Integer id);

    @Query(value = "{ 'salesMan._id': '?0' }")
    public List<Order> findBySalesMan(Integer id);

    @Query(value = "{ 'salesMan.zone': ?0 }")
    public List<Order> findByZone(String zone);

}
