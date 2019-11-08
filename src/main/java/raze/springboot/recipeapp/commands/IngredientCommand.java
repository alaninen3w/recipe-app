package raze.springboot.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
public class IngredientCommand {
    private Long id;
    private Long recipe_id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uomCommand;


}
