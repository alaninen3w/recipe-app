package raze.springboot.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import raze.springboot.recipeapp.model.Notes;

public interface NotesRepository extends CrudRepository<Notes,Long> {
}
