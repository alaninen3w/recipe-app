package raze.springboot.recipeapp.services.jpaimpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import raze.springboot.recipeapp.commands.CategoryCommand;
import raze.springboot.recipeapp.commands.IngredientCommand;
import raze.springboot.recipeapp.commands.NotesCommand;
import raze.springboot.recipeapp.commands.RecipeCommand;
import raze.springboot.recipeapp.converters.RecipeCommandToRecipe;
import raze.springboot.recipeapp.converters.RecipeToRecipeCommand;
import raze.springboot.recipeapp.model.Difficulty;
import raze.springboot.recipeapp.repositories.RecipeRepository;
import raze.springboot.recipeapp.services.RecipeService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeJpaServiceImplIT {
    private static final Long RECIPE_ID = 1L;
    private static final Integer COOK_TIME = 2;
    private static final Integer PREP_TIME = 3;
    private static final String DESCRIPTION = "my recipe";
    private static final String DIRECTION = "my direction ";
    private static final Difficulty DIFFICULTY=Difficulty.HARD;
    private static final Integer SERVING =4;
    private static final String SOURCE = "my source";
    private static final String URL = "my url";
    private static final Long CATEGORY_ID = 1L;
    private static final Long CATEGORY_ID2 = 2L;
    private static final Long INGREDIENT_ID = 1L;
    private static final Long INGREDIENT_ID2 = 2L;
    private static final Long NOTES_ID = 1L;


    private RecipeRepository recipeRepository;
    private RecipeCommandToRecipe recipeCommandToRecipe;
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    public RecipeJpaServiceImplIT(RecipeRepository recipeRepository
            , RecipeCommandToRecipe recipeCommandToRecipe
            , RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    private RecipeService recipeService;

    @BeforeEach
    void setUp() {


        recipeService =new RecipeJpaServiceImpl(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
    }



    @Test
    void saveNullRecipeCommand(){
        assertNull(recipeService.saveRecipeCommand(null));

    }
    @Test
    void saveEmptyRecipeCommand(){
      assertNotNull(recipeService
              .saveRecipeCommand(RecipeCommand.builder().build()));
    }


//
//    @Test
//    void saveRecipeCommandWithNullId(){
//
//        //given
//        RecipeCommand recipeCommand =
//                RecipeCommand.builder()
//                        .description(DESCRIPTION)
//                        .directions(DIRECTION)
//                        .difficulty(DIFFICULTY)
//                        .serving(SERVING)
//                        .source(SOURCE)
//                        .cookTime(COOK_TIME)
//                        .prepTime(PREP_TIME)
//                        .url(URL)
//                        .notes(NotesCommand.builder().id(NOTES_ID).build())
//                        .build();
//        IngredientCommand ingredientCommand1=
//                IngredientCommand.builder()
//                        .id(INGREDIENT_ID)
//                        .build();
//        IngredientCommand ingredientCommand2=
//                IngredientCommand.builder()
//                        .id(INGREDIENT_ID2)
//                        .build();
//        Set<IngredientCommand> ingredientCommands =
//                new HashSet<>();
//        ingredientCommands
//                .addAll(Arrays.asList(ingredientCommand1,ingredientCommand2));
//
//        CategoryCommand categoryCommand1 =
//                CategoryCommand.builder()
//                        .id(CATEGORY_ID)
//                        .build();
//        CategoryCommand categoryCommand2 =
//                CategoryCommand.builder()
//                        .id(CATEGORY_ID2)
//                        .build();
//
//        Set<CategoryCommand> categoryCommands =
//                new HashSet<>();
//        categoryCommands
//                .addAll(Arrays.asList(categoryCommand1,categoryCommand2));
//
//        recipeCommand.setIngredients(ingredientCommands);
//        recipeCommand.setCategories(categoryCommands);
//
//
//
//
//        //when
//
//
//
//        RecipeCommand returnedRecipeCommand =
//                recipeService.saveRecipeCommand(recipeCommand);
//
//
//        //then
//        assertNotNull(returnedRecipeCommand);
//        assertEquals(returnedRecipeCommand.getId(),null);
//        assertEquals(returnedRecipeCommand.getDescription(),DESCRIPTION);
//        assertEquals(returnedRecipeCommand.getCookTime(),COOK_TIME);
//        assertEquals(returnedRecipeCommand.getPrepTime(),PREP_TIME);
//        assertEquals(returnedRecipeCommand.getDirections(),DIRECTION);
//        assertEquals(returnedRecipeCommand.getDifficulty(),DIFFICULTY);
//        assertEquals(returnedRecipeCommand.getServing(),SERVING);
//        assertEquals(returnedRecipeCommand.getSource(),SOURCE);
//        assertEquals(returnedRecipeCommand.getUrl(),URL);
//        assertEquals(returnedRecipeCommand.getNotes().getId(),NOTES_ID);
//        assertEquals(returnedRecipeCommand.getIngredients().size(),2);
//        assertEquals(returnedRecipeCommand.getCategories().size(),2);
//
//    }
//





    @Test
    void saveRecipeCommandWithId(){

        //given
        RecipeCommand recipeCommand =
                RecipeCommand.builder()
                        .id(RECIPE_ID)
                        .description(DESCRIPTION)
                        .directions(DIRECTION)
                        .difficulty(DIFFICULTY)
                        .serving(SERVING)
                        .source(SOURCE)
                        .cookTime(COOK_TIME)
                        .prepTime(PREP_TIME)
                        .url(URL)
                        .notes(NotesCommand.builder().id(NOTES_ID).build())
                        .build();
        IngredientCommand ingredientCommand1=
                IngredientCommand.builder()
                        .id(INGREDIENT_ID)
                        .build();
        IngredientCommand ingredientCommand2=
                IngredientCommand.builder()
                        .id(INGREDIENT_ID2)
                        .build();
        Set<IngredientCommand> ingredientCommands =
                new HashSet<>();
        ingredientCommands
                .addAll(Arrays.asList(ingredientCommand1,ingredientCommand2));

        CategoryCommand categoryCommand1 =
                CategoryCommand.builder()
                        .id(CATEGORY_ID)
                        .build();
        CategoryCommand categoryCommand2 =
                CategoryCommand.builder()
                        .id(CATEGORY_ID2)
                        .build();

        Set<CategoryCommand> categoryCommands =
                new HashSet<>();
        categoryCommands
                .addAll(Arrays.asList(categoryCommand1,categoryCommand2));

        recipeCommand.setIngredients(ingredientCommands);
        recipeCommand.setCategories(categoryCommands);




        //when



        RecipeCommand returnedRecipeCommand =
                recipeService.saveRecipeCommand(recipeCommand);


        //then
        assertNotNull(returnedRecipeCommand);
        assertEquals(returnedRecipeCommand.getId(),RECIPE_ID);
        assertEquals(returnedRecipeCommand.getDescription(),DESCRIPTION);
        assertEquals(returnedRecipeCommand.getCookTime(),COOK_TIME);
        assertEquals(returnedRecipeCommand.getPrepTime(),PREP_TIME);
        assertEquals(returnedRecipeCommand.getDirections(),DIRECTION);
        assertEquals(returnedRecipeCommand.getDifficulty(),DIFFICULTY);
        assertEquals(returnedRecipeCommand.getServing(),SERVING);
        assertEquals(returnedRecipeCommand.getSource(),SOURCE);
        assertEquals(returnedRecipeCommand.getUrl(),URL);
        assertEquals(returnedRecipeCommand.getNotes().getId(),NOTES_ID);
        assertEquals(returnedRecipeCommand.getIngredients().size(),2);
        assertEquals(returnedRecipeCommand.getCategories().size(),2);

    }
}
