package raze.springboot.recipeapp.model.commands;

import lombok.Getter;
import lombok.Setter;
import raze.springboot.recipeapp.model.Category;
import raze.springboot.recipeapp.model.Difficulty;
import raze.springboot.recipeapp.model.Ingredient;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
public class RecipeCommand {
    private Long id;
    private Integer prepTime;
    private Integer cookTime;
    private Integer serving;
    private String source;
    private String url;
    private String directions;
    private Set<Ingredient> ingredients=new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<Category> categories=new HashSet<>();








}
