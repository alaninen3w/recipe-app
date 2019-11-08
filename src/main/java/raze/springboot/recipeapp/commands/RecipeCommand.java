package raze.springboot.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import raze.springboot.recipeapp.model.Difficulty;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter

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

    @Builder
    public RecipeCommand(Long id, String description, Integer prepTime
            , Integer cookTime, Integer serving, String source, String url
            , String directions, Set<IngredientCommand> ingredients
            , Byte[] image, Difficulty difficulty, NotesCommand notes
            , Set<CategoryCommand> categories) {
        this.id = id;
        this.description = description;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.serving = serving;
        this.source = source;
        this.url = url;
        this.directions = directions;
        if(ingredients != null) this.ingredients.addAll(ingredients);
        this.image = image;
        this.difficulty = difficulty;
        this.notes = notes;
       if(categories != null) this.categories.addAll(categories);
    }
}
