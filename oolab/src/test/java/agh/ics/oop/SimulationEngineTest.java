package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationEngineTest {

    @Test
    public void runTest(){
        final IWorldMap testMap = new RectangularMap(6, 11);
        final Animal animal1 = new Animal(testMap, new Vector2d(2, 0), MapDirection.SOUTH);
        final Animal animal2 = new Animal(testMap, new Vector2d(3,7), MapDirection.NORTH);


        final MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "b",
                "r", "l",
                "f", "f",
                "r", "r",
                "f", "f",
                "f", "f",
                "f", "f",
                "f", "f"});

        IWorldMap map = new RectangularMap(6, 11);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        assertEquals(map.toString(), testMap.toString());

        final IWorldMap testMap1 = new RectangularMap(6, 11);
        final Animal animal11 = new Animal(testMap1, new Vector2d(1, 2), MapDirection.SOUTH);
        final Animal animal12 = new Animal(testMap1, new Vector2d(5,2), MapDirection.NORTH);
        new Animal(testMap1, new Vector2d(2, 2));
        final MoveDirection[] directions1 = new OptionsParser().parse(new String[]{
                "f", "f",
                "f", "f",
                "r", "r",
                "f", "f",
                "f", "f",
                "r", "l"
        });


        IWorldMap map1 = new RectangularMap(6, 11);
        new Animal(map1, new Vector2d(2, 2));
        Vector2d[] positions1 = { new Vector2d(0,0), new Vector2d(5,0) };
        IEngine engine1 = new SimulationEngine(directions1, map1, positions1);
        engine1.run();
        System.out.println(testMap1);
        System.out.println(map1);
        assertEquals(map1.toString(), testMap1.toString());
    }

}