package agh.ics.oop;

public enum Direction {
    Down(new Vector2d(0, -1)),
    Right(new Vector2d(1, 0)),
    Left(new Vector2d(-1, 0));

    private final Vector2d direction;
    Direction(Vector2d vector2d) {
        this.direction = vector2d;
    }

    public Vector2d getDirection() {
        return direction;
    }
}
