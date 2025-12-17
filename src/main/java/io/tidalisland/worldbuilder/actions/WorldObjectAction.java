package io.tidalisland.worldbuilder.actions;

import io.tidalisland.utils.Position;
import io.tidalisland.worldbuilder.EditorState;
import io.tidalisland.worldobjects.WorldObjectType;

/**
 * Action for placing or removing a world object.
 */
public class WorldObjectAction implements EditorAction {

  private final EditorState state;
  private final Position position;
  private final WorldObjectType oldObjectId;
  private final WorldObjectType newObjectId;

  /** Creates a new world object action. */
  public WorldObjectAction(EditorState state, Position position, WorldObjectType oldObjectId,
      WorldObjectType newObjectId) {
    this.state = state;
    this.position = position;
    this.oldObjectId = oldObjectId;
    this.newObjectId = newObjectId;
  }

  @Override
  public void undo() {
    state.setWorldObject(position, oldObjectId);
  }

  @Override
  public void redo() {
    state.setWorldObject(position, newObjectId);
  }
}
