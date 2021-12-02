package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    RectangularMap testMap = new RectangularMap(4, 5);


    private final Animal animal1 = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
    private final Animal animal2 = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);

    @Test
    public void toStringWithPositionTest(){
        assertEquals(new Animal(testMap, new Vector2d(3, 2), MapDirection.NORTH).toStringWithPosition(),"(^, (3,2))");

        assertEquals(new Animal(testMap, new Vector2d(4, 0), MapDirection.EAST).toStringWithPosition(),"(>, (4,0))");

        assertEquals(new Animal(testMap, new Vector2d(1, 1), MapDirection.SOUTH).toStringWithPosition(),"(v, (1,1))");

        assertEquals(new Animal(testMap, new Vector2d(1, 4), MapDirection.WEST).toStringWithPosition(),"(<, (1,4))");

        assertEquals(new Animal(testMap, new Vector2d(1, 4), MapDirection.WEST).toStringWithPosition(),"(<, (1,4))");
    }

    @Test
    public void isAtTest(){
        Animal animal;

        animal = new Animal(testMap, new Vector2d(3,2));
        assertTrue(animal.isAt(new Vector2d(3, 2)));

        animal = new Animal(testMap, new Vector2d(1,2));
        assertTrue(animal.isAt(new Vector2d(1, 2)));

        animal = new Animal(testMap, new Vector2d(4,5));
        assertFalse(animal.isAt(new Vector2d(3, 2)));

        animal = new Animal(testMap, new Vector2d(2,0));
        assertFalse(animal.isAt(new Vector2d(3, 0)));

        animal = new Animal(testMap, new Vector2d(1,2));
        assertFalse(animal.isAt(new Vector2d(4, 4)));
    }

    @Test
    public void moveUtilTest(){
        Animal animal;
        testMap.place(animal1);
        testMap.place(animal2);

        animal = new Animal(testMap, new Vector2d(2, 1), MapDirection.SOUTH);
        animal.moveUtil(true);
        assertEquals(animal.getPosition(),new Vector2d(2, 0) );
        assertEquals(animal.getDirection(), MapDirection.SOUTH);
    }

    @Test
    public void moveTest(){
        Animal animal;
        Vector2d newPosition;

        animal = new Animal(testMap, new Vector2d(0, 0), MapDirection.NORTH);
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getPosition(), new Vector2d(0, 0));
        assertEquals(animal.getDirection(), MapDirection.EAST);

        animal = new Animal(testMap, new Vector2d(3, 4), MapDirection.WEST);
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getPosition(), new Vector2d(3, 4));
        assertEquals(animal.getDirection(), MapDirection.SOUTH);;

        animal = new Animal(testMap, new Vector2d(3, 1), MapDirection.EAST);
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2d(4, 1) );
        assertEquals(animal.getDirection(), MapDirection.EAST);

        animal = new Animal(testMap, new Vector2d(2, 3), MapDirection.NORTH);
        animal.move(MoveDirection.BACKWARD);
        assertEquals(animal.getPosition(),new Vector2d(2, 2) );
        assertEquals(animal.getDirection(), MapDirection.NORTH);

        animal = new Animal(testMap, new Vector2d(0, 1), MapDirection.WEST);
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2d(0, 1));
        assertEquals(animal.getDirection(), MapDirection.WEST);
    }

    @Test
    public void moveMultipleTimesTest(){
        Animal animal;
        testMap.place(animal1);
        testMap.place(animal2);

        animal = new Animal(testMap, new Vector2d(3, 3), MapDirection.SOUTH);
        animal.moveMultipleTimes(new String[]{"f", "f", "left", "right", "right"});
        assertEquals(animal.getPosition(), new Vector2d(3, 1));
        assertEquals(animal.getDirection(), MapDirection.WEST);

        animal = new Animal(testMap, new Vector2d(4, 2), MapDirection.NORTH);
        animal.moveMultipleTimes(new String[]{"backward", "Left", ">"});
        assertEquals(animal.getPosition(), new Vector2d(4, 1));
        assertEquals(animal.getDirection(), MapDirection.NORTH);

        animal = new Animal(testMap, new Vector2d(1, 2), MapDirection.WEST);
        animal.moveMultipleTimes(new String[]{"for", "lefT", "down", "up", "up"});
        assertEquals(animal.getPosition(), new Vector2d(1, 2));
        assertEquals(animal.getDirection(), MapDirection.WEST);

        animal = new Animal(testMap, new Vector2d(3, 3), MapDirection.NORTH);
        animal.moveMultipleTimes(new String[]{});
        assertEquals(animal.getPosition(), new Vector2d(3, 3));
        assertEquals(animal.getDirection(), MapDirection.NORTH);

        animal = new Animal(testMap, new Vector2d(4, 4), MapDirection.EAST);
        animal.moveMultipleTimes(new String[]{"f", "l","fr", "forward", "L", "left"});
        assertEquals(animal.getPosition(), new Vector2d(4, 5));
        assertEquals(animal.getDirection(), MapDirection.WEST);
    }
}
