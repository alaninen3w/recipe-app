package raze.springboot.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raze.springboot.recipeapp.commands.NotesCommand;
import raze.springboot.recipeapp.model.Notes;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {
    private final static Long NOTES_ID = 1L;
    private final static String RECIPE_NOTES = "recipe notes";


    private  NotesToNotesCommand notesToNotesCommand;

    @BeforeEach
    void setUp() {
        notesToNotesCommand = new NotesToNotesCommand();
    }

    @Test
    void nullObjectTest(){
            assertNull(notesToNotesCommand.convert(null));
    }

    @Test
    void emptyObjectTest(){

        assertNotNull(notesToNotesCommand.convert(Notes.builder().build()));

    }
    @Test
    void convert() {
        //given
        Notes notes =
                Notes.builder()
                .id(NOTES_ID)
                .recipeNotes(RECIPE_NOTES)
                .build();
        //when
        NotesCommand returnedNotesCommand = notesToNotesCommand.convert(notes);


        //then
        assertNotNull(returnedNotesCommand);
        assertEquals(returnedNotesCommand.getId() , NOTES_ID );
        assertEquals(returnedNotesCommand.getRecipeNotes() , RECIPE_NOTES);



    }
}