package raze.springboot.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import raze.springboot.recipeapp.commands.CategoryCommand;
import raze.springboot.recipeapp.model.Category;
@Component
public class CategoryCommandToCategory
        implements Converter<CategoryCommand, Category> {
    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if(categoryCommand == null ) return null;

       final Category category =
                Category.builder()
                .id(categoryCommand.getId())
                .description(categoryCommand.getDescription())
                .build();

        return category;
    }
}
