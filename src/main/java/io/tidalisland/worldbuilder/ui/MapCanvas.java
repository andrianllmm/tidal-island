package io.tidalisland.worldbuilder.ui;

import io.tidalisland.tiles.Tile;
import io.tidalisland.utils.Position;
import io.tidalisland.worldbuilder.EditorState;
import io.tidalisland.worldbuilder.ViewPort;
import io.tidalisland.worldbuilder.actions.FillAction;
import io.tidalisland.worldbuilder.actions.TileAction;
import io.tidalisland.worldbuilder.actions.WorldObjectAction;
import io.tidalisland.worldobjects.WorldObject;
import io.tidalisland.worldobjects.WorldObjectRegistry;
import io.tidalisland.worldobjects.WorldObjectType;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Main drawing canvas for the World Builder.
 */
public class MapCanvas extends JPanel {

  private final EditorState state;

  /** Creates a new map canvas. */
  public MapCanvas(EditorState state) {
    this.state = state;
    setBackground(Color.BLACK);

    state.addChangeListener(this::repaint);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        state.setDrawing(true);
        state.getActionHistory().startStroke();
        handleClick(e);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        state.setDrawing(false);
        state.getActionHistory().endStroke();
      }
    });

    addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        handleClick(e);
      }
    });

    addMouseWheelListener(e -> {
      if (e.getWheelRotation() < 0) {
        state.getViewPort().zoom(-1);
      } else {
        state.getViewPort().zoom(1);
      }
      repaint();
    });
  }

  private int tileSize() {
    ViewPort vp = state.getViewPort();
    return Math.min(getWidth() / vp.getCols(), getHeight() / vp.getRows());
  }

  private void handleClick(MouseEvent e) {
    int ts = tileSize();
    ViewPort vp = state.getViewPort();
    int col = e.getX() / ts + vp.getX();
    int row = e.getY() / ts + vp.getY();

    if (!state.isInBounds(col, row)) {
      return;
    }

    Position tilePos = new Position(col, row);

    if (SwingUtilities.isLeftMouseButton(e)) {
      // Paint
      if (state.getSelectedTile() != null) {
        // Paint with tiles
        int newId = state.getTileSet().getAll().indexOf(state.getSelectedTile());

        if (state.isFillEnabled()) {
          // Fill
          fill(col, row, newId);
          state.getActionHistory().endStroke();

        } else {
          // Paint
          int oldId = state.getTileId(col, row);
          if (oldId != newId) {
            state.setTileId(col, row, newId);
            state.getActionHistory().addToStroke(new TileAction(state, col, row, oldId, newId));
            state.notifyChange();
          }
        }
      } else if (state.getSelectedWorldObject() != null) {
        // Paint with world objects
        WorldObjectType oldType = state.getWorldObject(tilePos);
        WorldObjectType selType = state.getSelectedWorldObject();

        state.setWorldObject(tilePos, selType);
        state.getActionHistory()
            .addToStroke(new WorldObjectAction(state, tilePos, oldType, selType));
        state.notifyChange();
      }

    } else if (SwingUtilities.isRightMouseButton(e)) {
      // Erase
      if (state.getSelectedTile() != null) {
        // Erase only tiles if a tile is selected
        int oldTile = state.getTileId(col, row);
        if (oldTile != -1) {
          state.setTileId(col, row, -1);
          state.getActionHistory().addToStroke(new TileAction(state, col, row, oldTile, -1));
          state.notifyChange();
        }
      } else if (state.getSelectedWorldObject() != null) {
        // Erase onWorldObjectTypen object is selected
        WorldObjectType oldObj = state.getWorldObject(tilePos);
        if (oldObj != null) {
          state.setWorldObject(tilePos, null);
          state.getActionHistory().addToStroke(new WorldObjectAction(state, tilePos, oldObj, null));
          state.notifyChange();
        }
      }
    }

    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());

    int ts = tileSize();
    ViewPort vp = state.getViewPort();

    for (int y = 0; y < vp.getRows(); y++) {
      for (int x = 0; x < vp.getCols(); x++) {
        int mapX = x + vp.getX();
        int mapY = y + vp.getY();

        if (!state.isInBounds(mapX, mapY)) {
          continue;
        }

        // Pixel position on the canvas
        int pixelX = x * ts;
        int pixelY = y * ts;

        // Draw tile
        Tile tile = state.getTile(mapX, mapY);
        if (tile != null) {
          tile.getSprite().getFrame().drawScaled(g, pixelX, pixelY, ts, ts);
        }

        // Draw world object
        Position tilePos = new Position(mapX, mapY);
        WorldObjectType objType = state.getWorldObject(tilePos);
        if (objType != null) {
          WorldObject obj = WorldObjectRegistry.create(objType, tilePos);
          if (obj.getSpriteSet() != null) {
            obj.getSpriteSet().getFrame().drawScaled(g, pixelX, pixelY, ts, ts);
          }
        }

        // Draw grid
        g.setColor(Color.DARK_GRAY);
        g.drawRect(pixelX, pixelY, ts, ts);
      }
    }
  }

  /** Fills the map starting at a given position. */
  private void fill(int startX, int startY, int newTileId) {
    int targetId = state.getTileId(startX, startY);
    if (targetId == newTileId) {
      return; // Nothing to do if filling with same tile
    }

    Map<Point, Integer> oldValues = new HashMap<>();
    Deque<Point> stack = new ArrayDeque<>();
    stack.push(new Point(startX, startY));

    while (!stack.isEmpty()) {
      Point p = stack.pop();
      int x = p.x;
      int y = p.y;

      if (!state.isInBounds(x, y)) {
        continue;
      }

      int currentId = state.getTileId(x, y);
      if (currentId != targetId) {
        continue; // Only fill tiles that match the original
      }

      oldValues.put(new Point(x, y), currentId);
      state.setTileId(x, y, newTileId);

      stack.push(new Point(x + 1, y));
      stack.push(new Point(x - 1, y));
      stack.push(new Point(x, y + 1));
      stack.push(new Point(x, y - 1));
    }

    if (!oldValues.isEmpty()) {
      state.getActionHistory().addToStroke(new FillAction(state, oldValues, newTileId));
    }
  }

}
