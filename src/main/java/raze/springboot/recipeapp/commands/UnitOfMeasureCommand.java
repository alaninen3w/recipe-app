package raze.springboot.recipeapp.commands;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UnitOfMeasureCommand {

    private Long id;
    private String description;
}
