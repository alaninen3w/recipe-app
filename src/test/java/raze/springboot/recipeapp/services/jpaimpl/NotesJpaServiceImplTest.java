package raze.springboot.recipeapp.services.jpaimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import raze.springboot.recipeapp.model.Notes;
import raze.springboot.recipeapp.repositories.NotesRepository;
import raze.springboot.recipeapp.services.NotesService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class NotesJpaServiceImplTest {

    @Mock
    private NotesRepository notesRepository;
    @InjectMocks
    private NotesService notesService;


    @BeforeEach
    void setUp() {
        notesService=new NotesJpaServiceImpl();
    }

    @Test
    void findByIdNotesTest() {
        //given
        Optional<Notes> notes= Optional.of(Notes.builder().id(1L).build());

        //when
        when(notesRepository.findById(anyLong())).thenReturn(notes);
        Notes returnedNotes=notesService.findById(1L);

        //then
        assertNotNull(returnedNotes);
        verify(notesRepository,times(1)).findById(anyLong());
        verify(notesRepository,never()).findAll();

    }

    @Test
    void findAllNotesTest() {
        //given
        Set<Notes> notesSet=new HashSet<>();
        Notes notes1=Notes.builder().id(1L).build();
        Notes notes2=Notes.builder().id(2L).build();
        notesSet.addAll(Arrays.asList(notes1,notes2));

        //when
        when(notesRepository.findAll()).thenReturn(notesSet);
        Set<Notes> returnedNotes=notesService.findAll();

        //then
        assertEquals(returnedNotes.size(),2);
        verify(notesRepository,times(1)).findAll();
        verify(notesRepository,never()).findById(anyLong());



    }

    @Test
    void saveNotesTest() {
        //given
        Notes notes=Notes.builder().id(1L).build();

        //when
        ArgumentCaptor<Notes> notesArgumentCaptor=ArgumentCaptor.forClass(Notes.class);
        when(notesRepository.save(any(Notes.class))).thenReturn(notes);
        Notes returnedNotes=notesService.save(notes);

        //then
        assertEquals(returnedNotes.getId(),1L);
        verify(notesRepository,times(1)).save(notesArgumentCaptor.capture());
        Notes capturedNotes=notesArgumentCaptor.getValue();
        assertEquals(capturedNotes,returnedNotes);

        verify(notesRepository,never()).saveAll(anyIterable());

    }

    @Test
    void deleteById() {
        //given
        Long deleteId=1L;

        //when
        notesService.deleteById(deleteId);

        //then
        verify(notesRepository,times(1)).deleteById(deleteId);
        verify(notesRepository,never()).deleteAll();


    }

    @Test
    void delete() {
    }
}