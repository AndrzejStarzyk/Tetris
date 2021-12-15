package agh.ics.oop;

public class World {
    public static void main(String[] args) {

        try{
            MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "b",
                    "r", "l",
                    "f", "f",
                    "r", "r",
                    "f", "f",
                    "f", "f",
                    "f", "f",
                    "f", "f"});
            IWorldMap map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
            IEngine engine = new SimulationEngine(directions, map, positions);
            System.out.println(map);
            engine.run();
            System.out.println(map);

        }
        catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }

    }

}