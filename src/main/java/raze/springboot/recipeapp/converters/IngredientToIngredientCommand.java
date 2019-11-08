package raze.springboot.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import raze.springboot.recipeapp.commands.IngredientCommand;
import raze.springboot.recipeapp.model.Ingredient;
@Component
public class IngredientToIngredientCommand
        implements Converter<Ingredient, IngredientCommand> {
    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if(ingredient ==  null) return  null;


        IngredientCommand ingredientCommand =
                IngredientCommand.builder()
                .id(ingredient.getId())
                .description(ingredient.getDescription())
                .amount(ingredient.getAmount())
                .recipe_id(ingredient.getRecipe()==null?
                        null : ingredient.getRecipe().getId())
                .uomCommand(uomConverter.convert(ingredient.getUom()))
                .build();

        return ingredientCommand;
    }
}
