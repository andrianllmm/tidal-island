package io.tidalisland.worldbuilder.actions;

/**
 * Represents a reversible action in the editor.
 */
public interface EditorAction {
  void undo();

  void redo();
}