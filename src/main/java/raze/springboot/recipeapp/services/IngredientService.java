package raze.springboot.recipeapp.services;

import raze.springboot.recipeapp.commands.IngredientCommand;
import raze.springboot.recipeapp.model.Ingredient;

public interface IngredientService extends CrudService<Ingredient,Long> {

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    Ingredient findByRecipeAndIngredientId(Long recipeId , Long ingredientId);

    void deleteById(Long recipeId , Long ingredientId);

}
