package raze.springboot.recipeapp.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotesCommand {
    private Long id;
    private String recipeNotes;

}
