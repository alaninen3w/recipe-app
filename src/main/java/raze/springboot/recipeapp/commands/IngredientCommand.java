package raze.springboot.recipeapp.commands;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipe_id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uomCommand;


}
