package io.tidalisland.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages keyboard input for the game.
 */
public class KeyHandler implements KeyListener {

  /** Map of actions -> key codes that trigger them. */
  private final KeyBindings keyBindings;

  /** Stores whether each action is currently held down (action name -> true/false). */
  private final Map<Action, Boolean> heldDown;

  /** Records actions that were just pressed this frame. */
  private final Map<Action, Boolean> justPressed = new HashMap<>();

  /**
   * Initializes default key bindings and state.
   */
  public KeyHandler() {
    keyBindings = KeyBindingsLoader.load("/keybindings/keybindings.json");

    heldDown = new HashMap<>();
    for (Action action : keyBindings.getActions()) {
      heldDown.put(action, false);
    }
  }

  /**
   * Returns true if the action is currently held down.
   */
  public boolean isDown(Action action) {
    return heldDown.getOrDefault(action, false);
  }

  /**
   * Returns true if any of the given actions are currently held down.
   */
  public boolean anyDown(Action... actions) {
    return Arrays.stream(actions).anyMatch(heldDown::get);
  }

  /**
   * Returns true only on the frame the action was pressed.
   */
  public boolean isJustPressed(Action action) {
    return justPressed.getOrDefault(action, false);
  }

  /**
   * Must be called at the end of each frame.
   */
  public void endFrame() {
    justPressed.clear();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    for (Action action : keyBindings.getActions()) {
      if (keyBindings.has(action, e.getKeyCode())) {
        markPressed(action);
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    for (Action action : keyBindings.getActions()) {
      if (keyBindings.has(action, e.getKeyCode())) {
        markReleased(action);
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {} // unused

  /** Marks an action as pressed. */
  private void markPressed(Action action) {
    if (!heldDown.getOrDefault(action, false)) {
      justPressed.put(action, true);
    }
    heldDown.put(action, true);
  }

  /** Marks an action as released. */
  private void markReleased(Action action) {
    heldDown.put(action, false);
  }
}
