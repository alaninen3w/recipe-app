package raze.springboot.recipeapp.services.jpaimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import raze.springboot.recipeapp.commands.IngredientCommand;
import raze.springboot.recipeapp.commands.UnitOfMeasureCommand;
import raze.springboot.recipeapp.converters.IngredientCommandToIngredient;
import raze.springboot.recipeapp.converters.IngredientToIngredientCommand;
import raze.springboot.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import raze.springboot.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import raze.springboot.recipeapp.model.Ingredient;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.model.UnitOfMeasure;
import raze.springboot.recipeapp.repositories.IngredientRepository;
import raze.springboot.recipeapp.repositories.RecipeRepository;
import raze.springboot.recipeapp.repositories.UnitOfMeasureRepository;
import raze.springboot.recipeapp.services.IngredientService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientJpaServiceImplTest {
    private static final Long INGREDIENT_ID = 1L;
    private static final Long INGREDIENT_ID2 = 2L;
    private static final String DESCRIPTION = "my description";
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final Long UOM_ID = 1L;
    private static final Long RECIPE_ID = 1L;



    private IngredientService ingredientService;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private IngredientToIngredientCommand ingredientToIngredientCommand;

    private IngredientCommandToIngredient ingredientCommandToIngredient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientCommandToIngredient  =
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientToIngredientCommand =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

        ingredientService = new IngredientJpaServiceImpl(ingredientRepository
                , recipeRepository
                , unitOfMeasureRepository
                ,ingredientCommandToIngredient
                ,ingredientToIngredientCommand);
    }
    @Test
    void findByNullId(){
            assertNull(ingredientService.findById(null));
    }

    @Test
    void findById() {
        //given
        Ingredient ingredient =
                Ingredient.builder()
                .id(INGREDIENT_ID)
                .build();


        //when
        when(ingredientRepository.findById(anyLong()))
                .thenReturn(Optional.of(ingredient));

        Ingredient returnedIngredient = ingredientService
                .findById(INGREDIENT_ID);

        assertNotNull(returnedIngredient);
        assertEquals(returnedIngredient.getId(),INGREDIENT_ID);
    }


    @Test
    void findAll() {
        //given
        Ingredient ingredient1 =
                Ingredient.builder()
                .id(INGREDIENT_ID)
                .build();
        Ingredient ingredient2 =
                Ingredient.builder()
                .id(INGREDIENT_ID2)
                .build();
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.addAll(Arrays.asList(ingredient1,ingredient2));

        //when
        when(ingredientRepository.findAll())
                .thenReturn(ingredients);
        Set<Ingredient> returnedIngredients =
                ingredientService.findAll();


        //then
        assertEquals(returnedIngredients.size(),2);

        verify(ingredientRepository,times(1))
                .findAll();


    }







    @Test
    void deleteByRecipeAndIngredientId(){
        //given
        Long recipeDeleteId = 1L;
        Long ingredientDeleteId = 2L;

        Recipe recipe =
                Recipe.builder()
                .id(recipeDeleteId)
                .build();
        Ingredient ingredient =
                Ingredient.builder()
                .id(ingredientDeleteId)
                .build();
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);

        //when
        when(recipeRepository.findById(anyLong()))
                .thenReturn(Optional.of(recipe));
        ingredientService.deleteById(recipeDeleteId,ingredientDeleteId);

        //then
        assertEquals(recipe.getIngredients().size(),0);

        verify(recipeRepository , times(1))
                .findById(anyLong());
        verify(recipeRepository , times(1))
                .save(any());
        verify(ingredientRepository , times(1))
                .deleteById(anyLong());
    }


    @Test
    void saveNullIngredientCommand(){
        assertNull(ingredientService.saveIngredientCommand(null));
    }

    @Test
    void updateIngredientCommand() {
        //given
        IngredientCommand ingredientCommand =
                IngredientCommand.builder()
                        .id(INGREDIENT_ID)
                        .uomCommand(UnitOfMeasureCommand.builder().id(UOM_ID).build())
                        .amount(AMOUNT)
                        .description(DESCRIPTION)
                        .recipe_id(RECIPE_ID)
                        .build();
        Recipe recipe =
                Recipe.builder()
                        .id(RECIPE_ID)
                        .build();
        recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));

        UnitOfMeasure unitOfMeasure =
                UnitOfMeasure.builder()
                .id(UOM_ID)
                .build();

        //when
        when(recipeRepository.findById(anyLong()))
                .thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any()))
                .thenReturn(recipe);
        when(unitOfMeasureRepository.findById(anyLong()))
                .thenReturn(Optional.of(unitOfMeasure));


        IngredientCommand returnedIngredientCommand =
                ingredientService.saveIngredientCommand(ingredientCommand);


        //then
        assertEquals(returnedIngredientCommand.getId(),INGREDIENT_ID);
        assertEquals(returnedIngredientCommand.getUomCommand().getId(),UOM_ID);
        assertEquals(returnedIngredientCommand.getRecipe_id(),RECIPE_ID);
        assertEquals(returnedIngredientCommand.getAmount(),AMOUNT);
        assertEquals(returnedIngredientCommand.getDescription(),DESCRIPTION);

        verify(recipeRepository,times(1))
                .findById(anyLong());
        verify(recipeRepository,times(1))
                .save(any());
    }
    @Test
    void saveIngredientCommand(){
        //given
        IngredientCommand ingredientCommand =
                IngredientCommand.builder()
                        .id(INGREDIENT_ID)
                        .uomCommand(UnitOfMeasureCommand.builder().id(UOM_ID).build())
                        .amount(AMOUNT)
                        .description(DESCRIPTION)
                        .recipe_id(RECIPE_ID)
                        .build();
        Recipe recipe =
                Recipe.builder()
                        .id(RECIPE_ID)
                        .build();
        recipe.addIngredient(Ingredient.builder().id(INGREDIENT_ID2).build());

        UnitOfMeasure unitOfMeasure =
                UnitOfMeasure.builder()
                        .id(UOM_ID)
                        .build();

        //when
        when(recipeRepository.findById(anyLong()))
                .thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any()))
                .thenReturn(recipe);
        when(unitOfMeasureRepository.findById(anyLong()))
                .thenReturn(Optional.of(unitOfMeasure));


        IngredientCommand returnedIngredientCommand =
                ingredientService.saveIngredientCommand(ingredientCommand);


        //then
        assertEquals(returnedIngredientCommand.getId(),INGREDIENT_ID);
        assertEquals(returnedIngredientCommand.getUomCommand().getId(),UOM_ID);
        assertEquals(returnedIngredientCommand.getRecipe_id(),RECIPE_ID);
        assertEquals(returnedIngredientCommand.getAmount(),AMOUNT);
        assertEquals(returnedIngredientCommand.getDescription(),DESCRIPTION);

        verify(recipeRepository,times(1))
                .findById(anyLong());
        verify(recipeRepository,times(1))
                .save(any());
    }

    @Test
    void findByRecipeAndIngredientId() {
        //given
        Recipe recipe =
                Recipe.builder()
                .id(RECIPE_ID)
                .build();
        Ingredient ingredient =
                Ingredient.builder()
                .id(INGREDIENT_ID2)
                .recipe(recipe)
                .build();
        recipe.addIngredient(ingredient);

        //when
        when(recipeRepository.findById(anyLong()))
                .thenReturn(Optional.of(recipe));
        Ingredient returnedIngredient = ingredientService.findByRecipeAndIngredientId(RECIPE_ID,INGREDIENT_ID2);
        //then
        assertEquals(returnedIngredient.getId() , INGREDIENT_ID2);
        assertEquals(returnedIngredient.getRecipe().getId() , RECIPE_ID);

        verify(recipeRepository , times(1))
                .findById(anyLong());


    }
}