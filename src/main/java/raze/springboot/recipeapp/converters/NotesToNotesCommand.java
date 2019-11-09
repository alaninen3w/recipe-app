package raze.springboot.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import raze.springboot.recipeapp.commands.NotesCommand;
import raze.springboot.recipeapp.model.Notes;
@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {


    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes notes) {
        if(notes == null) return null;

        NotesCommand notesCommand =
                NotesCommand.builder()
                .id(notes.getId())
                .recipeNotes(notes.getRecipeNotes())
                .build();


        return notesCommand;
    }
}
