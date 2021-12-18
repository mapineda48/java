package ocho.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ocho.entity.Laptop;

@Repository
public class LaptopRepository {
    @Autowired
    private LaptopCrudRepository laptopCrudRepository;

    public List<Laptop> findByDescription(String description) {
        return laptopCrudRepository.findByDescription(description);
    }

    public List<Laptop> findByPrice(Double price) {
        return laptopCrudRepository.findByPrice(price);
    };

    public List<Laptop> findAll() {
        return (List<Laptop>) laptopCrudRepository.findAll();
    }

    public Optional<Laptop> findById(BigInteger id) {
        return laptopCrudRepository.findById(id);
    }

    public Laptop save(Laptop laptop) {
        return laptopCrudRepository.save(laptop);
    }

    public void delete(Laptop laptop) {
        laptopCrudRepository.delete(laptop);
    }
}