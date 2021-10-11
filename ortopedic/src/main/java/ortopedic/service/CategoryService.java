package ortopedic.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ortopedic.entity.Category;
import ortopedic.entity.Ortopedic;
import ortopedic.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    public Optional<Category> getCategory(Integer id) {
        return categoryRepository.getCategory(id);
    }

    public Category save(Category category) {

        if (category.getId() == null) {
            category.setOrtopedics(Collections.emptyList());

            return categoryRepository.save(category);
        }

        Optional<Category> res = categoryRepository.getCategory(category.getId());

        if (res.isEmpty()) {
            return categoryRepository.save(category);
        } else {
            return category;
        }
    }

    public Category update(Category category) {
        Optional<Category> res = categoryRepository.getCategory(category.getId());

        if (!res.isEmpty()) {
            Category record = res.get();

            String name = category.getName();
            String description = category.getDescription();

            if (name != null) {
                record.setName(name);
            }

            if (description != null) {
                record.setDescription(description);
            }

            categoryRepository.save(record);

            return record;
        } else {
            return category;
        }

    }

    public boolean delete(Integer id) {
        Boolean aBoolean = getCategory(id).map(category -> {
            categoryRepository.delete(category);
            return true;
        }).orElse(false);

        return aBoolean;
    }

}
