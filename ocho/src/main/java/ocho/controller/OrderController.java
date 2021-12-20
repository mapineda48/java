package ocho.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ocho.entity.Order;
import ocho.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private static String FORMAT_DATE ="yyyy-MM-dd'T'HH:mm:ss.SSSXXX"; 


    @Autowired
    private OrderService orderService;

    @GetMapping("/date/{date}/{id}")
    public List<Order> findByDateAndSalesMan(@PathVariable("date") String date,
            @PathVariable("id") Integer id) {

        try {
            var dateTime = date + "T05:00:00.000+00:00";

            var registerDay = new SimpleDateFormat(FORMAT_DATE).parse(dateTime);

            return orderService.findByDateAndSalesMan(registerDay, id);
        } catch (Exception e) {
            System.out.println(e);

            return new ArrayList<Order>();
        }

    };

    @GetMapping("/state/{status}/{id}")
    public List<Order> findByStatusAndSalesMan(@PathVariable("status") String status, @PathVariable("id") Integer id) {
        return orderService.findByStatusAndSalesMan(status, id);
    }

    @GetMapping("/salesman/{id}")
    public List<Order> findBySalesMan(@PathVariable("id") Integer id) {
        return orderService.findBySalesMan(id);
    }

    @GetMapping("/zona/{zone}")
    public List<Order> findByZone(@PathVariable("zone") String zone) {
        return orderService.findByZone(zone);
    }

    @GetMapping("/all")
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Order> findById(@PathVariable("id") BigInteger id) {
        return orderService.findById(id);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Order save(@RequestBody Order order) {
        try {
            return orderService.save(order);
        } catch (Exception e) {
            System.out.println(e);
            return new Order();
        }
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Order update(@RequestBody Order order) {
        return orderService.update(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable("id") BigInteger id) {
        return orderService.delete(id);
    }

}