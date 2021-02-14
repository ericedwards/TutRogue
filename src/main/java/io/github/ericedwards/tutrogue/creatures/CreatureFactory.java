package io.github.ericedwards.tutrogue.creatures;

import asciiPanel.AsciiPanel;
import io.github.ericedwards.tutrogue.world.World;

public class CreatureFactory {

    private World world;

    public CreatureFactory(World world){
        this.world = world;
    }

    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 100, 20, 5);
        world.addCreatureAtEmptyLocation(player);
        new PlayerAi(player);
        return player;
    }

    public Creature newFungus(){
        Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10, 0, 0);
        world.addCreatureAtEmptyLocation(fungus);
        new FungusAi(fungus, this);
        return fungus;
    }

    public Creature newDarkElf(){
        Creature fungus = new Creature(world, 'E', AsciiPanel.cyan, 50, 10, 10);
        world.addCreatureAtEmptyLocation(fungus);
        new FungusAi(fungus, this);
        return fungus;
    }
}
