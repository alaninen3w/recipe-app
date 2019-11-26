package raze.springboot.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.services.RecipeService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IndexControllerTest {
    private final static Long RECIPE_ID = 1L;
    private final static Long RECIPE_ID2 = 2L;


    private IndexController indexController;

     private MockMvc mockMvc;
    @Mock
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();


    }

    @Test
    void indexPage() throws Exception {
        //given
        Recipe recipe1 =
                Recipe.builder()
                .id(RECIPE_ID)
                .build();
        Recipe recipe2 =
                Recipe.builder()
                .id(RECIPE_ID2)
                .build();
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe1);
        recipeSet.add(recipe2);

        //when
        when(recipeService.findAll())
                .thenReturn(recipeSet);


        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("recipes",recipeService.findAll()));

    }
}