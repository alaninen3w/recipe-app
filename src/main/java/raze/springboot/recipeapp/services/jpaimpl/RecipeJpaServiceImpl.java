package raze.springboot.recipeapp.services.jpaimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.repositories.RecipeRepository;
import raze.springboot.recipeapp.services.RecipeService;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeJpaServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeJpaServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
}
