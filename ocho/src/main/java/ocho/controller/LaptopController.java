package ocho.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

import ocho.entity.Laptop;
import ocho.service.LaptopService;

@RestController
@RequestMapping("/api/laptop")
@PreAuthorize("hasRole('ADMIN')")
public class LaptopController {

    @Autowired
    private LaptopService laptopService;

    @GetMapping("/description/{description}")
    public List<Laptop> findByDescription(@PathVariable("description") String description) {
        return laptopService.findByDescription(description);
    }

    @GetMapping("/price/{price}")
    public List<Laptop> findByPrice(@PathVariable("price") Double price) {
        return laptopService.findByPrice(price);
    };

    @GetMapping("/all")
    public List<Laptop> findAll() {
        return laptopService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Laptop> findById(@PathVariable("id") BigInteger id) {
        return laptopService.findById(id);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Laptop save(@RequestBody Laptop laptop) {
        try {
            return laptopService.save(laptop);
        } catch (Exception e) {
            System.out.println(e);
            return new Laptop();
        }
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Laptop update(@RequestBody Laptop laptop) {
        return laptopService.update(laptop);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable("id") BigInteger id) {
        return laptopService.delete(id);
    }

}