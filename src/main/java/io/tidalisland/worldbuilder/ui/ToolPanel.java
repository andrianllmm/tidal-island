package io.tidalisland.worldbuilder.ui;

import io.tidalisland.worldbuilder.EditorState;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

    // Add tiles
    addLabel("Tiles");
    CustomToggleButton fillBtn = new CustomToggleButton("Fill");
    fillBtn.addActionListener(e -> state.setFillEnabled(fillBtn.isSelected()));
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
}
