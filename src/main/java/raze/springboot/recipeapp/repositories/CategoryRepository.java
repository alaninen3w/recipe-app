package raze.springboot.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import raze.springboot.recipeapp.model.Category;

public interface CategoryRepository extends CrudRepository<Category,Long> {
}
