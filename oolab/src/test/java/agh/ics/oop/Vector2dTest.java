package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest{
    @Test
    public void equalsTest(){
        Vector2d[][] vectorList = {
                {new Vector2d(1, 2), new Vector2d(1, 2)},
                {new Vector2d(0, 0), new Vector2d(0, 2)},
                {new Vector2d(30762, 0), new Vector2d(1847291, 0)},
                {new Vector2d(5, 6), new Vector2d(0, 2)},
                {new Vector2d(-123, 123), new Vector2d(123, -123)}
        };
        boolean[] ans = {true, false, false, false, false};
        for(int i=0;i<vectorList.length;i++){
            assertEquals(vectorList[i][0].equals(vectorList[i][1]), ans[i]);
        }
    }

    @Test
    public void toStringTest(){
        Vector2d[] vectorList = {
                new Vector2d(1, 2), new Vector2d(4, -22),
                new Vector2d(0, 0), new Vector2d(0, 2),
                new Vector2d(30762, 0), new Vector2d(1847291, 0),
                new Vector2d(-123, 123), new Vector2d(123, -123)
        };
        String[] ans = {"(1,2)", "(4,-22)","(0,0)", "(0,2)", "(30762,0)", "(1847291,0)", "(-123,123)", "(123,-123)"};
        for(int i=0;i<vectorList.length;i++){
            assertEquals(vectorList[i].toString(), ans[i]);
        }
    }

    @Test
    public void precedesTest(){
        Vector2d[][] vectorList = {
                {new Vector2d(3, 8), new Vector2d(3, 8)},
                {new Vector2d(49, 49), new Vector2d(51, 49)},
                {new Vector2d(-19, 11), new Vector2d(-20, 10)},
                {new Vector2d(1, -1), new Vector2d(7, -2)},
                {new Vector2d(-123, 123), new Vector2d(123, -123)}
        };
        boolean[] ans = {true, true, false, false, false};
        for(int i=0;i<vectorList.length;i++){
            assertEquals(vectorList[i][0].precedes(vectorList[i][1]), ans[i]);
        }
    }
    @Test
    public void followsTest(){
        Vector2d[][] vectorList = {
                {new Vector2d(3, 8), new Vector2d(3, 8)},
                {new Vector2d(49, 49), new Vector2d(51, 49)},
                {new Vector2d(-19, 11), new Vector2d(-20, 10)},
                {new Vector2d(1, -1), new Vector2d(7, -2)},
                {new Vector2d(-123, 123), new Vector2d(123, -123)}
        };
        boolean[] ans = {true, false, true, false, false};
        for(int i=0;i<vectorList.length;i++){
            assertEquals(vectorList[i][0].follows(vectorList[i][1]), ans[i]);
        }
    }

    @Test
    public void upperRightTest(){
        Vector2d[][] vectorList = {
                {new Vector2d(1, 2), new Vector2d(1, 2)},
                {new Vector2d(6, 5), new Vector2d(-5, 6)},
                {new Vector2d(30762, 47264), new Vector2d(1847291, 17366666)},
                {new Vector2d(-2, 0), new Vector2d(0, 2)},
                {new Vector2d(-123, 123), new Vector2d(123, -123)}
        };
        Vector2d[] ans = {
                new Vector2d(1, 2),
                new Vector2d(6, 6),
                new Vector2d(1847291, 17366666),
                new Vector2d(0, 2),
                new Vector2d(123, 123)
        };
        for(int i=0;i<vectorList.length;i++){
            assertEquals(vectorList[i][0].upperRight(vectorList[i][1]), ans[i]);
        }
    }

    @Test
    public void lowerLeftTest(){
        Vector2d[][] vectorList = {
                {new Vector2d(1, 2), new Vector2d(1, 2)},
                {new Vector2d(6, 5), new Vector2d(-5, 6)},
                {new Vector2d(30762, 47264), new Vector2d(1847291, 17366666)},
                {new Vector2d(-2, 0), new Vector2d(0, 2)},
                {new Vector2d(-123, 123), new Vector2d(123, -123)}
        };
        Vector2d[] ans = {
                new Vector2d(1, 2),
                new Vector2d(-5, 5),
                new Vector2d(30762, 47264),
                new Vector2d(-2, 0),
                new Vector2d(-123, -123)
        };
        for(int i=0;i<vectorList.length;i++){
            assertEquals(vectorList[i][0].lowerLeft(vectorList[i][1]), ans[i]);
        }
    }

    @Test
    public void addTest(){
        Vector2d[][] vectorList = {
                {new Vector2d(-3, 4), new Vector2d(7, 1)},
                {new Vector2d(0, 0), new Vector2d(0, 0)},
                {new Vector2d(45, 81), new Vector2d(31, 75)},
                {new Vector2d(-1, 1), new Vector2d(1, -1)},
                {new Vector2d(-54, 2), new Vector2d(4, -38)}
        };
        Vector2d[] ans = {
                new Vector2d(4, 5),
                new Vector2d(0, 0),
                new Vector2d(76, 156),
                new Vector2d(0, 0),
                new Vector2d(-50, -36)
        };
        for(int i=0;i<vectorList.length;i++){
            assertEquals(vectorList[i][0].add(vectorList[i][1]), ans[i]);
        }
    }

    @Test
    public void subtractTest(){
        Vector2d[][] vectorList = {
                {new Vector2d(-3, 4), new Vector2d(7, 1)},
                {new Vector2d(0, 0), new Vector2d(0, 0)},
                {new Vector2d(45, 81), new Vector2d(31, 75)},
                {new Vector2d(-1, 1), new Vector2d(1, -1)},
                {new Vector2d(-54, 2), new Vector2d(4, -38)}
        };
        Vector2d[] ans = {
                new Vector2d(-10, 3),
                new Vector2d(0, 0),
                new Vector2d(14, 6),
                new Vector2d(-2, 2),
                new Vector2d(-58, 40)
        };
        for(int i=0;i<vectorList.length;i++){
            assertEquals(vectorList[i][0].subtract(vectorList[i][1]), ans[i]);
        }
    }

    @Test
    public void oppositeTest(){
        Vector2d[] vectorList = {
                new Vector2d(1, 2), new Vector2d(-1, 2),
                new Vector2d(0, 0), new Vector2d(8, -2),
                new Vector2d(30762, 23), new Vector2d(-1847291, -87),
                new Vector2d(-123, 123), new Vector2d(123, -123)
        };
        Vector2d[] ans = {
                new Vector2d(-1, -2), new Vector2d(1, -2),
                new Vector2d(0, 0), new Vector2d(-8, 2),
                new Vector2d(-30762, -23), new Vector2d(1847291, 87),
                new Vector2d(123, -123), new Vector2d(-123, 123)
        };
        for(int i=0;i<vectorList.length;i++){
            assertEquals(vectorList[i].opposite(), ans[i]);
        }
    }

    @Test
    public void correctPositionTest(){
        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(4, 4);
        assertTrue(new Vector2d(1, 3).correctPosition(lowerLeft, upperRight));
        assertTrue(new Vector2d(0, 0).correctPosition(lowerLeft, upperRight));
        assertTrue(new Vector2d(4, 4).correctPosition(lowerLeft, upperRight));
        assertTrue(new Vector2d(0, 4).correctPosition(lowerLeft, upperRight));
        assertTrue(new Vector2d(4, 0).correctPosition(lowerLeft, upperRight));
        assertFalse(new Vector2d(38271, -1871).correctPosition(lowerLeft, upperRight));
        assertFalse(new Vector2d(27164, 3).correctPosition(lowerLeft, upperRight));
        assertFalse(new Vector2d(-23, 3).correctPosition(lowerLeft, upperRight));
        assertFalse(new Vector2d(0, -1).correctPosition(lowerLeft, upperRight));
    }
}
