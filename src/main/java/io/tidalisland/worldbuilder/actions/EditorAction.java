package io.tidalisland.worldbuilder.actions;

/**
 * Represents a reversible action in the editor.
 */
public interface EditorAction {

  /** Undoes the action. */
  void undo();

  /** Redoes the action. */
  void redo();
}
