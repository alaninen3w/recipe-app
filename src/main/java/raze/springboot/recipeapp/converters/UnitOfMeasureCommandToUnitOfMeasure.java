package raze.springboot.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import raze.springboot.recipeapp.commands.UnitOfMeasureCommand;
import raze.springboot.recipeapp.model.UnitOfMeasure;
@Component
public class UnitOfMeasureCommandToUnitOfMeasure
        implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand unitOfMeasureCommand) {
        if(unitOfMeasureCommand == null) return null;

       final UnitOfMeasure unitOfMeasure =
                UnitOfMeasure.builder()
                .id(unitOfMeasureCommand.getId())
                .description(unitOfMeasureCommand.getDescription())
                .build();


        return unitOfMeasure;
    }
}
