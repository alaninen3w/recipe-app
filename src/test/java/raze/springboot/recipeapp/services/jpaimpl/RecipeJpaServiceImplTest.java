package raze.springboot.recipeapp.services.jpaimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.repositories.RecipeRepository;
import raze.springboot.recipeapp.services.RecipeService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


class RecipeJpaServiceImplTest {
    @InjectMocks
    private  RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;


    @BeforeEach
    void setUp() {
        recipeService =new RecipeJpaServiceImpl();
    }

    @Test
    void findByIdRecipeTest() {
        //given
        Recipe recipe=Recipe.builder().build();
        recipe.setId(2L);
        Optional<Recipe> optionalRecipe=Optional.of(recipe);
        //when
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        Recipe recipeReturned = recipeService.findById(2L);

        //then
        assertNotNull(recipeReturned);
        assertEquals(recipeReturned.getId(),2L);
        verify(recipeRepository,times(1)).findById(2L);
        verify(recipeRepository,never()).findAll();


    }

    @Test
    void findAllRecipeTest() {
        //given
        Set<Recipe> recipeSet=new HashSet<>();
        Recipe recipe1=Recipe.builder().id(1L).build();
        Recipe recipe2=Recipe.builder().id(2L).build();
        recipeSet.addAll(Arrays.asList(recipe1,recipe2));

        //when
        when(recipeRepository.findAll()).thenReturn(recipeSet);
        Set<Recipe> returnedRecipes=recipeService.findAll();

        //then
        assertEquals(returnedRecipes.size(),2);
        verify(recipeRepository,times(1)).findAll();
        verify(recipeRepository,never()).findById(anyLong());



    }

    @Test
    void saveRecipeTest() {
        //given
        Recipe recipe=Recipe.builder().id(1L).build();
        ArgumentCaptor<Recipe> recipeArgumentCaptor=ArgumentCaptor.forClass(Recipe.class);

        //when
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        Recipe returnedRecipe=recipeService.save(recipe);

        //then
        assertEquals(returnedRecipe,recipe);
        verify(recipeRepository,times(1)).save(recipeArgumentCaptor.capture());
        Recipe capturedRecipe=recipeArgumentCaptor.getValue();
        assertEquals(returnedRecipe,capturedRecipe);


    }

    @Test
    void deleteByIdRecipeTest() {
        //given
        Long idToDelete=1L;

        //when
        recipeService.deleteById(idToDelete);

        //then
        verify(recipeRepository,times(1)).deleteById(anyLong());
        verify(recipeRepository,never()).deleteAll();


    }

    @Test
    void delete() {
    }
}