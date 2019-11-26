package raze.springboot.recipeapp.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import raze.springboot.recipeapp.converters.RecipeCommandToRecipe;
import raze.springboot.recipeapp.converters.RecipeToRecipeCommand;
import raze.springboot.recipeapp.model.Recipe;
import raze.springboot.recipeapp.services.ImageService;
import raze.springboot.recipeapp.services.RecipeService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public ImageController(ImageService imageService
            , RecipeService recipeService
            , RecipeToRecipeCommand recipeToRecipeCommand
            , RecipeCommandToRecipe recipeCommandToRecipe) {

        this.imageService = imageService;
        this.recipeService = recipeService;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

   @GetMapping({"recipe/{id}/image"})
   public String getImageForm(@PathVariable String id , Model model){
        Recipe recipe = recipeService.findById(Long.valueOf(id));
        model.addAttribute("recipecommand"
                , recipeToRecipeCommand.convert(recipe));

        return "recipe/imageuploadform";
   }
   @PostMapping({"recipe/{id}/image"})
   public String uploadImage(@PathVariable String id
           , @RequestParam("imageFile") MultipartFile imageMultipartFile) {
        imageService.saveImageFile(Long.valueOf(id) , imageMultipartFile);

        return "redirect:/recipe/" + id + "/show";
   }

   @GetMapping("/recipe/{id}/recipeimage")
   public void renderImageFormDB(@PathVariable String id
           , HttpServletResponse httpServletResponse) throws IOException {
        Recipe recipe =
                recipeService.findById(Long.valueOf(id));
        Byte [] imageBytes = recipe.getImage();

        if(imageBytes != null){
            byte [] bytes = new byte[imageBytes.length];
            int i = 0;
            for(byte b : imageBytes){
                bytes[i++] = b ;
            }

        httpServletResponse.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(bytes);
            IOUtils.copyLarge(is, httpServletResponse.getOutputStream());

        }


   }



}
