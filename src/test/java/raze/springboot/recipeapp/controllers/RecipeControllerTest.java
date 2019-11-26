package raze.springboot.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import raze.springboot.recipeapp.commands.RecipeCommand;
import raze.springboot.recipeapp.converters.RecipeCommandToRecipe;
import raze.springboot.recipeapp.converters.RecipeToRecipeCommand;
import raze.springboot.recipeapp.model.*;
import raze.springboot.recipeapp.services.RecipeService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {
    private static final Long RECIPE_ID = 1L;
    private static final Integer COOK_TIME = 2;
    private static final Integer PREP_TIME = 3;
    private static final String DESCRIPTION = "my recipe";
    private static final String DIRECTION = "my direction ";
    private static final Difficulty DIFFICULTY=Difficulty.HARD;
    private static final Integer SERVING = 4;
    private static final String SOURCE = "my source";
    private static final String URL = "my url";
    private static final Long CATEGORY_ID = 1L;
    private static final Long CATEGORY_ID2 = 2L;
    private static final Long INGREDIENT_ID = 1L;
    private static final Long INGREDIENT_ID2 = 2L;
    private static final Long NOTES_ID = 1L;
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final Long UOM_ID = 5L;
    private static final String UOM_DESCRIPTION = "uom description";
    private static final String RECIPE_NOTES = "recipe notes";

    private RecipeController recipeController;

    private MockMvc mockMvc;
    @Mock
    private RecipeService recipeService;
    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService
                ,recipeCommandToRecipe,recipeToRecipeCommand);

        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void getRecipe() throws Exception {
        //given
        Recipe recipe = Recipe.builder()
                .id(RECIPE_ID)
                .description(DESCRIPTION)
                .directions(DIRECTION)
                .difficulty(DIFFICULTY)
                .servings(SERVING)
                .source(SOURCE)
                .cookTime(COOK_TIME)
                .prepTime(PREP_TIME)
                .url(URL)
                .build();
        Ingredient ingredient = Ingredient.builder()
                .id(INGREDIENT_ID)
                .amount(AMOUNT)
                .description(DESCRIPTION)
                .build();

        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
                .id(UOM_ID)
                .description(UOM_DESCRIPTION)
                .build();
        Notes notes = Notes.builder()
                .id(NOTES_ID)
                .recipeNotes(RECIPE_NOTES)
                .build();


        recipe.addIngredient(ingredient);
        recipe.setNotes(notes);
        ingredient.setRecipe(recipe);
        ingredient.setUom(unitOfMeasure);
        notes.setRecipe(recipe);

        //when
        when(recipeService.findById(anyLong()))
                .thenReturn(recipe);
        Recipe returnedRecipe = recipeService.findById(RECIPE_ID);

        //then
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));




    }
    @Test
    void getNewRecipeForm() throws Exception {
        //given
        RecipeCommand recipeCommand = RecipeCommand.builder().build();
        //when
        //then
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipecommand"));

    }

    @Test
    void saveNewRecipeForm() throws Exception {
        //given
        RecipeCommand recipeCommand =
                RecipeCommand.builder()
                .id(RECIPE_ID)
                .description(DESCRIPTION)
                .build();

        ArgumentCaptor<RecipeCommand> recipeCommandArgumentCaptor =
                ArgumentCaptor.forClass(RecipeCommand.class);

        //when

        when(recipeService.saveRecipeCommand(any()))
                .thenReturn(recipeCommand);


        //then
        mockMvc.perform(post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", String.valueOf(RECIPE_ID))
            .param("description" , DESCRIPTION))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/show"));

        verify(recipeService , times(1))
                .saveRecipeCommand(recipeCommandArgumentCaptor.capture());

        RecipeCommand capturedRecipeCommand = recipeCommandArgumentCaptor.getValue();

        assertEquals(capturedRecipeCommand.getId() , RECIPE_ID);
        assertEquals(capturedRecipeCommand.getDescription() , DESCRIPTION);

    }
    @Test
    void updateRecipeForm() throws Exception{
        //given
        Recipe recipe =
                Recipe.builder()
                .id(RECIPE_ID)
                .description(DESCRIPTION)
                .build();
        //when
        when(recipeService.findById(anyLong()))
                .thenReturn(recipe);

        //then
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attribute("recipecommand"
                        , recipeToRecipeCommand.convert(recipe)));
        verify(recipeService  , times(1))
                .findById(anyLong());
    }

    @Test
    void deleteRecipe() throws Exception {
        //given
        Long recipeDeleteId = 1L;
        //when

        //then
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService , times(1))
                .deleteById(anyLong());


    }
}