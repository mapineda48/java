package ortopedic.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ortopedic.entity.Category;
import ortopedic.entity.Ortopedic;
import ortopedic.repository.OrtopedicRepository;

@Service
public class OrtopedicService {

    @Autowired
    private OrtopedicRepository ortopedicRepository;

    @Autowired
    private CategoryService categoryService;

    public List<Ortopedic> getAll() {
        return ortopedicRepository.getAll();
    }

    public Optional<Ortopedic> getOrtopedic(Integer id) {
        return ortopedicRepository.getOrtopedic(id);
    }

    public Ortopedic save(Ortopedic ortopedic) {

        if (ortopedic.getId() == null) {
            Category category = ortopedic.getCategory();

            if (category != null) {
                Integer id = category.getId();

                if (id != null) {
                    Optional<Category> res = categoryService.getCategory(id);

                    if (res.isPresent()) {
                        ortopedic.setCategory(res.get());
                    }
                }
            }

            ortopedic.setMessages(Collections.emptyList());

            ortopedic.setReservations(Collections.emptyList());

            return ortopedicRepository.save(ortopedic);
        }

        Optional<Ortopedic> res = ortopedicRepository.getOrtopedic(ortopedic.getId());

        if (res.isEmpty()) {
            return ortopedicRepository.save(ortopedic);
        } else {
            return ortopedic;
        }
    }

    public Ortopedic update(Ortopedic ortopedic) {
        Optional<Ortopedic> res = ortopedicRepository.getOrtopedic(ortopedic.getId());

        if (!res.isEmpty()) {
            Ortopedic record = res.get();

            String name = ortopedic.getName();
            String description = ortopedic.getDescription();
            String brand = ortopedic.getBrand();
            Integer year = ortopedic.getYear();

            if (name != null) {
                record.setName(name);
            }

            if (description != null) {
                record.setDescription(description);
            }

            if (brand != null) {
                record.setBrand(brand);
            }

            if (year != null) {
                record.setYear(year);
            }

            ortopedicRepository.save(record);

            return record;
        } else {
            return ortopedic;
        }

    }

    public boolean delete(Integer id) {
        Boolean aBoolean = getOrtopedic(id).map(ortopedic -> {
            ortopedicRepository.delete(ortopedic);
            return true;
        }).orElse(false);

        return aBoolean;
    }

}
