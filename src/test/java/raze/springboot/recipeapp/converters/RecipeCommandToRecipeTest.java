package raze.springboot.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import raze.springboot.recipeapp.commands.CategoryCommand;
import raze.springboot.recipeapp.commands.IngredientCommand;
import raze.springboot.recipeapp.commands.NotesCommand;
import raze.springboot.recipeapp.commands.RecipeCommand;
import raze.springboot.recipeapp.model.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecipeCommandToRecipeTest {
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





    @InjectMocks
    private RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    private NotesCommandToNotes notesCommandToNotes;
    @Mock
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    @Mock
    private CategoryCommandToCategory categoryCommandToCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void nullObjectTest(){
        assertNull(recipeCommandToRecipe.convert(null));
    }
    @Test
    void emptyObjectTest(){
        //given
        RecipeCommand recipeCommand =
                RecipeCommand.builder()
                .build();
        Notes notes =
                Notes.builder()
                .build();
        Ingredient ingredient =
                Ingredient.builder()
                .build();
        Category category =
                Category.builder()
                .build();
        //when
        when(notesCommandToNotes.convert(any(NotesCommand.class)))
                .thenReturn(notes);
        when(ingredientCommandToIngredient.convert(any(IngredientCommand.class)))
                .thenReturn(ingredient);
        when(categoryCommandToCategory.convert(any(CategoryCommand.class)))
                .thenReturn(category);

        Recipe returnedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        //then
        assertNotNull(returnedRecipe);
       

    }

    @Test
    void convert() {
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
        when(notesCommandToNotes.convert(any(NotesCommand.class)))
                .thenReturn(Notes.builder().id(NOTES_ID).build());
        when(ingredientCommandToIngredient.convert(any(IngredientCommand.class)))
                .thenReturn(Ingredient.builder().id(INGREDIENT_ID).build())
                .thenReturn(Ingredient.builder().id(INGREDIENT_ID2).build());
        when(categoryCommandToCategory.convert(any(CategoryCommand.class)))
                .thenReturn(Category.builder().id(CATEGORY_ID).build())
                .thenReturn(Category.builder().id(CATEGORY_ID2).build());

        Recipe returnedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        //then
        assertNotNull(returnedRecipe);
        assertEquals(returnedRecipe.getId(),RECIPE_ID);
        assertEquals(returnedRecipe.getDescription(),DESCRIPTION);
        assertEquals(returnedRecipe.getPrepTime(),PREP_TIME);
        assertEquals(returnedRecipe.getCookTime(),COOK_TIME);
        assertEquals(returnedRecipe.getServings(),SERVING);
        assertEquals(returnedRecipe.getSource(),SOURCE);
        assertEquals(returnedRecipe.getUrl(),URL);
        assertEquals(returnedRecipe.getDirections(),DIRECTION);
        assertEquals(returnedRecipe.getDifficulty(),DIFFICULTY);
        assertEquals(returnedRecipe.getNotes().getId(),NOTES_ID);
        assertEquals(returnedRecipe.getIngredients().size(),2);
        assertEquals(returnedRecipe.getCategories().size(),2);

        verify(notesCommandToNotes,times(1))
                .convert(any(NotesCommand.class));
        verify(ingredientCommandToIngredient,times(2))
                .convert(any(IngredientCommand.class));
        verify(categoryCommandToCategory,times(2))
                .convert(any(CategoryCommand.class));




    }
}