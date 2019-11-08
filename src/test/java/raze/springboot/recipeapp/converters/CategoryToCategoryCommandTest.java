package raze.springboot.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raze.springboot.recipeapp.commands.CategoryCommand;
import raze.springboot.recipeapp.model.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {
    private static final Long CATEGORY_ID = 1L;
    private static final String DESCRIPTION = "my description";


    private CategoryToCategoryCommand categoryToCategoryCommand;


    @BeforeEach
    void setUp() {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    void nullObjectTest(){
        assertNull(categoryToCategoryCommand.convert(null));
    }


    @Test
    void emptyObjectTest(){
        assertNotNull(categoryToCategoryCommand.convert
                     (Category.builder().build()));
    }


    @Test
     void convert() {
        //given
        Category category = Category.builder().id(CATEGORY_ID)
                .description(DESCRIPTION).build();

        //when
        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);

        //then

        assertEquals(categoryCommand.getId(),CATEGORY_ID);
        assertEquals(categoryCommand.getDescription(),DESCRIPTION);







    }
}