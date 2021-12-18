package ocho.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ocho.entity.Order;
import ocho.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findByDateAndSalesMan(Date registerDay, Integer id){
        return orderRepository.findByDateAndSalesMan(registerDay, id);
    };

    public List<Order> findByStatusAndSalesMan(String status, Integer id) {
        return orderRepository.findByStatusAndSalesMan(status, id);
    }

    public List<Order> findBySalesMan(Integer id) {
        return orderRepository.findBySalesMan(id);
    }

    public List<Order> findByZone(String zone) {
        return orderRepository.findByZone(zone);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(BigInteger id) {
        return orderRepository.findById(id);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Order order) {
        Optional<Order> res = orderRepository.findById(order.getId());

        if (!res.isEmpty()) {
            Order record = res.get();

            var products = order.getProducts();
            var quantities = order.getQuantities();
            var registerDay = order.getRegisterDay();
            var salesMan = order.getSalesMan();
            var status = order.getStatus();

            record.setProducts(products);
            record.setQuantities(quantities);
            record.setRegisterDay(registerDay);
            record.setSalesMan(salesMan);
            record.setStatus(status);

            orderRepository.save(record);

            return record;
        } else {
            return order;
        }

    }

    public boolean delete(BigInteger id) {
        Boolean aBoolean = findById(id).map(Order -> {
            orderRepository.delete(Order);
            return true;
        }).orElse(false);

        return aBoolean;
    }

}