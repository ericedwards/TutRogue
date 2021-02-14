package io.github.ericedwards.tutrogue.creatures;

import io.github.ericedwards.tutrogue.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class Creature {

    private World world;

    public int x;
    public int y;

    private int hp;
    private int maxHp;
    private int attackValue;
    private int defenseValue;
    private char glyph;
    private Color color;
    private CreatureAi creatureAi;

    private static final Logger logger = LoggerFactory.getLogger(Creature.class);

    public Creature(World world, char glyph, Color color, int maxHp, int attackValue, int defenseValue) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHp = maxHp;
        this.hp = this.maxHp;               // Start off with maxHp
        this.attackValue = attackValue;
        this.defenseValue = defenseValue;
    }

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

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    public void moveBy(int mx, int my) {
        Creature other = world.getCreatureAt(x + mx, y + my);
        if (other == null) {
            creatureAi.onEnter(x + mx, y + my, world.getTile(x + mx, y + my));
        } else {
            attack(other);
        }
    }

    public void attack(Creature other) {
        int amount = Math.max(0, attackValue - other.getDefenseValue());
        amount = (int) (Math.random() * amount) + 1;
        logger.debug("attacking for {}", amount);
        other.modifyHp(-amount);
        // notify("You attack the '%s' for %d damage.", other.glyph, amount);
        other.notify("The '%s' attacks you for %d damage.", glyph, amount);
        doAction("attack the '%s' for %d damage", other.glyph, amount);
    }

    public void modifyHp(int amount) {
        hp += amount;
        if (hp < 1) {
            doAction("die");
            world.removeCreature(this);
        }
    }

    public void notify(String message, Object... params) {
        creatureAi.onNotify(String.format(message, params));
    }

    public void update() {
        creatureAi.onUpdate();
    }

    public void doAction(String message, Object ... params){
        int r = 9;
        for (int ox = -r; ox < r+1; ox++){
            for (int oy = -r; oy < r+1; oy++){
                if (ox*ox + oy*oy > r*r)
                    continue;
                Creature other = world.getCreatureAt(x+ox, y+oy);
                if (other == null)
                    continue;
                if (other == this)
                    other.notify("You " + message + ".", params);
                else
                    other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);
            }
        }
    }

    private String makeSecondPerson(String text){
        String[] words = text.split(" ");
        words[0] = words[0] + "s";

        StringBuilder builder = new StringBuilder();
        for (String word : words){
            builder.append(" ");
            builder.append(word);
        }

        return builder.toString().trim();
    }

    public boolean canEnter(int wx, int wy) {
        return world.getTile(wx, wy).isGround() && world.getCreatureAt(wx, wy) == null;
    }
}
