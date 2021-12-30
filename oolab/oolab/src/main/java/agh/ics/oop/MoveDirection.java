package agh.ics.oop;

public enum MoveDirection{
    FORWARD,
    BACKWARD,
    RIGHT,
    LEFT;

    public String toString(){
        String y;
        y = switch(this){
            case FORWARD -> "^";
            case BACKWARD -> "v";
            case RIGHT -> ">";
            case LEFT -> "<";
        };
        return y;
    }
    public static MoveDirection fromString(String x){
        MoveDirection y;
        y = switch(x){
            case "f", "forward" -> MoveDirection.FORWARD;
            case "b", "backward" -> MoveDirection.BACKWARD;
            case "r", "right" -> MoveDirection.RIGHT;
            case "l", "left" -> MoveDirection.LEFT;
            default -> null;
        };
        return y;
    }
}