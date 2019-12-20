package raze.springboot.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import raze.springboot.recipeapp.commands.IngredientCommand;
import raze.springboot.recipeapp.commands.RecipeCommand;
import raze.springboot.recipeapp.commands.UnitOfMeasureCommand;
import raze.springboot.recipeapp.converters.IngredientCommandToIngredient;
import raze.springboot.recipeapp.converters.IngredientToIngredientCommand;
import raze.springboot.recipeapp.converters.RecipeCommandToRecipe;
import raze.springboot.recipeapp.converters.RecipeToRecipeCommand;
import raze.springboot.recipeapp.model.Ingredient;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.services.IngredientService;
import raze.springboot.recipeapp.services.RecipeService;
import raze.springboot.recipeapp.services.UnitOfMeasureService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {
    private static final Long INGREDIENT_ID = 1L;
    private static final Long INGREDIENT_ID2 = 2L;
    private static final String DESCRIPTION = "my description";
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final Long UOM_ID = 1L;
    private static final Long RECIPE_ID = 1L;
    private static final Long RECIPE_ID2 = 3L;

    private IngredientController ingredientController;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private RecipeService recipeService ;
    @Mock
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    @Mock
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    private UnitOfMeasureService unitOfMeasureService;


    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(recipeService,ingredientService
                ,recipeToRecipeCommand,recipeCommandToRecipe, ingredientToIngredientCommand, unitOfMeasureService);

        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

    }

    @Test
    void getRecipeIngredientList() throws Exception {
        //given
        Recipe recipe =
                Recipe.builder()
                .id(RECIPE_ID)
                .build();

        RecipeCommand recipeCommand =
                RecipeCommand.builder()
                .id(RECIPE_ID)
                .build();

        Ingredient ingredient1 =
                Ingredient.builder()
                .id(INGREDIENT_ID)
                .recipe(recipe)
                .build();
        Ingredient ingredient2 =
                Ingredient.builder()
                .id(INGREDIENT_ID2)
                .recipe(recipe)
                .build();
        Set<Ingredient> ingredientSet = new HashSet<>();
        ingredientSet.add(ingredient1);
        ingredientSet.add(ingredient2);
        recipe.setIngredients(ingredientSet);
        //when
       when(recipeService.findById(anyLong()))
               .thenReturn(recipe);
       when(recipeToRecipeCommand.convert(any()))
               .thenReturn(recipeCommand);

       //then
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attribute("recipecommand" , recipeCommand));

        verify(recipeService , times(1))
                .findById(anyLong());
        verify(recipeToRecipeCommand , times(1))
                .convert(recipe);


    }
    @Test
    void getRecipeIngredient() throws Exception {
        //given
        Recipe recipe =
                Recipe.builder()
                .id(RECIPE_ID)
                .build();
        Ingredient ingredient =
                Ingredient.builder()
                .id(INGREDIENT_ID)
                .recipe(recipe)
                .build();
        RecipeCommand recipeCommand =
                RecipeCommand.builder()
                .id(RECIPE_ID)
                .build();
        IngredientCommand ingredientCommand =
                IngredientCommand.builder()
                .id(INGREDIENT_ID)
                .build();
        recipe.addIngredient(ingredient);

        recipeCommand.getIngredients().add(ingredientCommand);
        ingredientCommand.setRecipe_id(RECIPE_ID);



        //when
        when(ingredientService.findByRecipeAndIngredientId(RECIPE_ID , INGREDIENT_ID))
                .thenReturn(ingredient);
        when(ingredientToIngredientCommand.convert(any()))
                .thenReturn(ingredientCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attribute("ingredient" , ingredientCommand));

        verify(ingredientService , times(1))
                .findByRecipeAndIngredientId(anyLong(),anyLong());


    }

    @Test
    void newIngredientForm() throws Exception {
        //given
        Recipe recipe =
                Recipe.builder()
                .id(RECIPE_ID2)
                .build();
        IngredientCommand ingredientCommand =
                IngredientCommand.builder()
                .recipe_id(RECIPE_ID2)
                .build();

        //when
        when(recipeService.findById(anyLong()))
                .thenReturn(recipe);

        //
        mockMvc.perform(get("/recipe/3/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredientcommand" ))
                .andExpect(model().attributeExists("uomList"));


        verify(recipeService , times(1))
                .findById(anyLong());
        verify(unitOfMeasureService , times(1))
                .findAll();

    }


    @Test
    void saveIngredientCommandToRecipe() throws Exception {
        //given
        RecipeCommand recipeCommand =
                RecipeCommand.builder()
                .id(RECIPE_ID)
                .build();
        IngredientCommand ingredientCommand =
                IngredientCommand.builder()
                .id(INGREDIENT_ID)
                .description(DESCRIPTION)
                .recipe_id(RECIPE_ID)
                .build();
        UnitOfMeasureCommand unitOfMeasureCommand =
                UnitOfMeasureCommand.builder()
                .id(UOM_ID)
                .build();
        ingredientCommand.setUomCommand(unitOfMeasureCommand);
        recipeCommand.getIngredients().add(ingredientCommand);


        //when
       when(ingredientService.saveIngredientCommand(any()))
               .thenReturn(ingredientCommand);

        //then
        mockMvc.perform(post("/recipe/1/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id" , "")
                .param("description" , DESCRIPTION))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/1/show"));
        verify(ingredientService , times(1))
                .saveIngredientCommand(any());

    }

    @Test
    void deleteByRecipeAndIngredientId() throws Exception {
        //given
        RecipeCommand recipeCommand =
                RecipeCommand.builder()
                        .id(RECIPE_ID2)
                        .build();
        IngredientCommand ingredientCommand =
                IngredientCommand.builder()
                        .id(INGREDIENT_ID2)
                        .recipe_id(RECIPE_ID2)
                        .build();
        recipeCommand.getIngredients().add(ingredientCommand);

        //when
        //then
        mockMvc.perform(get("/recipe/2/ingredient/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

       verify(ingredientService ,times(1))
               .deleteById(anyLong(),anyLong());


    }

    @Test
    void updateRecipeIngredient() throws Exception {
        //given
        Ingredient ingredient =
                Ingredient.builder()
                .id(INGREDIENT_ID)
                .build();
        Recipe recipe =
                Recipe.builder()
                .id(RECIPE_ID)
                .build();
        IngredientCommand ingredientCommand =
                IngredientCommand.builder()
                .id(INGREDIENT_ID)
                .build();

        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        //when
        when(ingredientService.findByRecipeAndIngredientId(anyLong(),anyLong()))
                .thenReturn(ingredient);
        when(ingredientToIngredientCommand.convert(any()))
                .thenReturn(ingredientCommand);
        //the
        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredientcommand"))
                .andExpect(model().attributeExists("uomList"));

    }

}