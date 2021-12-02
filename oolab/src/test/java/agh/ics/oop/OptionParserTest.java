package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptionParserTest {

    @Test
    public void parseTest(){
        assertArrayEquals(OptionsParser.parse(new String[]{"f", "l", "r"}),
                new MoveDirection[]{MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.RIGHT});

        assertArrayEquals(OptionsParser.parse(new String[]{"b", "forward", "prawo", "right"}),
                new MoveDirection[]{MoveDirection.BACKWARD, MoveDirection.FORWARD, MoveDirection.RIGHT});

        assertArrayEquals(OptionsParser.parse(new String[]{"left", "right", "foreward", "Right", "LEFT"}),
                new MoveDirection[]{MoveDirection.LEFT, MoveDirection.RIGHT});

        assertArrayEquals(OptionsParser.parse(new String[]{"backward", "L", ">"}),
                new MoveDirection[]{MoveDirection.BACKWARD});

        assertArrayEquals(OptionsParser.parse(new String[]{}),
                new MoveDirection[]{});

        assertArrayEquals(OptionsParser.parse(new String[]{"naprzód", "left", "R", "f"}),
                new MoveDirection[]{ MoveDirection.LEFT, MoveDirection.FORWARD});
    }
}
