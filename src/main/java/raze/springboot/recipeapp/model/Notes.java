package raze.springboot.recipeapp.model;

import javax.persistence.*;

@Entity
public class Notes  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  Id;

    @OneToOne
    private Recipe recipe;

    private String recipeNotes;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeNotes() {
        return recipeNotes;
    }

    public void setRecipeNotes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }
}
