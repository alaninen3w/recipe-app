package raze.springboot.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import raze.springboot.recipeapp.commands.IngredientCommand;
import raze.springboot.recipeapp.commands.UnitOfMeasureCommand;
import raze.springboot.recipeapp.model.Ingredient;
import raze.springboot.recipeapp.model.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IngredientCommandToIngredientTest {
    private final static Long INGREDIENT_ID = 1L;
    private final static Long RECIPE_ID = 3L;
    private final static String DESCRIPTION = "description";
    private final static BigDecimal AMOUNT = new BigDecimal("5");
    private final static Long UOM_ID = 2L;


    private IngredientCommandToIngredient ingredientCommandToIngredient;
    @Mock
    private UnitOfMeasureCommandToUnitOfMeasure uomCommandConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientCommandToIngredient = new IngredientCommandToIngredient(uomCommandConverter);
    }
    @Test
    void nullObjectTest(){
        assertNull(ingredientCommandToIngredient.convert(null));
    }
    @Test
    void emptyObjectTest(){
        //given
        IngredientCommand ingredientCommand =
                IngredientCommand.builder()
                .build();
        //when
        when(uomCommandConverter.convert(any(UnitOfMeasureCommand.class)))
                .thenReturn(UnitOfMeasure.builder().build());
        Ingredient returnedIngredient =
                ingredientCommandToIngredient.convert(ingredientCommand);
        //then
        assertNotNull(returnedIngredient);


    }

    @Test
    void convert() {
        //given
        IngredientCommand ingredientCommand =
                IngredientCommand.builder()
                .id(INGREDIENT_ID)
                .recipe_id(RECIPE_ID)
                .description(DESCRIPTION)
                .amount(AMOUNT)
                .uomCommand(UnitOfMeasureCommand.builder().id(UOM_ID).build())
                .build();

        //when
        when(uomCommandConverter.convert(any(UnitOfMeasureCommand.class)))
                .thenReturn(UnitOfMeasure.builder().id(UOM_ID).build());

        Ingredient returnedIngredient =
                ingredientCommandToIngredient.convert(ingredientCommand);


        //then
        assertNotNull(returnedIngredient);
        assertEquals(returnedIngredient.getId(),INGREDIENT_ID);
        assertEquals(returnedIngredient.getRecipe().getId(),RECIPE_ID);
        assertEquals(returnedIngredient.getDescription(),DESCRIPTION);
        assertEquals(returnedIngredient.getAmount(),AMOUNT);
        assertEquals(returnedIngredient.getUom().getId(),UOM_ID);

        verify(uomCommandConverter,times(1))
                .convert(any(UnitOfMeasureCommand.class));

    }
}