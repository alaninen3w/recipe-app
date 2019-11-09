package raze.springboot.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raze.springboot.recipeapp.commands.CategoryCommand;
import raze.springboot.recipeapp.model.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {
    private final static Long CATEGORY_ID = 1L;
    private final static String DESCRIPTION = "description";


    private CategoryCommandToCategory categoryCommandToCategory;

    @BeforeEach
    void setUp() {
        categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    void nullObjectTest(){
        assertNull(categoryCommandToCategory.convert(null));
    }
    @Test
    void emptyObjectTest(){
        assertNotNull(categoryCommandToCategory
                .convert(CategoryCommand.builder().build()));
    }

    @Test
    void convert() {
        //given
        CategoryCommand categoryCommand =
                CategoryCommand.builder()
                .id(CATEGORY_ID)
                .description(DESCRIPTION)
                .build();

        //when
        Category returnedCategory =
                categoryCommandToCategory.convert(categoryCommand);

        //then
        assertNotNull(returnedCategory);
        assertEquals(returnedCategory.getId(),CATEGORY_ID);
        assertEquals(returnedCategory.getDescription(),DESCRIPTION);


    }
}