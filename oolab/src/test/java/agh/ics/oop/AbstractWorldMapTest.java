package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {

    @Test
    void toStringTest(){
        RectangularMap testMap = new RectangularMap(4, 5);
        Animal animal1 = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        Animal animal2 = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);
        testMap.place(animal1);
        testMap.place(animal2);


    }

    @Test
    void isOccupiedTest() {
        RectangularMap testMap = new RectangularMap(4, 5);
        Animal animal1 = new Animal(testMap, new Vector2d(1, 1), MapDirection.EAST);
        Animal animal2 = new Animal(testMap, new Vector2d(0, 5), MapDirection.SOUTH);
        testMap.place(animal1);
        testMap.place(animal2);

        assertTrue(testMap.isOccupied(new Vector2d(1, 1)));
        assertTrue(testMap.isOccupied(new Vector2d(0, 5)));
        assertFalse(testMap.isOccupied(new Vector2d(0, 1)));
        assertFalse(testMap.isOccupied(new Vector2d(4, 3)));
        assertFalse(testMap.isOccupied(new Vector2d(0, 7)));
    }


}