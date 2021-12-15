package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {

    @Test
    void isOccupiedTest() {
        RectangularMap testMap = new RectangularMap(5, 6);
        Animal animal1 = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        Animal animal2 = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);

        assertTrue(testMap.isOccupied(new Vector2d(1, 1)));
        assertTrue(testMap.isOccupied(new Vector2d(0, 5)));
        assertFalse(testMap.isOccupied(new Vector2d(0, 1)));
        assertFalse(testMap.isOccupied(new Vector2d(4, 3)));
        assertFalse(testMap.isOccupied(new Vector2d(0, 7)));
    }


}