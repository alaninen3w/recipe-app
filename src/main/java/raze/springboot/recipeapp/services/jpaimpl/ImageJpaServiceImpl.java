package raze.springboot.recipeapp.services.jpaimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.repositories.RecipeRepository;
import raze.springboot.recipeapp.services.ImageService;

import java.io.IOException;
import java.util.Optional;
@Slf4j
@Service
public class ImageJpaServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageJpaServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Override
    public void saveImageFile(Long recipeId, MultipartFile multipartFile)  {

        Optional<Recipe> optionalRecipe =
                recipeRepository.findById(recipeId);
        if(optionalRecipe.isPresent()){
            log.debug("adding image to recipe with id : " + recipeId);
            Recipe recipe = optionalRecipe.get();


            try {
                Byte [] imageBytes =  new Byte[multipartFile.getBytes().length];
                int i = 0;
                for(byte b : multipartFile.getBytes()){
                    imageBytes[i++] = b;
                }
                recipe.setImage(imageBytes);
                recipeRepository.save(recipe);

            } catch (IOException e) {
//                e.printStackTrace();
                throw new RuntimeException("Image File Not found");
            }




        }else{
            throw new RuntimeException("Recipe Not Found");
        }


    }
}
