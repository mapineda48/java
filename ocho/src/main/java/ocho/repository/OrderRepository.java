package ocho.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ocho.entity.Order;

@Repository
public class OrderRepository {
    @Autowired
    private OrderCrudRepository orderCrudRepository;

    public List<Order> findByStatusAndSalesMan(String status, Integer id) {
        return orderCrudRepository.findByStatusAndSalesMan(status, id);
    }

    public List<Order> findBySalesMan(Integer id) {
        return orderCrudRepository.findBySalesMan(id);
    }

    public List<Order> findByZone(String zone) {
        return orderCrudRepository.findByZone(zone);
    }

    public List<Order> findAll() {
        return orderCrudRepository.findAll();
    }

    public Optional<Order> findById(BigInteger id) {
        return orderCrudRepository.findById(id);
    }

    public Order save(Order order) {
        return orderCrudRepository.save(order);
    }

    public void delete(Order order) {
        orderCrudRepository.delete(order);
    }
}