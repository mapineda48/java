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
    private LaptopCrudRepository LaptopCrudRepository;

    public List<Laptop> findAll() {
        return (List<Laptop>) LaptopCrudRepository.findAll();
    }

    public Optional<Laptop> findById(BigInteger id) {
        return LaptopCrudRepository.findById(id);
    }

    public Laptop save(Laptop laptop) {
        return LaptopCrudRepository.save(laptop);
    }

    public void delete(Laptop laptop) {
        LaptopCrudRepository.delete(laptop);
    }
}