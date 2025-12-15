package io.tidalisland.worldbuilder.actions;

import io.tidalisland.worldbuilder.EditorState;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * Action for filling an area with a tile.
 */
public class FillAction implements EditorAction {

  private final EditorState state;
  private final Map<Point, Integer> oldValues;
  private final int newTileId;

  /** Creates a new fill action. */
  public FillAction(EditorState state, Map<Point, Integer> oldValues, int newTileId) {
    this.state = state;
    this.oldValues = new HashMap<>(oldValues);
    this.newTileId = newTileId;
  }

  @Override
  public void undo() {
    for (Map.Entry<Point, Integer> entry : oldValues.entrySet()) {
      Point p = entry.getKey();
      state.setTileId(p.x, p.y, entry.getValue());
    }
  }

  @Override
  public void redo() {
    for (Point p : oldValues.keySet()) {
      state.setTileId(p.x, p.y, newTileId);
    }
  }
}
