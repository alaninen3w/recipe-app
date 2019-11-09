package raze.springboot.recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import raze.springboot.recipeapp.commands.NotesCommand;
import raze.springboot.recipeapp.model.Notes;
@Component
public class NotesCommandToNotes
        implements Converter<NotesCommand, Notes> {
    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand notesCommand) {
        if(notesCommand == null ) return  null;
        Notes notes =
                Notes.builder()
                .id(notesCommand.getId())
                .recipeNotes(notesCommand.getRecipeNotes())
                .build();



        return notes;
    }
}
