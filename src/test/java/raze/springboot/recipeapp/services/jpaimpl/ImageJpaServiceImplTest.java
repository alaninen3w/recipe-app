package raze.springboot.recipeapp.services.jpaimpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.repositories.RecipeRepository;
import raze.springboot.recipeapp.services.ImageService;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

;


class ImageJpaServiceImplTest {
    private ImageService imageService ;

    @Mock
    private RecipeRepository recipeRepository ;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageJpaServiceImpl(recipeRepository);
    }

    @Test
    void saveImageFile() throws IOException {
        //given
        Long RECIPE_ID = 1L;
        MultipartFile multipartFile
                = new MockMultipartFile("image file" ,"my bytes".getBytes());
        Recipe recipe =
                Recipe.builder()
                .id(RECIPE_ID)
                .build();
        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        //when
        when(recipeRepository.findById(anyLong()))
                .thenReturn(Optional.of(recipe));
        imageService.saveImageFile(RECIPE_ID,multipartFile);

        //then
        verify(recipeRepository,times(1))
                .save(recipeArgumentCaptor.capture());
        Recipe capturedRecipe = recipeArgumentCaptor.getValue();

        assertEquals(capturedRecipe.getImage().length, multipartFile.getBytes().length);

    }
}