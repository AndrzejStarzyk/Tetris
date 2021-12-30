package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OptionParserTest {

    @Test
    public void parseTest(){
        List<String> list1 = new ArrayList<String>();
        list1.add("f");
        list1.add("l");
        list1.add("r");
        assertArrayEquals(OptionsParser.parse(list1),
                new MoveDirection[]{MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.RIGHT});

        List<String> list2 = new ArrayList<String>();
        list2.add("b");
        list2.add("forward");
        list2.add("r");
        assertArrayEquals(OptionsParser.parse(list2),
                new MoveDirection[]{MoveDirection.BACKWARD, MoveDirection.FORWARD, MoveDirection.RIGHT});

        List<String> list3 = new ArrayList<String>();
        list3.add("left");
        list3.add("right");
        assertArrayEquals(OptionsParser.parse(list3),
                new MoveDirection[]{MoveDirection.LEFT, MoveDirection.RIGHT});

        List<String> list4 = new ArrayList<String>();
        list4.add("backward");
        list4.add("l");
        list4.add("r");
        assertArrayEquals(OptionsParser.parse(list4),
                new MoveDirection[]{MoveDirection.BACKWARD, MoveDirection.LEFT, MoveDirection.RIGHT});

        assertArrayEquals(OptionsParser.parse(new ArrayList<String>()),
                new MoveDirection[]{});

        List<String> list5 = new ArrayList<String>();
        list5.add("left");
        list5.add("f");
        assertArrayEquals(OptionsParser.parse(list5),
                new MoveDirection[]{ MoveDirection.LEFT, MoveDirection.FORWARD});
    }
}
