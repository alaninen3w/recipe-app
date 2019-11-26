package raze.springboot.recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import raze.springboot.recipeapp.commands.RecipeCommand;
import raze.springboot.recipeapp.converters.RecipeCommandToRecipe;
import raze.springboot.recipeapp.converters.RecipeToRecipeCommand;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.services.ImageService;
import raze.springboot.recipeapp.services.RecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {
    private final static Long RECIPE_ID = 1L;
    private final static String DESCRIPTION = "recipe description";
    private  ImageController imageController;
    @Mock
    private ImageService imageService;
    @Mock
    private RecipeService recipeService;
    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;



    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(imageService,recipeService
                ,recipeToRecipeCommand,recipeCommandToRecipe);

        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

   @Test
   void getImageForm() throws Exception {
        //given
       RecipeCommand recipeCommand =
               RecipeCommand.builder()
               .id(RECIPE_ID)
               .description(DESCRIPTION)
               .build();

       Recipe recipe =
               Recipe.builder()
               .id(RECIPE_ID)
               .description(DESCRIPTION)
               .build();
       //when
       when(recipeService.findById(anyLong()))
               .thenReturn(recipe);
       when(recipeToRecipeCommand.convert(any()))
               .thenReturn(recipeCommand);

       //then

       mockMvc.perform(get("/recipe/1/image"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/imageuploadform"))
               .andExpect(model().attribute("recipecommand" , recipeCommand));

       verify(recipeService,times(1))
               .findById(anyLong());

   }
   @Test
   void loadImage() throws Exception {
        //given
        MockMultipartFile imageFile = new MockMultipartFile("imageFile"
                , "My File".getBytes());

        //when



       //then
       mockMvc.perform(multipart("/recipe/1/image")
               .file(imageFile))
               .andExpect(status().is3xxRedirection())
               .andExpect(header().string("Location" , "/recipe/1/show"));
       verify(imageService , times(1))
               .saveImageFile(anyLong() , any());

   }

   @Test
   void renderImageFileFromDB() throws Exception {
        //given
       RecipeCommand recipeCommand =
               RecipeCommand.builder()
               .id(RECIPE_ID)
               .build();
       Recipe recipe =
               Recipe.builder()
               .id(RECIPE_ID)
               .build();
       String myImageBytes = "My Image File" ;
       Byte[] imageBytes = new Byte[myImageBytes.getBytes().length];

       int i = 0;

       for(byte b : myImageBytes.getBytes()){
           imageBytes[i++] = b;
       }
       recipeCommand.setImage(imageBytes);
       recipe.setImage(imageBytes);
       //when
       when(recipeService.findById(anyLong()))
               .thenReturn(recipe);

       //then
       MockHttpServletResponse response = mockMvc
               .perform(get("/recipe/1/recipeimage"))
               .andExpect(status().isOk())
               .andReturn().getResponse();

       byte[] responseBytes = response.getContentAsByteArray();

       assertEquals(responseBytes.length,myImageBytes.getBytes().length);



   }

   

}