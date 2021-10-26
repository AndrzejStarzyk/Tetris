package agh.ics.oop;

enum MoveDirection{
    FORWARD,
    BACKWARD,
    RIGHT,
    LEFT;

    public String toString(){
        String y;
        y = switch(this){
            case FORWARD -> "Zwierzak idzie do przodu";
            case BACKWARD -> "Zwierzak idzie do tylu";
            case RIGHT -> "Zwierzak idzie w prawo";
            case LEFT -> "Zwierzak idzie w lewo";
        };
        return y;
    }
    public static MoveDirection toMoveDirection(String x){
        MoveDirection y;
        y = switch(x){
            case "f" -> MoveDirection.FORWARD;
            case "b" -> MoveDirection.BACKWARD;
            case "r" -> MoveDirection.RIGHT;
            case "l" -> MoveDirection.LEFT;
            default -> null;
        };
        return y;
    }
}
enum MapDirection{
    NORTH,
    EAST,
    SOUTH,
    WEST;
    public String toString(){
        String message;
        message = switch (this){
            case NORTH -> "Polnoc";
            case EAST -> "Wschod";
            case SOUTH -> "Poludnie";
            case WEST -> "Zachod";
        };
        return message;
    }
    public MapDirection next(){
        MapDirection res;
        res = switch (this){
            case NORTH -> MapDirection.EAST;
            case EAST -> MapDirection.SOUTH;
            case SOUTH -> MapDirection.WEST;
            case WEST -> MapDirection.NORTH;
        };
        return res;
    }
    public MapDirection previous(){
        MapDirection res;
        res = switch (this){
            case NORTH -> MapDirection.WEST;
            case EAST -> MapDirection.NORTH;
            case SOUTH -> MapDirection.EAST;
            case WEST -> MapDirection.SOUTH;
        };
        return res;
    }
    public Vector2d toUnitVector(){
        Vector2d res;
        res = switch (this){
            case NORTH -> new Vector2d(0, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
        };
        return res;
    }

}
