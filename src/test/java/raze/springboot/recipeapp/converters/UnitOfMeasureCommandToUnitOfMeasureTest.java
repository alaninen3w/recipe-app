package raze.springboot.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raze.springboot.recipeapp.commands.UnitOfMeasureCommand;
import raze.springboot.recipeapp.model.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {
    private final static Long UOM_ID = 1L;
    private final static String DESCRIPTION = "description";


    private UnitOfMeasureCommandToUnitOfMeasure uomCommandConverter;

    @BeforeEach
    void setUp() {
        uomCommandConverter = new UnitOfMeasureCommandToUnitOfMeasure();
    }
    @Test
    void nullObjectTest(){
        assertNull(uomCommandConverter.convert(null));
    }
    @Test
    void emptyObjectTest(){
        assertNotNull(uomCommandConverter
                .convert(UnitOfMeasureCommand.builder().build()));
    }

    @Test
    void convert() {
        //given
        UnitOfMeasureCommand unitOfMeasureCommand =
                UnitOfMeasureCommand.builder()
                .id(UOM_ID)
                .description(DESCRIPTION)
                .build();

        //when
        UnitOfMeasure returnedUnitOfMeasure =
                uomCommandConverter.convert(unitOfMeasureCommand);

        //then
        assertNotNull(returnedUnitOfMeasure);
        assertEquals(returnedUnitOfMeasure.getId(),UOM_ID);
        assertEquals(returnedUnitOfMeasure.getDescription(),DESCRIPTION);


    }
}