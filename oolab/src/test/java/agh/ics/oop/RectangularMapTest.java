package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    void canMoveTo() {
        RectangularMap testMap = new RectangularMap(5, 6);
        Animal animal1 = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        Animal animal2 = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);

        assertTrue(testMap.canMoveTo(new Vector2d(0, 0)));
        assertTrue(testMap.canMoveTo(new Vector2d(3, 2)));
        assertFalse(testMap.canMoveTo(new Vector2d(1, 1)));
        assertFalse(testMap.canMoveTo(new Vector2d(0, 5)));
        assertFalse(testMap.canMoveTo(new Vector2d(5, 5)));

    }

    @Test
    void place() {
        RectangularMap testMap = new RectangularMap(5, 6);
        Animal animal1 = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        Animal animal2 = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);

        assertEquals(animal1.getMap(), testMap);
        assertEquals(animal2.getMap(), testMap);
    }

    @Test
    void objectAt() {
        RectangularMap testMap = new RectangularMap(5, 6);
        Animal animal1 = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        Animal animal2 = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);

        assertEquals(testMap.objectAt(new Vector2d(1, 1)), animal1);
        assertEquals(testMap.objectAt(new Vector2d(0, 5)), animal2);
        assertEquals(testMap.objectAt(new Vector2d(2, 1)), null);
        assertEquals(testMap.objectAt(new Vector2d(3, 5)), null);
        assertEquals(testMap.objectAt(new Vector2d(0, 1)), null);
    }



}