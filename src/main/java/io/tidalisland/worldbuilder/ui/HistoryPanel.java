package io.tidalisland.worldbuilder.ui;

import io.tidalisland.worldbuilder.EditorState;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * A panel for undo/redo buttons.
 */
public class HistoryPanel extends JPanel {

  /** Creates a new history panel. */
  public HistoryPanel(EditorState state) {
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    setBackground(Color.BLACK);
    setAlignmentX(Component.LEFT_ALIGNMENT);

    add(new CustomButton("Undo", state::undo));
    add(new CustomButton("Redo", state::redo));
  }
}
