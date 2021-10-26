package agh.ics.oop;

public class Vector2d {
    public static void main(String[] args){
    Vector2d position1 = new Vector2d(1,2);
    System.out.println(position1);
    Vector2d position2 = new Vector2d(-2,1);
    System.out.println(position2);
    System.out.println(position1.add(position2));
    }

    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return '(' + Integer.toString(this.x) + ',' + Integer.toString(this.y) + ')';
    }

    boolean percedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(this.x, other.x),Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(this.x, other.x),Math.min(this.y, other.y));
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x+other.x, this.y+other.y);
    }

    public Vector2d substract(Vector2d other){
        return new Vector2d(this.x-other.x, this.y-other.y);
    }

    public boolean equals(Object other){
        if( this == other){
            return true;
        }
        if(!(other instanceof Vector2d)){
            return false;
        }
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }
    public int hashcode(){
        return 1;
    }

    Vector2d opposite(){return new Vector2d(this.y, this.x);}
}

