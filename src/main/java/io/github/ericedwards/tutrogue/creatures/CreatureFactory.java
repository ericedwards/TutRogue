package io.github.ericedwards.tutrogue.creatures;

import asciiPanel.AsciiPanel;
import io.github.ericedwards.tutrogue.world.World;

public class CreatureFactory {

    private World world;

    public CreatureFactory(World world){
        this.world = world;
    }

    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite);
        world.addAtEmptyLocation(player);
        new PlayerAi(player);
        return player;
    }

    public Creature newFungus(){
        Creature fungus = new Creature(world, 'f', AsciiPanel.green);
        world.addAtEmptyLocation(fungus);
        new FungusAi(fungus);
        return fungus;
    }
}