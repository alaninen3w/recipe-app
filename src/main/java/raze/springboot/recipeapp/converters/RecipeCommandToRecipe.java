package raze.springboot.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import raze.springboot.recipeapp.commands.RecipeCommand;
import raze.springboot.recipeapp.model.Recipe;

import java.util.stream.Collectors;

@Component
public class RecipeCommandToRecipe
        implements Converter<RecipeCommand , Recipe> {
    private final NotesCommandToNotes notesCommandToNotes;
    private final  IngredientCommandToIngredient ingredientCommandToIngredient;
    private final CategoryCommandToCategory categoryCommandToCategory;

    public RecipeCommandToRecipe(NotesCommandToNotes notesCommandToNotes
            , IngredientCommandToIngredient ingredientCommandToIngredient
            , CategoryCommandToCategory categoryCommandToCategory) {
        this.notesCommandToNotes = notesCommandToNotes;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if(recipeCommand == null) return  null;

        Recipe recipe =
                Recipe.builder()
                .id(recipeCommand.getId())
                .description(recipeCommand.getDescription())
                .difficulty(recipeCommand.getDifficulty())
                .directions(recipeCommand.getDirections())
                .cookTime(recipeCommand.getCookTime())
                .prepTime(recipeCommand.getPrepTime())
                .servings(recipeCommand.getServing())
                .source(recipeCommand.getSource())
                .url(recipeCommand.getUrl())
                .image(recipeCommand.getImage())
                .notes(recipeCommand.getNotes() == null ? null
                        :notesCommandToNotes
                        .convert(recipeCommand.getNotes()))
                .ingredients(recipeCommand.getIngredients() == null ? null
                        :recipeCommand.getIngredients()
                        .stream().map(ingredientCommandToIngredient::convert)
                        .collect(Collectors.toSet()))
                .categories(recipeCommand.getCategories() == null ? null
                        :recipeCommand.getCategories()
                        .stream().map(categoryCommandToCategory::convert)
                        .collect(Collectors.toSet()))
                .build();


        return recipe;
    }
}
