package io.tidalisland.worldbuilder;

import io.tidalisland.worldbuilder.ui.WorldBuilderFrame;
import javax.swing.SwingUtilities;

/**
 * Main entry point for the World Builder app.
 */
public class WorldBuilder {

  /** Runs the world builder app. */
  public static void main(String[] args) {
    String tilesetPath = args.length > 0 ? args[0] : "/tilesets/tileset.json";

    SwingUtilities.invokeLater(() -> new WorldBuilderFrame(tilesetPath));
  }
}
