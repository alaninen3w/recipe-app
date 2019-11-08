package raze.springboot.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import raze.springboot.recipeapp.commands.RecipeCommand;
import raze.springboot.recipeapp.model.Recipe;

import java.util.stream.Collectors;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final NotesToNotesCommand notesToNotesCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommand
            , IngredientToIngredientCommand ingredientToIngredientCommand
            , CategoryToCategoryCommand categoryToCategoryCommand) {
        this.notesToNotesCommand = notesToNotesCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if(recipe ==  null) return null;

        RecipeCommand recipeCommand =
                RecipeCommand.builder()
                .id(recipe.getId())
                .description(recipe.getDescription())
                .prepTime(recipe.getPrepTime())
                .cookTime(recipe.getCookTime())
                .directions(recipe.getDirections())
                .serving(recipe.getServings())
                .difficulty(recipe.getDifficulty())
                .source(recipe.getSource())
                .url(recipe.getUrl())
                .image(recipe.getImage())
                .notes(notesToNotesCommand.convert(recipe.getNotes()))
                .ingredients(recipe.getIngredients() == null ? null
                        :recipe.getIngredients()
                        .stream().map(ingredientToIngredientCommand::convert)
                        .collect(Collectors.toSet()))
                .categories(recipe.getCategories() == null ? null
                        :recipe.getCategories()
                        .stream().map(categoryToCategoryCommand::convert)
                        .collect(Collectors.toSet()))
                .build();





        return recipeCommand;
    }
}
