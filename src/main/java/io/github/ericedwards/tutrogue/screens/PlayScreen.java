package io.github.ericedwards.tutrogue.screens;

import asciiPanel.AsciiPanel;
import io.github.ericedwards.tutrogue.creatures.Creature;
import io.github.ericedwards.tutrogue.creatures.CreatureFactory;
import io.github.ericedwards.tutrogue.world.World;
import io.github.ericedwards.tutrogue.world.WorldBuilder;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen {

    private World world;
    private final int screenWidth;
    private final int screenHeight;
    private Creature player;
    private final List<String> messages;

    public PlayScreen() {
        screenWidth = 80;
        screenHeight = 21;
        createWorld();
        messages = new ArrayList<>();
        messages.add("You are having fun.");
        CreatureFactory creatureFactory = new CreatureFactory(world);
        createCreatures(creatureFactory, messages);
    }

    private void createCreatures(CreatureFactory creatureFactory, List<String> messages) {
        player = creatureFactory.newPlayer(messages);
        for (int i = 0; i < 8; i++) {
            creatureFactory.newFungus();
        }
        creatureFactory.newDarkElf();
    }

    private void createWorld() {
        world = new WorldBuilder(90, 31)
                .makeCaves()
                .build();
    }

    public int getScrollX() {
        return Math.max(0,
                Math.min(player.x - screenWidth / 2, world.getWidth() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0,
                Math.min(player.y - screenHeight / 2, world.getHeight() - screenHeight));
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;
                terminal.write(world.getGlyph(wx, wy), x, y, world.getColor(wx, wy));
            }
        }
        for (Creature creature : world.getCreatures()) {
            if (creature.x >= left && creature.x < (left + screenWidth)
                    && creature.y >= top && creature.y < (top + screenHeight)) {
                terminal.write(creature.getGlyph(),
                        creature.x - left, creature.y - top, creature.getColor());
            }
        }
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages) {
        int top = screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++){
            terminal.writeCenter(messages.get(i), top + i);
        }
        messages.clear();
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();
        displayTiles(terminal, left, top);
        displayMessages(terminal, messages);
        String stats = String.format(" %3d/%3d hp", player.getHp(), player.getMaxHp());
        terminal.write(stats, 1, 23);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H:
                player.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                player.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K:
                player.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J:
                player.moveBy(0, 1);
                break;
            case KeyEvent.VK_Y:
                player.moveBy(-1, -1);
                break;
            case KeyEvent.VK_U:
                player.moveBy(1, -1);
                break;
            case KeyEvent.VK_B:
                player.moveBy(-1, 1);
                break;
            case KeyEvent.VK_N:
                player.moveBy(1, 1);
                break;
            case KeyEvent.VK_ESCAPE:
                return new LoseScreen();
            case KeyEvent.VK_ENTER:
                return new WinScreen();
        }
        world.update();
        return this;
    }
}
