package io.tidalisland.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages keyboard input for the game.
 */
public class KeyHandler implements KeyListener {

  /** Map of action names -> list of key codes. */
  private final Map<String, List<Integer>> keyBindings;

  /** Stores whether each action is currently held down (action name -> true/false). */
  private final Map<String, Boolean> heldDown;

  /** Records actions that were just pressed this frame. */
  private final Map<String, Boolean> justPressed = new HashMap<>();

  /**
   * Initializes default key bindings and state.
   */
  public KeyHandler() {
    keyBindings = Map.of("up", List.of(KeyEvent.VK_UP, KeyEvent.VK_W), "down",
        List.of(KeyEvent.VK_DOWN, KeyEvent.VK_S), "left", List.of(KeyEvent.VK_LEFT, KeyEvent.VK_A),
        "right", List.of(KeyEvent.VK_RIGHT, KeyEvent.VK_D), "interact", List.of(KeyEvent.VK_E),
        "toggle_inventory", List.of(KeyEvent.VK_I), "toggle_crafting", List.of(KeyEvent.VK_C),
        "pause", List.of(KeyEvent.VK_ESCAPE), "toggle_fullscreen", List.of(KeyEvent.VK_F));

    heldDown = new HashMap<>();
    for (String action : keyBindings.keySet()) {
      heldDown.put(action, false);
    }
  }

  /**
   * Returns true if the action is currently held down.
   */
  public boolean isDown(String action) {
    return heldDown.getOrDefault(action, false);
  }

  /**
   * Returns true if any of the given actions are currently held down.
   */
  public boolean anyDown(String... actions) {
    return Arrays.stream(actions).anyMatch(heldDown::get);
  }

  /**
   * Returns true only on the frame the action was pressed.
   */
  public boolean isJustPressed(String action) {
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
    keyBindings.forEach((action, keys) -> {
      if (keys.contains(e.getKeyCode())) {
        markPressed(action);
      }
    });
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keyBindings.forEach((action, keys) -> {
      if (keys.contains(e.getKeyCode())) {
        markReleased(action);
      }
    });
  }

  @Override
  public void keyTyped(KeyEvent e) {} // unused

  /** Marks an action as pressed. */
  private void markPressed(String action) {
    if (!heldDown.getOrDefault(action, false)) {
      justPressed.put(action, true);
    }
    heldDown.put(action, true);
  }

  /** Marks an action as released. */
  private void markReleased(String action) {
    heldDown.put(action, false);
  }
}
