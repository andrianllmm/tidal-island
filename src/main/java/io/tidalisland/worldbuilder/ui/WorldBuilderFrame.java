package io.tidalisland.worldbuilder.ui;

import io.tidalisland.config.Config;
import io.tidalisland.tiles.TileSet;
import io.tidalisland.tiles.TileSetLoader;
import io.tidalisland.worldbuilder.EditorState;
import io.tidalisland.worldbuilder.WorldExporter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Main frame for the World Builder application.
 */
public class WorldBuilderFrame extends JFrame {
  private final String tilesetPath;
  private final EditorState editorState;
  private final MapCanvas mapCanvas;
  private final MiniMapPanel miniMapPanel;
  private final ToolPanel toolPanel;

  /** Creates a new world builder frame. */
  public WorldBuilderFrame(String tilesetPath) {
    this.tilesetPath = tilesetPath;
    TileSet tileSet = TileSetLoader.load(tilesetPath);

    this.editorState = new EditorState(
        Config.mapWidth(),
        Config.mapHeight(),
        tileSet);

    setTitle("World Builder");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Create main canvas
    mapCanvas = new MapCanvas(editorState);
    mapCanvas.setPreferredSize(new Dimension(800, 800));
    add(mapCanvas, BorderLayout.CENTER);

    // Create minimap
    miniMapPanel = new MiniMapPanel(editorState, mapCanvas);

    // Create tool panel
    toolPanel = new ToolPanel(editorState, miniMapPanel, this::saveMap);
    add(toolPanel, BorderLayout.EAST);

    // Add listener
    editorState.addChangeListener(() -> {
      mapCanvas.repaint();
      miniMapPanel.repaint();
    });

    // Setup keyboard shortcuts
    setupKeyboardShortcuts();

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void setupKeyboardShortcuts() {
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
      if (e.getID() == KeyEvent.KEY_PRESSED) {
        boolean ctrlDown = (e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0;

        if (ctrlDown && e.getKeyCode() == KeyEvent.VK_Z) {
          editorState.undo();
          repaintAll();
          return true;
        } else if (ctrlDown && e.getKeyCode() == KeyEvent.VK_Y) {
          editorState.redo();
          repaintAll();
          return true;
        } else if (ctrlDown && e.getKeyCode() == KeyEvent.VK_S) {
          saveMap();
          return true;
        }
      }
      return false;
    });
  }

  private void saveMap() {
    if (!isMapComplete()) {
      JOptionPane.showMessageDialog(
          this,
          "Cannot save: some tiles are not set.",
          "Save Error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      WorldExporter.export(editorState, tilesetPath);
      JOptionPane.showMessageDialog(this, "Saved!");
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(
          this,
          "Failed to save: " + e.getMessage(),
          "Save Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private boolean isMapComplete() {
    for (int y = 0; y < editorState.getMapHeight(); y++) {
      for (int x = 0; x < editorState.getMapWidth(); x++) {
        if (editorState.getTileId(x, y) == -1) {
          return false;
        }
      }
    }
    return true;
  }

  private void repaintAll() {
    mapCanvas.repaint();
    miniMapPanel.repaint();
  }
}
