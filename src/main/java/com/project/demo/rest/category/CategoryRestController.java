package com.project.demo.rest.category;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.category.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories") // Asegúrate de que esto NO tiene "/api" si así funciona tu login
public class CategoryRestController {

    private final CategoryRepository repository;

    public CategoryRestController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Category> findAll() {
        return repository.findAll();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public Category create(@RequestBody Category category) {
        return repository.save(category);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category updated) {
        Category category = repository.findById(id).orElseThrow();
        category.setName(updated.getName());
        category.setDescription(updated.getDescription());
        return repository.save(category);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
