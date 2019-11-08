package raze.springboot.recipeapp.services.jpaimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import raze.springboot.recipeapp.model.Notes;
import raze.springboot.recipeapp.repositories.NotesRepository;
import raze.springboot.recipeapp.services.NotesService;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class NotesJpaServiceImpl implements NotesService {

    private final NotesRepository notesRepository;

    public NotesJpaServiceImpl(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public Notes findById(Long aLong) {
        log.debug("finding notes id : " + aLong);
        return notesRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<Notes> findAll() {
        log.debug("finding all notes");
        Set<Notes> notesSet = new HashSet<>();
        notesRepository.findAll().iterator().forEachRemaining(notesSet::add);

        return notesSet;
    }

    @Override
    public Notes save(Notes notes) {
        if(notes!=null){
            log.debug("saving notes : " + notes);
            return notesRepository.save(notes);

        }

        return null;
    }

    @Override
    public void deleteById(Long aLong) {
        notesRepository.deleteById(aLong);
    }

    @Override
    public void delete(Notes object) {
            notesRepository.delete(object);
    }
}
