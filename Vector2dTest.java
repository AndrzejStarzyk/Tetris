package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    Vector2d[][] vectorList = {
            {new Vector2d(1, 2), new Vector2d(1, 2)},
            {new Vector2d(0, 0), new Vector2d(0, 2)},
            {new Vector2d(30762, 0), new Vector2d(1847291, 0)},
            {new Vector2d(0, 0), new Vector2d(0, 2)},
            {new Vector2d(-123, 123), new Vector2d(123, -123)}
    };
    @Test
    public void equalsTest() throws Exception{
        for(Vector2d[] vectorPair: vectorList){
            assertEquals(vectorPair[1].equals(vectorPair[0]),
                    vectorPair[0].x == vectorPair[1].x && vectorPair[0].y == vectorPair[1].y);
        }
    }

    @Test
    public void toStringTest() throws Exception{
        for(Vector2d[] vectorPair: vectorList){
            assertEquals(vectorPair[0].toString(),
                    '(' + Integer.toString(vectorPair[0].x) + ',' + Integer.toString(vectorPair[0].y) + ')');
            assertEquals(vectorPair[1].toString(),
                    '(' + Integer.toString(vectorPair[1].x) + ',' + Integer.toString(vectorPair[1].y) + ')');
        }
    }

    @Test
    public void precedesTest() throws Exception{
        for(Vector2d[] vectorPair: vectorList){
            assertTrue(vectorPair[0].x <= vectorPair[1].x && vectorPair[0].y <= vectorPair[1].y);
        }
    }
    @Test
    public void followsTest() throws Exception{
        for(Vector2d[] vectorPair: vectorList){
            assertTrue(vectorPair[0].x >= vectorPair[1].x && vectorPair[0].y >= vectorPair[1].y);
        }
    }

    @Test
    public void upperRightTest() throws Exception{
        for(Vector2d[] vectorPair: vectorList){
            assertEquals(vectorPair[0].upperRight(vectorPair[1]),
                    new Vector2d(Math.max(vectorPair[0].x, vectorPair[1].x), Math.max(vectorPair[0].y, vectorPair[1].y))
            );
        }
    }

    @Test
    public void lowerLeftTest() throws Exception{
        for(Vector2d[] vectorPair: vectorList){
            assertEquals(vectorPair[0].lowerLeft(vectorPair[1]),
                    new Vector2d(Math.min(vectorPair[0].x, vectorPair[1].x), Math.min(vectorPair[0].y, vectorPair[1].y))
            );
        }
    }

    @Test
    public void addTest() throws Exception{
        for(Vector2d[] vectorPair: vectorList){
            assertEquals(vectorPair[0].add(vectorPair[1]),
                    new Vector2d(vectorPair[0].x + vectorPair[1].x, vectorPair[0].y + vectorPair[1].y)
            );
        }
    }

    @Test
    public void substractTest() throws Exception{
        for(Vector2d[] vectorPair: vectorList){
            assertEquals(vectorPair[0].substract(vectorPair[1]),
                    new Vector2d(vectorPair[0].x - vectorPair[1].x, vectorPair[0].y - vectorPair[1].y)
            );
        }
    }

    @Test
    public void oppositeTest() throws Exception{
        for(Vector2d[] vectorPair: vectorList){
            assertEquals(vectorPair[0].opposite(), new Vector2d(vectorPair[0].y, vectorPair[0].x));
            assertEquals(vectorPair[1].opposite(), new Vector2d(vectorPair[1].y, vectorPair[1].x));
        }
    }
}
