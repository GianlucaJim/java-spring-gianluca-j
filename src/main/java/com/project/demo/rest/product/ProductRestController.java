package com.project.demo.rest.product;

import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.product.ProductRepository;
import com.project.demo.logic.entity.category.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public ProductRestController(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN_ROLE')")
    @PostMapping
    public Product create(@RequestBody Product product) {
        return productRepo.save(product);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN_ROLE')")
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product updated) {
        Product product = productRepo.findById(id).orElseThrow();
        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        product.setQuantity(updated.getQuantity());
        product.setCategory(updated.getCategory());
        return productRepo.save(product);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN_ROLE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productRepo.deleteById(id);
    }
}
