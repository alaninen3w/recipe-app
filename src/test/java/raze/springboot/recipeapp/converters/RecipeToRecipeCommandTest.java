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

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecipeToRecipeCommandTest {
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
    private RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    @Mock
    private CategoryToCategoryCommand categoryToCategoryCommand;
    @Mock
    private NotesToNotesCommand notesToNotesCommand;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }


    @Test
    void convertNullObject(){
        assertNull(recipeToRecipeCommand.convert(null));
    }
    @Test
    void convertEmptyObject(){
        //given

        //when
        when(notesToNotesCommand.convert(any(Notes.class)))
                .thenReturn(NotesCommand.builder()
                .id(NOTES_ID)
                .build());
        when(ingredientToIngredientCommand.convert(any(Ingredient.class)))
                .thenReturn(IngredientCommand.builder()
                .build());
        when(categoryToCategoryCommand.convert(any(Category.class)))
                .thenReturn(CategoryCommand.builder()
                .build());

        //then
        assertNotNull(recipeToRecipeCommand
                .convert(Recipe.builder().build()));
    }


    @Test
    void convert() {
        //given
        Recipe recipe=Recipe.builder()
                .id(RECIPE_ID)
                .description(DESCRIPTION)
                .prepTime(PREP_TIME)
                .cookTime(COOK_TIME)
                .difficulty(DIFFICULTY)
                .directions(DIRECTION)
                .source(SOURCE)
                .servings(SERVING)
                .url(URL)
                .notes(Notes.builder().id(NOTES_ID).build())
                .build();

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(Ingredient.builder().id(INGREDIENT_ID).build());
        ingredients.add(Ingredient.builder().id(INGREDIENT_ID2).build());

        Set<Category> categories = new HashSet<>();
        categories.add(Category.builder().id(CATEGORY_ID).build());
        categories.add(Category.builder().id(CATEGORY_ID2).build());

        recipe.setIngredients(ingredients);
        recipe.setCategories(categories);

        //when
        when(notesToNotesCommand.convert(any(Notes.class)))
                .thenReturn(NotesCommand.builder().id(NOTES_ID).build());
        when(ingredientToIngredientCommand.convert(any(Ingredient.class)))
                .thenReturn(IngredientCommand.builder().id(INGREDIENT_ID).build())
                .thenReturn(IngredientCommand.builder().id(INGREDIENT_ID2).build());
        when(categoryToCategoryCommand.convert(any(Category.class)))
                .thenReturn(CategoryCommand.builder().id(CATEGORY_ID).build())
                .thenReturn(CategoryCommand.builder().id(CATEGORY_ID2).build());

        RecipeCommand returnedRecipeCommand=recipeToRecipeCommand.convert(recipe);

        //then
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

        verify(notesToNotesCommand,times(1))
                .convert(any());

        verify(ingredientToIngredientCommand , times(2))
                .convert(any());

        verify(categoryToCategoryCommand,times(2))
                .convert(any());




    }
}