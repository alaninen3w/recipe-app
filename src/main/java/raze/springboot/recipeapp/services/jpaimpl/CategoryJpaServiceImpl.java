package raze.springboot.recipeapp.services.jpaimpl;

import org.springframework.stereotype.Service;
import raze.springboot.recipeapp.model.Category;
import raze.springboot.recipeapp.services.CategoryService;

import java.util.Set;

@Service
public class CategoryJpaServiceImpl implements CategoryService {
    @Override
    public Category findById(Long aLong) {
        return null;
    }

    @Override
    public Set<Category> findAll() {
        return null;
    }

    @Override
    public Category save(Category object) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Category object) {

    }
}
