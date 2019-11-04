package raze.springboot.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import raze.springboot.recipeapp.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe,Long> {
}
