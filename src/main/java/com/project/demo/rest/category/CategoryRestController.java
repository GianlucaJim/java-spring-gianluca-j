package com.project.demo.rest.category;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.category.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryRepository repository;

    public CategoryRestController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Category> findAll() {
        return repository.findAll();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN_ROLE')")
    @PostMapping
    public Category create(@RequestBody Category category) {
        return repository.save(category);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN_ROLE')")
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category updated) {
        Category category = repository.findById(id).orElseThrow();
        category.setName(updated.getName());
        category.setDescription(updated.getDescription());
        return repository.save(category);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN_ROLE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
