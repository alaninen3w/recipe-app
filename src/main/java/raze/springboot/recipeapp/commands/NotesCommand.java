package raze.springboot.recipeapp.commands;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotesCommand {
    private Long id;
    private String recipeNotes;

}
