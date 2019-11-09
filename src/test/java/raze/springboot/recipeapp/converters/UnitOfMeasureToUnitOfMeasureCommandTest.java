package raze.springboot.recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import raze.springboot.recipeapp.commands.UnitOfMeasureCommand;
import raze.springboot.recipeapp.model.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;
@Component
class UnitOfMeasureToUnitOfMeasureCommandTest {
    private final static Long UOM_ID = 1L;
    private final static String DESCRIPTION = "my description";

    private UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    @BeforeEach
    void setUp() {
        uomConverter = new UnitOfMeasureToUnitOfMeasureCommand();
    }
    @Test
    void nullObjectTest(){
        assertNull(uomConverter.convert(null));

    }
    @Test
    void emptyObjectTest(){
        assertNotNull(uomConverter
                .convert(UnitOfMeasure.builder().build()));
    }
    @Test
    void convert() {
        //given
        UnitOfMeasure unitOfMeasure =
                UnitOfMeasure.builder()
                .id(UOM_ID)
                .description(DESCRIPTION)
                .build();

        //when
        UnitOfMeasureCommand returnedUOMCommand =
                uomConverter.convert(unitOfMeasure);


        //then
        assertNotNull(returnedUOMCommand);
        assertEquals(returnedUOMCommand.getId(),UOM_ID);
        assertEquals(returnedUOMCommand.getDescription(),DESCRIPTION);




    }
}