package io.tidalisland.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;

/**
 * A mouse input manager designed for a real-time game loop. This class converts Swing events into
 * simple per-frame state: current mouse position, which mouse buttons are held, which were pressed
 * this frame, which were released this frame, scroll wheel movement for this frame
 */
public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

  // Current mouse coordinates relative to the component.
  private int mouseX = 0;
  private int mouseY = 0;

  // Stores whether each mouse button is currently held down (button id -> true/false).
  private final Map<Integer, Boolean> heldDown = new HashMap<>();

  // Records buttons that transitioned from up -> down this frame only.
  private final Map<Integer, Boolean> justPressed = new HashMap<>();

  // Records buttons that transitioned from down -> up frame only.
  private final Map<Integer, Boolean> justReleased = new HashMap<>();

  // How much the wheel moved this frame; positive = scrolled up, negative = down
  private int wheelDelta = 0;

  /**
   * Prepares the handler by registering known mouse buttons.
   */
  public MouseHandler() {
    heldDown.put(MouseEvent.BUTTON1, false); // left click
    heldDown.put(MouseEvent.BUTTON2, false); // middle click
    heldDown.put(MouseEvent.BUTTON3, false); // right click
  }

  public int getX() {
    return mouseX;
  }

  public int getY() {
    return mouseY;
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
   * Called by Swing when a mouse button goes down.
   */
  @Override
  public void mousePressed(MouseEvent e) {
    int button = e.getButton();

    // only flag justPressed if it wasn't already down
    if (!heldDown.get(button)) {
      justPressed.put(button, true);
    }

    heldDown.put(button, true);
  }

  /**
   * Called by Swing when a mouse button goes up.
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    int button = e.getButton();

    heldDown.put(button, false);
    justReleased.put(button, true);
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

  // The following three methods come from MouseListener, but this game doesn't use them (yet).

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}
}
