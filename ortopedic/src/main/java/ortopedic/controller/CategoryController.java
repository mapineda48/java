package ortopedic.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ortopedic.entity.Category;
import ortopedic.service.CategoryService;

@RestController
@RequestMapping("/api/Category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Category> getById(@PathVariable("id") Integer id) {
        return categoryService.getCategory(id);
    }

    @PostMapping("/save")
    public Category save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/update")
    public Category update(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return categoryService.delete(id);
    }

}
