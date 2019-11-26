package raze.springboot.recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import raze.springboot.recipeapp.services.RecipeService;
@Slf4j
@Controller
public class IndexController {

    private RecipeService recipeService ;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @RequestMapping({"" , "/" , "index" , "/index"})
    public String IndexPage(Model model){
        log.debug("index page with recipes : "+recipeService.findAll());

        model.addAttribute("recipes" , recipeService.findAll());

        return "index";
    }
}
