package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptionParserTest {

    @Test
    public void parseTest(){
        assertArrayEquals(OptionsParser.parse(new String[]{"f", "l", "r"}),
                new MoveDirection[]{MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.RIGHT});

        assertArrayEquals(OptionsParser.parse(new String[]{"b", "forward", "r"}),
                new MoveDirection[]{MoveDirection.BACKWARD, MoveDirection.FORWARD, MoveDirection.RIGHT});

        assertArrayEquals(OptionsParser.parse(new String[]{"left", "right"}),
                new MoveDirection[]{MoveDirection.LEFT, MoveDirection.RIGHT});

        assertArrayEquals(OptionsParser.parse(new String[]{"backward", "l", "r"}),
                new MoveDirection[]{MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.RIGHT});

        assertArrayEquals(OptionsParser.parse(new String[]{}),
                new MoveDirection[]{});

        assertArrayEquals(OptionsParser.parse(new String[]{"left", "f"}),
                new MoveDirection[]{ MoveDirection.LEFT, MoveDirection.FORWARD});
    }
}
