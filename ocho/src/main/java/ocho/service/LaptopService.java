package ocho.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ocho.entity.Laptop;
import ocho.repository.LaptopRepository;

@Service
public class LaptopService {

    @Autowired
    private LaptopRepository laptopRepository;

    public List<Laptop> findByDescription(String description) {
        return laptopRepository.findByDescription(description);
    }

    public List<Laptop> findByPrice(Double price) {
        return laptopRepository.findByPrice(price);
    };

    public List<Laptop> findAll() {
        return laptopRepository.findAll();
    }

    public Optional<Laptop> findById(BigInteger id) {
        return laptopRepository.findById(id);
    }

    public Laptop save(Laptop laptop) {
        return laptopRepository.save(laptop);
    }

    public Laptop update(Laptop laptop) {
        Optional<Laptop> res = laptopRepository.findById(laptop.getId());

        if (!res.isEmpty()) {
            Laptop record = res.get();

            var brand = laptop.getBrand();
            var description = laptop.getDescription();
            var hardDrive = laptop.getHardDrive();
            var memory = laptop.getMemory();
            var model = laptop.getModel();
            var os = laptop.getOs();
            var photography = laptop.getPhotography();
            var price = laptop.getPrice();
            var procesor = laptop.getProcesor();
            var quantity = laptop.getQuantity();

            record.setBrand(brand);
            record.setDescription(description);
            record.setHardDrive(hardDrive);
            record.setMemory(memory);
            record.setModel(model);
            record.setOs(os);
            record.setPhotography(photography);
            record.setPrice(price);
            record.setProcesor(procesor);
            record.setQuantity(quantity);

            laptopRepository.save(record);

            return record;
        } else {
            return laptop;
        }

    }

    public boolean delete(BigInteger id) {
        Boolean aBoolean = findById(id).map(Laptop -> {
            laptopRepository.delete(Laptop);
            return true;
        }).orElse(false);

        return aBoolean;
    }

}