package raze.springboot.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raze.springboot.recipeapp.commands.RecipeCommand;
import raze.springboot.recipeapp.converters.RecipeCommandToRecipe;
import raze.springboot.recipeapp.converters.RecipeToRecipeCommand;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.services.RecipeService;

@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeController(RecipeService recipeService
            , RecipeCommandToRecipe recipeCommandToRecipe
            , RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeService = recipeService;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @GetMapping({"/recipe/{id}/show"})
    public String getRecipe(@PathVariable String id , Model model){
        Recipe recipe = recipeService.findById(Long.valueOf(id));
        model.addAttribute("recipe" , recipe);
        return "recipe/show" ;
    }

    @GetMapping({"/recipe/new"})
    public String getRecipeForm(Model model){
        RecipeCommand recipeCommand = RecipeCommand.builder().build();
        model.addAttribute("recipecommand" , recipeCommand);

        return "recipe/recipeform";
    }


    @PostMapping({"/recipe"})
    public String saveRecipe(@ModelAttribute RecipeCommand recipeCommand ){
        recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + recipeCommand.getId() + "/show";
    }

    @GetMapping({"/recipe/{id}/update"})
    public String updateRecipe(@PathVariable String id
            , Model model){

        Recipe recipe = recipeService.findById(Long.valueOf(id));
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        model.addAttribute("recipecommand" , recipeCommand);

        return "recipe/recipeform";
    }


    @GetMapping({"/recipe/{id}/delete"})
    public String deleteRecipe(@PathVariable String id){
        recipeService.deleteById(Long.valueOf(id));
        return  "redirect:/";
    }

}
