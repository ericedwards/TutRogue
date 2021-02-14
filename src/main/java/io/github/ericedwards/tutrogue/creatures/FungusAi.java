package io.github.ericedwards.tutrogue.creatures;

public class FungusAi extends CreatureAi {

    private CreatureFactory creatureFactory;
    private int spreadCount;

    public FungusAi(Creature creature, CreatureFactory creatureFactory) {
        super(creature);
        this.creatureFactory = creatureFactory;
    }

    @Override
    public void onUpdate() {
        if (spreadCount < 2 && Math.random() < 0.01) {
            spread();
        }
    }

    private void spread() {
        int x = creature.x + (int)(Math.random() * 7) - 3;
        int y = creature.y + (int)(Math.random() * 7) - 3;
        if (!creature.canEnter(x, y))
            return;
        Creature child = creatureFactory.newFungus();
        child.x = x;
        child.y = y;
        spreadCount++;
        creature.doAction("spawn a child");
    }
}
