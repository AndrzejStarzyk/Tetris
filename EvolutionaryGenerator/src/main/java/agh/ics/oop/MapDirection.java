package agh.ics.oop;

import java.util.Random;

public enum MapDirection{

    N(new Vector2d(0, 1)),
    NE(new Vector2d(1, 1)),
    E(new Vector2d(1, 0)),
    SE(new Vector2d(1, -1)),
    S(new Vector2d(0, -1)),
    SW(new Vector2d(-1, -1)),
    W(new Vector2d(-1, 0)),
    NW(new Vector2d(-1, 1));

    private final Vector2d moveVector;


    MapDirection(Vector2d unitVector) {
        this.moveVector = unitVector;
    }

    public MapDirection rotateRight(){
        MapDirection res;
        res = switch (this){
            case N -> MapDirection.NE;
            case NE -> MapDirection.E;
            case E -> MapDirection.SE;
            case SE -> MapDirection.S;
            case S -> MapDirection.SW;
            case SW -> MapDirection.W;
            case W -> MapDirection.NW;
            case NW -> MapDirection.N;
        };
        return res;
    }

    public MapDirection rotateLeft(){
        MapDirection res;
        res = switch (this){
            case N -> MapDirection.NW;
            case NE -> MapDirection.N;
            case E -> MapDirection.NE;
            case SE -> MapDirection.E;
            case S -> MapDirection.SE;
            case SW -> MapDirection.S;
            case W -> MapDirection.SW;
            case NW -> MapDirection.W;
        };
        return res;
    }

    public Vector2d toVector(){
        return moveVector;
    }

    public static MapDirection randomDirection(){
        Random random = new Random();
        return switch (random.nextInt(8)){
            case 0 -> MapDirection.NW;
            case 1 -> MapDirection.N;
            case 2 -> MapDirection.NE;
            case 3 -> MapDirection.E;
            case 4 -> MapDirection.SE;
            case 5 -> MapDirection.S;
            case 6 -> MapDirection.SW;
            default-> MapDirection.W;
        };
    }

}