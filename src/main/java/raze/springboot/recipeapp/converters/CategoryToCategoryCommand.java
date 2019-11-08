package raze.springboot.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import raze.springboot.recipeapp.commands.CategoryCommand;
import raze.springboot.recipeapp.model.Category;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {


    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category category) {
        if (category==null) return null;

       final CategoryCommand categoryCommand =
                CategoryCommand.builder()
                .id(category.getId())
                .description(category.getDescription())
                .build();

        return categoryCommand;
    }
}
