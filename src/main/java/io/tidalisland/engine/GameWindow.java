package io.tidalisland.engine;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The game window.
 */
public class GameWindow extends Frame {
  /**
   * Creates a new game window.
   *
   * @param game the game
   * @param canvas the game canvas
   */
  public GameWindow(Game game, GameCanvas canvas) {
    super("Tidal Island");
    setResizable(true);
    setLocationRelativeTo(null);

    // Handle closing the window
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        if (game != null) {
          game.stop();
        }
        dispose();
        System.exit(0);
      }
    });

    // Add canvas
    add(canvas);
    pack();
    canvas.requestFocus();
  }
}
