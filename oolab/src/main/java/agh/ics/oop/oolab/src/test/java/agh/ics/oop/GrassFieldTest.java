package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    @Test
    void canMoveTo() {
        GrassField testMap = new GrassField(8);
        Animal animal1 = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        Animal animal2 = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);

        assertTrue(testMap.canMoveTo(new Vector2d(0, 0)));
        assertTrue(testMap.canMoveTo(new Vector2d(123, -432)));
        assertFalse(testMap.canMoveTo(new Vector2d(1, 1)));
        assertFalse(testMap.canMoveTo(new Vector2d(0, 5)));
        assertTrue(testMap.canMoveTo(new Vector2d(-3, -5)));
    }

    @Test
    void objectAt() {
        GrassField testMap = new GrassField(8);
        Animal animal1 = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        Animal animal2 = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);

        assertEquals(testMap.objectAt(new Vector2d(1, 1)), animal1);
        assertEquals(testMap.objectAt(new Vector2d(0, 5)), animal2);
        assertEquals(testMap.objectAt(new Vector2d(9, 9)), null);
        assertEquals(testMap.objectAt(new Vector2d(-20, 5)), null);
        assertEquals(testMap.objectAt(new Vector2d(14, -2)), null);
    }

    @Test
    void placeGrass() {
        GrassField testMap = new GrassField(10);
        Animal animal1 = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        Animal animal2 = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);
        assertTrue(testMap.placeGrass());
    }

    @Test
    void place() {
        GrassField testMap = new GrassField(8);

        Animal animal;
        animal = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        assertTrue(animal.isAt(new Vector2d(1, 1)));
        animal = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);
        assertTrue(animal.isAt(new Vector2d(0, 5)));
        animal = new Animal(testMap, new Vector2d(0, 0), MapDirection.NORTH);
        assertTrue(animal.isAt(new Vector2d(0, 0)));
        animal = new Animal(testMap, new Vector2d(4, 3), MapDirection.NORTH);
        assertTrue(animal.isAt(new Vector2d(4, 3)));
        animal = new Animal(testMap, new Vector2d(-2918, -1937), MapDirection.NORTH);
        assertTrue(animal.isAt(new Vector2d(-2918, -1937)));
    }
}