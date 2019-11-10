package raze.springboot.recipeapp.services.jpaimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raze.springboot.recipeapp.commands.RecipeCommand;
import raze.springboot.recipeapp.converters.RecipeCommandToRecipe;
import raze.springboot.recipeapp.converters.RecipeToRecipeCommand;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.repositories.RecipeRepository;
import raze.springboot.recipeapp.services.RecipeService;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeJpaServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;


    @Autowired
    public RecipeJpaServiceImpl(RecipeRepository recipeRepository
            , RecipeToRecipeCommand recipeToRecipeCommand
            , RecipeCommandToRecipe recipeCommandToRecipe) {

        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Override
    public Recipe findById(Long aLong) {
        log.debug("finding by recipe Id : "+aLong);
        return recipeRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<Recipe> findAll() {
        log.debug("finding all recipes");
        Set<Recipe> recipeSet=new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe save(Recipe recipe) {
        if(recipe!=null){
            log.debug("saving recipe : "+recipe );
          return  recipeRepository.save(recipe);

        }

        return null;
    }

    @Override
    public void deleteById(Long aLong) {
        recipeRepository.deleteById(aLong);
    }

    @Override
    public void delete(Recipe object) {
            recipeRepository.delete(object);
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        if(recipeCommand == null) return null;
        log.debug("saving  recipe command id : " + recipeCommand.getId());
       Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

       Recipe returnedRecipe = recipeRepository.save(recipe);
        return recipeToRecipeCommand.convert(returnedRecipe);
    }
}
