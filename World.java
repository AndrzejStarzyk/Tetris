package agh.ics.oop;

public class World {
    public static void main(String[] args){
        System.out.print("START");
        MoveDirection[] directions= new MoveDirection[args.length];
        for(int i=0; i < directions.length; i++) {
            directions[i] = MoveDirection.toMoveDirection(args[i]);
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

}
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