package io.tidalisland.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages mouse input for the game.
 */
public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
  // Mouse button IDs
  /** Left mouse button. */
  public static final int BTN_LEFT = 1;
  /** Right mouse button. */
  public static final int BTN_RIGHT = 2;
  /** Middle mouse button. */
  public static final int BTN_MIDDLE = 3;

  /** Current mouse coordinates relative to the component. */
  private int mouseX = 0;
  private int mouseY = 0;

  /** Stores whether each mouse button is currently held down (button id -> true/false). */
  private final Map<Integer, Boolean> heldDown = new HashMap<>();

  /** Records buttons that transitioned from up -> down this frame only. */
  private final Map<Integer, Boolean> justPressed = new HashMap<>();

  /** Records buttons that transitioned from down -> up frame only. */
  private final Map<Integer, Boolean> justReleased = new HashMap<>();

  /** Where each button was pressed. */
  private final Map<Integer, Integer> pressX = new HashMap<>();
  private final Map<Integer, Integer> pressY = new HashMap<>();

  /** Where each button was released (valid only on isJustReleased frame). */
  private final Map<Integer, Integer> releaseX = new HashMap<>();
  private final Map<Integer, Integer> releaseY = new HashMap<>();

  /** How much the wheel moved this frame; positive = scrolled up, negative = down. */
  private int wheelDelta = 0;

  /**
   * Prepares the handler by registering known mouse buttons.
   */
  public MouseHandler() {
    heldDown.put(BTN_LEFT, false);
    heldDown.put(BTN_RIGHT, false);
    heldDown.put(BTN_MIDDLE, false);
  }

  /**
   * Returns true while a given button is held down.
   */
  public boolean isDown(int button) {
    return heldDown.getOrDefault(button, false);
  }

  /**
   * Returns true only on the first frame the button is pressed.
   */
  public boolean isJustPressed(int button) {
    return justPressed.getOrDefault(button, false);
  }

  /**
   * Returns true only on the first frame after the button is released.
   */
  public boolean isJustReleased(int button) {
    return justReleased.getOrDefault(button, false);
  }

  /**
   * Wheel movement recorded this frame. Resets every frame.
   */
  public int getWheelDelta() {
    return wheelDelta;
  }

  /**
   * Must be called at the end of each frame.
   */
  public void endFrame() {
    justPressed.clear(); // only true on the exact frame they happened
    justReleased.clear(); // same logic as above
    wheelDelta = 0; // reset scroll movement for next frame
  }

  /**
   * Called when a mouse button goes down.
   */
  @Override
  public void mousePressed(MouseEvent e) {
    int button = e.getButton();

    if (!heldDown.get(button)) {
      justPressed.put(button, true);
      pressX.put(button, e.getX());
      pressY.put(button, e.getY());
    }

    heldDown.put(button, true);
  }

  /**
   * Called when a mouse button goes up.
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    int button = e.getButton();

    heldDown.put(button, false);
    justReleased.put(button, true);

    releaseX.put(button, e.getX());
    releaseY.put(button, e.getY());
  }

  /**
   * Updates mouse position when the mouse moves without holding a button.
   */
  @Override
  public void mouseMoved(MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();
  }

  /**
   * Dragging = movement while a button is held. Position update is identical to mouseMoved.
   */
  @Override
  public void mouseDragged(MouseEvent e) {
    mouseMoved(e);
  }

  /**
   * Captures scroll wheel movement. wheelRotation = number of "notches" scrolled.
   */
  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    wheelDelta += e.getWheelRotation();
  }

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  public int getX() {
    return mouseX;
  }

  public int getY() {
    return mouseY;
  }

  public int getPressX(int button) {
    return pressX.getOrDefault(button, mouseX);
  }

  public int getPressY(int button) {
    return pressY.getOrDefault(button, mouseY);
  }

  public int getReleaseX(int button) {
    return releaseX.getOrDefault(button, mouseX);
  }

  public int getReleaseY(int button) {
    return releaseY.getOrDefault(button, mouseY);
  }

  /**
   * True if the button was pressed and released within the same frame interval.
   */
  public boolean isClick(int button) {
    return isJustReleased(button) && pressX.containsKey(button);
  }
}
