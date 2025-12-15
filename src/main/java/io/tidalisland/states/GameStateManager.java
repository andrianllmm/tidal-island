package io.tidalisland.states;

import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Manages the game state.
 */
public class GameStateManager {

  private final Deque<GameState> states = new ArrayDeque<>();

  /**
   * Pushes a new state onto the stack.
   *
   * @param state the state
   */
  public void push(GameState state) {
    states.push(state);
    state.onEnter();
  }

  /**
   * Pops the current state off the stack.
   *
   * @returns the popped state
   */
  public void pop() {
    if (!states.isEmpty()) {
      states.pop().onExit();
    }
  }

  /**
   * Peeks at the current state.
   *
   * @returns the current state
   */
  public GameState peek() {
    return states.peek();
  }

  /**
   * Updates the state.
   */
  public void update() {
    if (!states.isEmpty()) {
      states.peek().update();
    }
  }

  /**
   * Renders the state.
   */
  public void render(Graphics g) {
    Iterator<GameState> it = states.descendingIterator();
    while (it.hasNext()) {
      it.next().render(g);
    }
  }
}
