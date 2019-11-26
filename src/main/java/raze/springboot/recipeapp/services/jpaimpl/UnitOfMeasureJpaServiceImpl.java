package raze.springboot.recipeapp.services.jpaimpl;

import org.springframework.stereotype.Service;
import raze.springboot.recipeapp.model.UnitOfMeasure;
import raze.springboot.recipeapp.repositories.UnitOfMeasureRepository;
import raze.springboot.recipeapp.services.UnitOfMeasureService;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class UnitOfMeasureJpaServiceImpl implements UnitOfMeasureService {
    public UnitOfMeasureJpaServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private final UnitOfMeasureRepository unitOfMeasureRepository;


    @Override
    public UnitOfMeasure findById(Long aLong) {
        return unitOfMeasureRepository.findById(aLong).orElse(null);
    }

    @Override
    public Set<UnitOfMeasure> findAll() {
        return  StreamSupport
                .stream(unitOfMeasureRepository.findAll().spliterator(),false)
                .collect(Collectors.toSet());
    }

    @Override
    public UnitOfMeasure save(UnitOfMeasure object) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(UnitOfMeasure object) {

    }
}
