package raze.springboot.recipeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication

public class RecipeAppApplication {

    public static void main(String[] args) {
        ApplicationContext ctx=SpringApplication.run(RecipeAppApplication.class, args);
    }

}
