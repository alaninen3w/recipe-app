package raze.springboot.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import raze.springboot.recipeapp.commands.IngredientCommand;
import raze.springboot.recipeapp.model.Ingredient;
import raze.springboot.recipeapp.model.Recipe;

@Component
public class IngredientCommandToIngredient
        implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure uomCommandConverter;

    public IngredientCommandToIngredient
            (UnitOfMeasureCommandToUnitOfMeasure uomCommandConverter) {
        this.uomCommandConverter = uomCommandConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if(ingredientCommand == null) return  null;

        Ingredient ingredient =
                Ingredient.builder()
                .id(ingredientCommand.getId())
                .description(ingredientCommand.getDescription())
                .amount(ingredientCommand.getAmount())
                .uom(uomCommandConverter
                        .convert(ingredientCommand.getUomCommand()))
                .build();

        if(  ingredientCommand.getRecipe_id() != null ){
            Recipe recipe =
                    Recipe.builder()
                    .id(ingredientCommand.getRecipe_id())
                    .build();
            ingredient.setRecipe(recipe);
            recipe.getIngredients().add(ingredient);

        }


        return ingredient;
    }
}
