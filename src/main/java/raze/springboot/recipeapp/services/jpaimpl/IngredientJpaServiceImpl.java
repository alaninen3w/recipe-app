package raze.springboot.recipeapp.services.jpaimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raze.springboot.recipeapp.commands.IngredientCommand;
import raze.springboot.recipeapp.converters.IngredientCommandToIngredient;
import raze.springboot.recipeapp.converters.IngredientToIngredientCommand;
import raze.springboot.recipeapp.model.Ingredient;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.repositories.IngredientRepository;
import raze.springboot.recipeapp.repositories.RecipeRepository;
import raze.springboot.recipeapp.repositories.UnitOfMeasureRepository;
import raze.springboot.recipeapp.services.IngredientService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientJpaServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientJpaServiceImpl(IngredientRepository ingredientRepository
            , RecipeRepository recipeRepository
            , UnitOfMeasureRepository unitOfMeasureRepository
            , IngredientCommandToIngredient ingredientCommandToIngredient
            , IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public Ingredient findById(Long aLong) {
        return ingredientRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<Ingredient> findAll() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredientRepository.findAll().forEach(ingredients::add);


        return ingredients;
    }

    @Override
    public Ingredient save(Ingredient object) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }


    @Override
    public void delete(Ingredient object) {

    }




    //Check this with master source from guru
    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        if(ingredientCommand == null) return  null;

        Optional<Recipe> optionalRecipe =
                recipeRepository.findById(ingredientCommand.getRecipe_id());

        if(optionalRecipe.isPresent()){
            Recipe recipe = optionalRecipe.get();
            Optional<Ingredient> optionalIngredient =
                    recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId()
                            .equals(ingredientCommand.getId()))
                    .findFirst();
            if(optionalIngredient.isPresent()){
                Ingredient ingredientToUpdate = optionalIngredient.get();
                ingredientToUpdate.setDescription(ingredientCommand.getDescription());
                ingredientToUpdate.setAmount(ingredientCommand.getAmount());
                ingredientToUpdate.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand.getId())
                        .orElseThrow(() -> new RuntimeException("UOM Not Found")));
                ingredientRepository.save(ingredientToUpdate);

            }else{
                Ingredient newIngredient =
                        ingredientCommandToIngredient.convert(ingredientCommand);
                ingredientRepository.save(newIngredient);
                recipe.addIngredient(newIngredient);
             }
            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredient =
                     savedRecipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();


            return  ingredientToIngredientCommand.convert(savedIngredient.get());

        }else{
            throw new RuntimeException("Recipe not found");
        }

    }

    @Override
    public Ingredient findByRecipeAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(optionalRecipe.isPresent()){
            Recipe recipe = optionalRecipe.get();
            Optional<Ingredient> optionalIngredient = recipe.getIngredients()
                    .stream().filter(ingredientToFilter ->
                            ingredientToFilter.getId().equals(ingredientId))
                    .findAny();
            if(optionalIngredient.isPresent()){
                return optionalIngredient.get();

            }else throw new RuntimeException("Ingredient Not Found");


        }else{
            throw  new RuntimeException("Recipe Not Found");
        }

    }


    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        log.debug("deleting ingredient with Id : "
                + ingredientId + "and Recipe Id : "
                + recipeId);
        Optional<Recipe> optionalRecipe =
                recipeRepository.findById(recipeId);

        if(optionalRecipe.isPresent()){
            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> optionalIngredient =
                    recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();
            if(optionalIngredient.isPresent()){
                Ingredient ingredient = optionalIngredient.get();
                recipe.getIngredients().remove(ingredient);
                ingredient.setRecipe(null);
                ingredientRepository.deleteById(ingredient.getId());
                recipeRepository.save(recipe);
            }


        }else {
            throw  new RuntimeException("Recipe Not Found");
        }


    }
}
