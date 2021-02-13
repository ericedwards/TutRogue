package io.github.ericedwards.tutrogue;

import java.awt.*;

public class World {

    private Tile[][] tiles;
    private int width;
    private int height;

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[x][y];
    }

    public char getGlyph(int x, int y) {
        return getTile(x, y).glyph();
    }

    public Color getColor(int x, int y) {
        return getTile(x, y).color();
    }

    public void dig(int x, int y) {
        if (getTile(x, y).isDiggable())
            tiles[x][y] = Tile.FLOOR;
    }

    public void addAtEmptyLocation(Creature creature){
        int x;
        int y;

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        }
        while (!getTile(x,y).isGround());

        creature.x = x;
        creature.y = y;
    }
}
