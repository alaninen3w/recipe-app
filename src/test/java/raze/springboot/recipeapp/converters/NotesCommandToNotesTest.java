package raze.springboot.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raze.springboot.recipeapp.commands.NotesCommand;
import raze.springboot.recipeapp.model.Notes;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    private final static Long NOTES_ID = 1L;
    private final static String RECIPE_NOTES = "recipe notes";

    private NotesCommandToNotes notesCommandToNotes;

    @BeforeEach
    void setUp() {
        notesCommandToNotes = new NotesCommandToNotes();
    }
    @Test
    void nullObjectTest(){
        assertNull(notesCommandToNotes.convert(null));
    }
    @Test
    void emptyObjectTest(){
        assertNotNull(notesCommandToNotes
                .convert(NotesCommand.builder().build()));
    }


    @Test
    void convert() {
        //given
        NotesCommand notesCommand =
                NotesCommand.builder()
                .id(NOTES_ID)
                .recipeNotes(RECIPE_NOTES)
                .build();

        //when
        Notes returnedNotes= notesCommandToNotes.convert(notesCommand);

        //then
        assertNotNull(returnedNotes);
        assertEquals(returnedNotes.getId() , NOTES_ID);
        assertEquals(returnedNotes.getRecipeNotes(),RECIPE_NOTES);


    }
}