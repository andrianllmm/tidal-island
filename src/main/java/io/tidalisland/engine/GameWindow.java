package io.tidalisland.engine;

import io.tidalisland.config.Config;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The game window.
 */
public class GameWindow extends Frame {

  private final GameCanvas canvas;
  private final GraphicsDevice graphicsDevice;
  private boolean isFullscreen = false;

  /**
   * Creates a new game window.
   */
  public GameWindow(Game game, GameCanvas canvas) {
    super("Tidal Island");
    this.canvas = canvas;
    this.graphicsDevice =
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    add(canvas);
    pack();
    canvas.requestFocus();

    setResizable(true);
    setLocationRelativeTo(null);

    // Handle closing the window
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        exitFullscreen();
        if (game != null) {
          game.stop();
        }
        dispose();
        System.exit(0);
      }
    });

    // Handle window resize
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        if (!isFullscreen) {
          Config.get().updateScreenDimensions(canvas.getWidth(), canvas.getHeight());
        }
      }
    });
  }

  /**
   * Toggles fullscreen mode.
   */
  public void toggleFullscreen() {
    if (isFullscreen) {
      exitFullscreen();
    } else {
      enterFullscreen();
    }
  }

  /**
   * Enters fullscreen mode.
   */
  private void enterFullscreen() {
    if (!graphicsDevice.isFullScreenSupported()) {
      System.err.println("Fullscreen not supported on this device");
      return;
    }

    try {
      dispose();
      setUndecorated(true);
      setResizable(false);

      graphicsDevice.setFullScreenWindow(this);

      int[] dimensions = Config.getFullscreenDimensions();
      canvas.setSize(dimensions[0], dimensions[1]); // resize canvas
      Config.get().updateScreenDimensions(dimensions[0], dimensions[1]);

      validate();
      repaint();

      isFullscreen = true;
      setVisible(true);
      canvas.requestFocus();
    } catch (Exception e) {
      System.err.println("Failed to enter fullscreen: " + e.getMessage());
      exitFullscreen();
    }
  }


  /**
   * Exits fullscreen mode.
   */
  private void exitFullscreen() {
    if (isFullscreen) {
      try {
        graphicsDevice.setFullScreenWindow(null);
        dispose();
        setUndecorated(false);
        setResizable(true);

        canvas.setSize(Config.screenWidth(), Config.screenHeight());

        validate();
        repaint();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        canvas.requestFocus();
      } catch (Exception e) {
        System.err.println("Failed to exit fullscreen: " + e.getMessage());
      }
    }
  }

  public boolean isFullscreen() {
    return isFullscreen;
  }
}
