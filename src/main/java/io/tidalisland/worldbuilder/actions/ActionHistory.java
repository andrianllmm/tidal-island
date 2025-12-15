package io.tidalisland.worldbuilder.actions;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Manages undo/redo history for {@link EditorAction}s.
 */
public class ActionHistory {

  private final Deque<EditorAction> undoStack = new ArrayDeque<>();
  private final Deque<EditorAction> redoStack = new ArrayDeque<>();
  private static final int MAX_HISTORY = 100;

  private StrokeAction currentStroke;

  /** Starts a new stroke. */
  public void startStroke() {
    currentStroke = new StrokeAction();
  }

  /** Ends the current stroke. */
  public void endStroke() {
    if (currentStroke != null && !currentStroke.isEmpty()) {
      record(currentStroke);
      currentStroke = null;
    }
  }

  /** Adds an action to the current stroke. */
  public void addToStroke(EditorAction action) {
    if (currentStroke != null) {
      currentStroke.addAction(action);
    } else {
      record(action);
    }
  }

  /** Records an action for undo/redo. */
  public void record(EditorAction action) {
    undoStack.push(action);
    redoStack.clear();

    if (undoStack.size() > MAX_HISTORY) {
      undoStack.removeLast();
    }
  }

  /** Undoes the last action. */
  public void undo() {
    if (!undoStack.isEmpty()) {
      EditorAction action = undoStack.pop();
      action.undo();
      redoStack.push(action);
    }
  }

  /** Redoes the last undone action. */
  public void redo() {
    if (!redoStack.isEmpty()) {
      EditorAction action = redoStack.pop();
      action.redo();
      undoStack.push(action);
    }
  }

  public boolean canUndo() {
    return !undoStack.isEmpty();
  }

  public boolean canRedo() {
    return !redoStack.isEmpty();
  }
}
