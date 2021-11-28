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
    private LaptopRepository LaptopRepository;

    public List<Laptop> findAll() {
        return LaptopRepository.findAll();
    }

    public Optional<Laptop> findById(BigInteger id) {
        return LaptopRepository.findById(id);
    }

    public Laptop save(Laptop laptop) {
        return LaptopRepository.save(laptop);
    }

    public Laptop update(Laptop laptop) {
        Optional<Laptop> res = LaptopRepository.findById(laptop.getId());

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

            LaptopRepository.save(record);

            return record;
        } else {
            return laptop;
        }

    }

    public boolean delete(BigInteger id) {
        Boolean aBoolean = findById(id).map(Laptop -> {
            LaptopRepository.delete(Laptop);
            return true;
        }).orElse(false);

        return aBoolean;
    }

}