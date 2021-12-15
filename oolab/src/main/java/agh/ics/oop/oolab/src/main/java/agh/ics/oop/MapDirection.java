package agh.ics.oop;

public enum MapDirection{

    NORTH(new Vector2d(0, 1), "^"),
    EAST(new Vector2d(1, 0),">"),
    SOUTH(new Vector2d(0, -1), "v"),
    WEST(new Vector2d(-1, 0), "<");

    Vector2d unitVector;
    String string;


    MapDirection(Vector2d unitVector, String string) {
        this.unitVector = unitVector;
        this.string = string;
    }

    public String toString(){
        return string;
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
        return unitVector;
    }

}