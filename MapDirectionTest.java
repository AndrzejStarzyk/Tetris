package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapDirectionTest {
    MapDirection[] directions = MapDirection.values();

    @Test
    public void nextTest() throws Exception {
        for( int i=0, j=1;i<4;i++, j=(j+1)%4){
            assertTrue(directions[i].next() == directions[j]);
        }
    }
    @Test
    public void previousTest() throws Exception {
        for( int i=0, j=3;i<4;i++, j=(j+1)%4){
            assertTrue(directions[i].previous() == directions[j]);
        }
    }
}
