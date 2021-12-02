package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "b",
                "r", "l",
                "f", "f",
                "r", "r",
                "f", "f",
                "f", "f",
                "f", "f",
                "f", "f"});
        IWorldMap map = new GrassField(10);
        System.out.println(map);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        System.out.println(map);
        engine.run();
        System.out.println(map);
    }

}
/*
public static void main(String[] args){
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
    }
 */
/*
public static void main(String[] args){
        System.out.print("START");
        MoveDirection[] directions= new MoveDirection[args.length];
        for(int i=0; i < directions.length; i++) {
            directions[i] = MoveDirection.fromString(args[i]);
        }
        run(directions);
        System.out.println();
        System.out.println("STOP");
    }
    public static void run(MoveDirection[] d){
        String msg;
        for (MoveDirection dir : d) {
            msg = dir.toString();
            System.out.println();
            System.out.print(msg);
        }

    }
 */
/*
public static void main(String[] args){
        System.out.print("START");
        String[] s = {"f", "f", "r", "l"};
        run(s);
        System.out.println();
        System.out.println("STOP");
    }
    public static void run(String[] args){
        for(String a : args){
            String msg = switch(a){
                case "f" -> "Zwierzak idzie do przodu";
                case "b" -> "Zwierzak idzie do tylu";
                case "r" -> "Zwierzak idzie w prawo";
                case "l" -> "Zwierzak idzie w lewo";
                default -> "Niepoprawny argument";
            };
            System.out.println();
            System.out.print(msg);
        }
    }
 */