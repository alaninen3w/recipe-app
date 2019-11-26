package raze.springboot.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import raze.springboot.recipeapp.model.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    Optional<Category> findByDescription(String description);
}
