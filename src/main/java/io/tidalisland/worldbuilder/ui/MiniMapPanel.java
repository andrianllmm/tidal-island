package io.tidalisland.worldbuilder.ui;

import io.tidalisland.tiles.Tile;
import io.tidalisland.utils.Position;
import io.tidalisland.worldbuilder.EditorState;
import io.tidalisland.worldbuilder.ViewPort;
import io.tidalisland.worldobjects.WorldObject;
import io.tidalisland.worldobjects.WorldObjectRegistry;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 * Small overview panel showing entire map with tiles and world objects.
 */
public class MiniMapPanel extends JPanel {

  private final EditorState state;
  private final MapCanvas canvas;

  /** Creates a new minimap panel. */
  public MiniMapPanel(EditorState state, MapCanvas canvas) {
    this.state = state;
    this.canvas = canvas;

    setBackground(Color.BLACK);
    setPreferredSize(new Dimension(180, 180));

    state.addChangeListener(this::repaint);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        moveView(e.getX(), e.getY());
      }
    });

    addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        moveView(e.getX(), e.getY());
      }
    });
  }

  private void moveView(int mouseX, int mouseY) {
    int size = Math.min(getWidth(), getHeight());
    int mapWidth = state.getMapWidth();
    int mapHeight = state.getMapHeight();
    double tileSize = (double) size / Math.max(mapWidth, mapHeight);

    int col = (int) (mouseX / tileSize) - state.getViewPort().getCols() / 2;
    int row = (int) (mouseY / tileSize) - state.getViewPort().getRows() / 2;
    state.getViewPort().moveTo(col, row);

    canvas.repaint();
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int size = Math.min(getWidth(), getHeight());
    int mapWidth = state.getMapWidth();
    int mapHeight = state.getMapHeight();
    double ts = (double) size / Math.max(mapWidth, mapHeight);

    // Draw tiles
    for (int y = 0; y < mapHeight; y++) {
      for (int x = 0; x < mapWidth; x++) {
        Tile tile = state.getTile(x, y);
        if (tile != null) {
          tile.getSprite().getFrame().drawScaled(g, (int) (x * ts), (int) (y * ts), (int) ts,
              (int) ts);
        }

        // Draw world objects
        String objId = state.getWorldObject(new Position(x, y));
        if (objId != null) {
          WorldObject obj = WorldObjectRegistry.create(objId, new Position(x, y));
          if (obj.getSpriteSet() != null) {
            obj.getSpriteSet().getFrame().drawScaled(g, (int) (x * ts), (int) (y * ts), (int) ts,
                (int) ts);
          }
        }
      }
    }

    // Draw viewport rectangle
    g.setColor(Color.YELLOW);
    ViewPort vp = state.getViewPort();
    g.drawRect((int) (vp.getX() * ts), (int) (vp.getY() * ts), (int) (vp.getCols() * ts),
        (int) (vp.getRows() * ts));
  }
}
