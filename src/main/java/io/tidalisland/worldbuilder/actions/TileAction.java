package io.tidalisland.worldbuilder.actions;

import io.tidalisland.worldbuilder.EditorState;

/**
 * Action for placing or removing a single tile.
 */
public class TileAction implements EditorAction {
  private final EditorState state;
  private final int col;
  private final int row;
  private final int oldTileId;
  private final int newTileId;

  /** Creates a new tile action. */
  public TileAction(EditorState state, int col, int row, int oldTileId, int newTileId) {
    this.state = state;
    this.col = col;
    this.row = row;
    this.oldTileId = oldTileId;
    this.newTileId = newTileId;
  }

  @Override
  public void undo() {
    state.setTileId(col, row, oldTileId);
  }

  @Override
  public void redo() {
    state.setTileId(col, row, newTileId);
  }
}