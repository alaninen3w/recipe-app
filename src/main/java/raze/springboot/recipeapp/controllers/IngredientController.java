package raze.springboot.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raze.springboot.recipeapp.commands.IngredientCommand;
import raze.springboot.recipeapp.commands.RecipeCommand;
import raze.springboot.recipeapp.commands.UnitOfMeasureCommand;
import raze.springboot.recipeapp.converters.IngredientToIngredientCommand;
import raze.springboot.recipeapp.converters.RecipeCommandToRecipe;
import raze.springboot.recipeapp.converters.RecipeToRecipeCommand;
import raze.springboot.recipeapp.model.Ingredient;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.services.IngredientService;
import raze.springboot.recipeapp.services.RecipeService;
import raze.springboot.recipeapp.services.UnitOfMeasureService;

import java.util.Set;

@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureService unitOfMeasureService;



    public IngredientController(RecipeService recipeService
            , IngredientService ingredientService
            , RecipeToRecipeCommand recipeToRecipeCommand
            , RecipeCommandToRecipe recipeCommandToRecipe
            , IngredientToIngredientCommand ingredientToIngredientCommand, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String getRecipeIngredientList(@PathVariable String id , Model model){
        Recipe recipe = recipeService.findById(Long.valueOf(id));
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
        Set<Ingredient> ingredientSet = recipe.getIngredients();
        model.addAttribute("recipecommand" , recipeCommand);
        return "recipe/ingredient/list";
    }
    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String getRecipeIngredient(@PathVariable String recipeId
            , @PathVariable String ingredientId , Model model){
        Ingredient ingredient = ingredientService
                .findByRecipeAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId));
        model.addAttribute("ingredient"
                , ingredientToIngredientCommand.convert(ingredient));
        return  "recipe/ingredient/show";
    }
    @GetMapping({"/recipe/{id}/ingredient/new"})
    public String getIngredientForm(@PathVariable String id , Model model){
        Recipe recipe = recipeService.findById(Long.valueOf(id));
        IngredientCommand ingredientCommand  =
                IngredientCommand.builder()
                .recipe_id(recipe.getId())
                .uomCommand(UnitOfMeasureCommand.builder().build())
                .build();


        model.addAttribute("ingredientcommand" , ingredientCommand);
        model.addAttribute("uomList" , unitOfMeasureService.findAll());


        return  "recipe/ingredient/ingredientform";
    }


    @PostMapping({"/recipe/{id}/ingredient"})
    public String saveIngredientCommandToRecipe(@PathVariable String id
            , @ModelAttribute("ingredientcomand")IngredientCommand ingredientCommand){
        Long recipeId = Long.valueOf(id);
//        if(recipeId == ingredientCommand.getRecipe_id()) {
            ingredientCommand.setRecipe_id(Long.valueOf(id));
            IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
//            System.out.println(ingredientCommand.getDescription() + " " + ingredientCommand.getId() );
//            System.out.println(savedIngredientCommand.getDescription() + " " + savedIngredientCommand.getId());
//            System.out.println(ingredientCommand.getUomCommand().getId());
//            System.out.println(savedIngredientCommand.getUomCommand().getDescription());
//        }
        return "redirect:/recipe/" + recipeId + "/ingredient/"
                + savedIngredientCommand.getId() + "/show" ;
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteByRecipeAndIngredientId(@PathVariable String recipeId
            , @PathVariable String ingredientId){
        ingredientService.deleteById(Long.valueOf(recipeId),Long.valueOf(ingredientId));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateByRecipeAndIngredientId(@PathVariable String recipeId
            , @PathVariable String ingredientId,Model model){
        Ingredient ingredient =
                ingredientService
                        .findByRecipeAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId));
        model.addAttribute("ingredientcommand" , ingredientToIngredientCommand.convert(ingredient));
        model.addAttribute("uomList" , unitOfMeasureService.findAll());
        return "recipe/ingredient/ingredientform";
    }



}
