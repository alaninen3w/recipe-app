package raze.springboot.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import raze.springboot.recipeapp.commands.UnitOfMeasureCommand;
import raze.springboot.recipeapp.model.UnitOfMeasure;
@Component
public class UnitOfMeasureToUnitOfMeasureCommand
        implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {


    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure)
    {
        if(unitOfMeasure == null) return  null;

        UnitOfMeasureCommand unitOfMeasureCommand =
                UnitOfMeasureCommand.builder()
                .id(unitOfMeasure.getId())
                .description(unitOfMeasure.getDescription())
                .build();


        return unitOfMeasureCommand;
    }
}
