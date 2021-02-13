package io.github.ericedwards.tutrogue;

import java.awt.*;

public class Creature {

    private World world;

    public int x;
    public int y;

    private char glyph;
    private Color color;
    private CreatureAi creatureAi;

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public CreatureAi getCreatureAi() {
        return creatureAi;
    }

    public void setCreatureAi(CreatureAi creatureAi) {
        this.creatureAi = creatureAi;
    }

    public Creature(World world, char glyph, Color color){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
    }

    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    public void moveBy(int mx, int my){
        creatureAi.onEnter(x+mx, y+my, world.getTile(x+mx, y+my));
    }
}
