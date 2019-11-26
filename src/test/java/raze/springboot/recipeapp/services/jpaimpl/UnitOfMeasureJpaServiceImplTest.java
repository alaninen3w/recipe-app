package raze.springboot.recipeapp.services.jpaimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import raze.springboot.recipeapp.model.UnitOfMeasure;
import raze.springboot.recipeapp.repositories.UnitOfMeasureRepository;
import raze.springboot.recipeapp.services.UnitOfMeasureService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UnitOfMeasureJpaServiceImplTest {
    public static final Long UOM_ID = 1L;
    public static final Long UOM_ID2 = 3L;
    public static final String DESCRIPTION = "my description" ;

    private UnitOfMeasureService unitOfMeasureService ;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;





    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureJpaServiceImpl(unitOfMeasureRepository);
    }

    @Test
    void findById() {
        //given
        UnitOfMeasure unitOfMeasure =
                UnitOfMeasure.builder()
                .id(UOM_ID)
                .build();

        //when
        when(unitOfMeasureRepository.findById(anyLong()))
                .thenReturn(Optional.of(unitOfMeasure));
        UnitOfMeasure returnedUOM =
                unitOfMeasureService.findById(UOM_ID);

        //then
        assertEquals(returnedUOM.getId(),UOM_ID);

        verify(unitOfMeasureRepository , times(1))
                .findById(anyLong());

    }

    @Test
    void findAll() {
        //given
        UnitOfMeasure unitOfMeasure1 =
                UnitOfMeasure.builder()
                .id(UOM_ID)
                .build();
        UnitOfMeasure unitOfMeasure2 =
                UnitOfMeasure.builder()
                .id(UOM_ID2)
                .build();
        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
        unitOfMeasureSet.add(unitOfMeasure1);
        unitOfMeasureSet.add(unitOfMeasure2);

        //when
        when(unitOfMeasureRepository.findAll())
                .thenReturn(unitOfMeasureSet);
        Set<UnitOfMeasure> returnedUOMs = unitOfMeasureService.findAll();

        //then
        assertEquals(returnedUOMs.size(),2);

        verify(unitOfMeasureRepository , times(1))
                .findAll();



    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void delete() {
    }
}