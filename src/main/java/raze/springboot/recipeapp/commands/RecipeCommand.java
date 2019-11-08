package raze.springboot.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import raze.springboot.recipeapp.model.Difficulty;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Builder
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer serving;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredients=new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories=new HashSet<>();








}
