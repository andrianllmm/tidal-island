package io.tidalisland.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles key events.
 */
public class KeyHandler implements KeyListener {
  private final Map<String, List<Integer>> keyBindings;
  private final Map<String, Boolean> actionStates;
  private final Map<String, Boolean> justPressed = new HashMap<>();

  /**
   * Creates a new key handler.
   */
  public KeyHandler() {
    keyBindings = Map.of("up", List.of(KeyEvent.VK_UP, KeyEvent.VK_W), "down",
        List.of(KeyEvent.VK_DOWN, KeyEvent.VK_S), "left", List.of(KeyEvent.VK_LEFT, KeyEvent.VK_A),
        "right", List.of(KeyEvent.VK_RIGHT, KeyEvent.VK_D), "interact", List.of(KeyEvent.VK_E),
        "toggle_inventory", List.of(KeyEvent.VK_I));

    actionStates = new HashMap<>();
    for (String action : keyBindings.keySet()) {
      actionStates.put(action, false);
    }
  }

  /**
   * Checks if a key is active.
   */
  public boolean isActive(String action) {
    return actionStates.getOrDefault(action, false);
  }

  /**
   * Checks if any of the given keys are active.
   */
  public boolean anyActive(String... actions) {
    return Arrays.stream(actions).anyMatch(actionStates::get);
  }

  /**
   * Checks if a key was just pressed.
   */
  public boolean isJustPressed(String action) {
    return justPressed.getOrDefault(action, false);
  }

  /**
   * Called once per frame.
   */
  public void endFrame() {
    justPressed.clear();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    keyBindings.forEach((action, keys) -> {
      if (keys.contains(e.getKeyCode())) {
        press(action);
      }
    });
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keyBindings.forEach((action, keys) -> {
      if (keys.contains(e.getKeyCode())) {
        release(action);
      }
    });
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  private void press(String action) {
    if (!actionStates.getOrDefault(action, false)) {
      justPressed.put(action, true);
    }
    actionStates.put(action, true);
  }

  private void release(String action) {
    actionStates.put(action, false);
  }
}
