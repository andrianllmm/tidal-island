package io.tidalisland.worldbuilder.ui;

import io.tidalisland.worldbuilder.EditorState;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Collections;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * Sidebar containing tiles, objects, fill toggle, and minimap.
 */
public class ToolPanel extends JPanel {
  /** Creates a new tool panel. */
  public ToolPanel(EditorState state, MiniMapPanel miniMap, Runnable saveCallback) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBackground(Color.BLACK);

    // Add minimap
    miniMap.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(miniMap);
    addVerticalGap();

    ButtonGroup tileGroup = new ButtonGroup();
    ButtonGroup objGroup = new ButtonGroup();

    CustomToggleButton fillBtn = new CustomToggleButton("Fill");
    fillBtn.addActionListener(e -> state.setFillEnabled(fillBtn.isSelected()));

    // Add tiles
    addLabel("Tiles");
    add(fillBtn);
    addVerticalGap();

    add(new TileSelect(state, tileGroup, objGroup));
    addVerticalGap();

    // Add world objects
    addLabel("World Objects");
    addVerticalGap();

    add(new WorldObjectSelect(state, objGroup, tileGroup));
    addVerticalGap();

    // Add history
    add(new HistoryPanel(state));
    addVerticalGap();

    // Add save button
    add(new CustomButton("Save", saveCallback));
  }

  /** Adds a label. */
  private void addLabel(String text) {
    JLabel label = new JLabel(text);
    label.setForeground(Color.WHITE);
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(label);
  }

  /** Adds a vertical gap. */
  private void addVerticalGap() {
    add(Box.createVerticalStrut(5));
  }

  /** Clears all buttons in a button group except one. */
  private void clearObjectSelectionExcept(ButtonGroup objGroup, JToggleButton except) {
    for (AbstractButton b : Collections.list(objGroup.getElements())) {
      if (b != except) {
        b.setSelected(false);
      }
    }
  }
}
