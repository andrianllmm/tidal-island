package io.tidalisland.worldbuilder.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Groups multiple actions into a single undoable unit (e.g., a brush stroke).
 */
public class StrokeAction implements EditorAction {

  private final List<EditorAction> actions = new ArrayList<>();

  public void addAction(EditorAction action) {
    actions.add(action);
  }

  public boolean isEmpty() {
    return actions.isEmpty();
  }

  @Override
  public void undo() {
    for (int i = actions.size() - 1; i >= 0; i--) {
      actions.get(i).undo();
    }
  }

  @Override
  public void redo() {
    for (EditorAction action : actions) {
      action.redo();
    }
  }
}
