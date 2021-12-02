package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    @Test
    void canMoveTo() {
        GrassField testMap = new GrassField(8);
        Animal animal1 = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        Animal animal2 = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);
        testMap.place(animal1);
        testMap.place(animal2);

        assertTrue(testMap.canMoveTo(new Vector2d(0, 0)) );
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
        testMap.place(animal1);
        testMap.place(animal2);

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
        testMap.place(animal1);
        testMap.place(animal2);
        assertTrue(testMap.placeGrass());
    }

    /*@Test
    void place() {
        GrassField testMap = new GrassField(8);
        new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);
        System.out.println(testMap);
        new Animal(testMap, new Vector2d(0, 0), MapDirection.NORTH
        assertTrue(testMap.place()));
        assertTrue(testMap.place(new Animal(testMap, new Vector2d(4, 3), MapDirection.NORTH)));
        assertTrue(testMap.place(new Animal(testMap, new Vector2d(-2918, -1937), MapDirection.NORTH)));
        assertFalse(testMap.place(new Animal(testMap, new Vector2d(1, 1), MapDirection.NORTH)));
        assertFalse(testMap.place(new Animal(testMap, new Vector2d(0, 5), MapDirection.NORTH)));
    }*/
}