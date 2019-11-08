package raze.springboot.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import raze.springboot.recipeapp.commands.IngredientCommand;
import raze.springboot.recipeapp.commands.UnitOfMeasureCommand;
import raze.springboot.recipeapp.model.Ingredient;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.model.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class IngredientToIngredientCommandTest {
    private static final Long INGREDIENT_ID = 1L;
    private static final String DESCRIPTION = "my description";
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final Long UOM_ID = 1L;
    private static final Long RECIPE_ID = 1L;
    private static final Recipe RECIPE = Recipe.builder().build();


    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    private UnitOfMeasureToUnitOfMeasureCommand uomConverter;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientToIngredientCommand =
                new IngredientToIngredientCommand(uomConverter);
    }
    @Test
    void nullObject(){
        assertNull
                (ingredientToIngredientCommand.convert(null));

    }
    @Test
    void emptyObject(){
        //given

        //when
        when(uomConverter.convert(any(UnitOfMeasure.class)))
                .thenReturn(UnitOfMeasureCommand.builder().build());
        //then
        assertNotNull
                (ingredientToIngredientCommand.convert
                        (Ingredient.builder().id(INGREDIENT_ID).build()));
    }

    @Test
    void convertWithNullUOM() {
        //given
        Ingredient ingredient = Ingredient.builder()
                .id(INGREDIENT_ID)
                .description(DESCRIPTION)
                .amount(AMOUNT)
                .recipe(RECIPE)
                .uom(null)
                .build();
        //when
        IngredientCommand ingredientCommand =
                ingredientToIngredientCommand.convert(ingredient);

        //then
        assertEquals(ingredientCommand.getId(),INGREDIENT_ID);
        assertEquals(ingredientCommand.getDescription(),DESCRIPTION);
        assertEquals(ingredientCommand.getAmount(),AMOUNT);
        assertNull(ingredientCommand.getUomCommand());
    }

    @Test
    void convert(){
        //given
        Ingredient ingredient = Ingredient.builder()
                .id(INGREDIENT_ID)
                .description(DESCRIPTION)
                .amount(AMOUNT)
                .recipe(RECIPE)
                .uom(UnitOfMeasure.builder().id(UOM_ID).build())
                .build();
        //when
        when(uomConverter.convert(any(UnitOfMeasure.class)))
                .thenReturn(UnitOfMeasureCommand.builder()
                .id(UOM_ID).build());

        IngredientCommand ingredientCommand =
                ingredientToIngredientCommand.convert(ingredient);


        //then
        assertEquals(ingredientCommand.getId(),INGREDIENT_ID);
        assertEquals(ingredientCommand.getDescription(),DESCRIPTION);
        assertEquals(ingredientCommand.getAmount(),AMOUNT);
        assertEquals(ingredientCommand.getUomCommand().getId(),UOM_ID);
    }


}