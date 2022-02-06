package agh.ics.oop.playfield;

public class Line {
    private final static int width = 10;
    private final boolean[] tiles = new boolean[width];
    private int tilesOccupied;

    public Line(){
        tilesOccupied = 0;
        for (int i = 0; i < width; i++) {
            tiles[i] = false;
        }
    }

    public boolean isFull(){
        return tilesOccupied == width;
    }

    public boolean isTileOccupied(int tileX){
        return tiles[tileX];
    }

    public void placeMonomino(int monominoX) {
        tiles[monominoX] = true;
        tilesOccupied++;
    }
}
