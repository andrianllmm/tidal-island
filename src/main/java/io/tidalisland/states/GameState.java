package io.tidalisland.states;

import java.awt.Graphics;

/**
 * Represents a state in the game.
 */
public interface GameState {
  /**
   * Called when the state is entered.
   */
  void onEnter();

  /**
   * Called when the state is exited.
   */
  void onExit();

  /**
   * Updates the state.
   */
  void update();

  /**
   * Renders the state.
   *
   * @param g the graphics context
   */
  void render(Graphics g);
}
