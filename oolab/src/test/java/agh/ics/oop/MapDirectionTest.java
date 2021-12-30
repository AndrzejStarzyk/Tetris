package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {
    MapDirection[] directions = MapDirection.values();

    @Test
    public void nextTest() {
        MapDirection[] ans = {MapDirection.EAST, MapDirection.SOUTH, MapDirection.WEST, MapDirection.NORTH};
        for (int i = 0; i < directions.length; i++) {
            assertEquals(directions[i].next(), ans[i]);
        }
    }
    @Test
    public void previousTest() {
        MapDirection[] ans = {MapDirection.WEST, MapDirection.NORTH, MapDirection.EAST, MapDirection.SOUTH};
        for (int i = 0; i < directions.length; i++) {
            assertEquals(directions[i].previous(), ans[i]);
        }
    }
    @Test
    public void toUnitVectorTest() {
        Vector2d[] ans = {new Vector2d(0, 1), new Vector2d(1, 0), new Vector2d(0, -1), new Vector2d(-1, 0)};
        for (int i = 0; i < directions.length; i++) {
            assertEquals(directions[i].toUnitVector(), ans[i]);
        }
    }
}
