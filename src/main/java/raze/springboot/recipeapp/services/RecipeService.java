package raze.springboot.recipeapp.services;

import raze.springboot.recipeapp.commands.RecipeCommand;
import raze.springboot.recipeapp.model.Recipe;

public interface RecipeService extends CrudService<Recipe,Long> {
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

}
