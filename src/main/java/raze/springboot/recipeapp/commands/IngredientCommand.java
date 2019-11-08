package raze.springboot.recipeapp.commands;

import lombok.Getter;
import lombok.Setter;
import raze.springboot.recipeapp.model.UnitOfMeasure;

import java.math.BigDecimal;
@Getter
@Setter
public class IngredientCommand {
    private Long id;
    private Long recipe_id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasure uom;


}
