package raze.springboot.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import raze.springboot.recipeapp.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient,Long> {
}
