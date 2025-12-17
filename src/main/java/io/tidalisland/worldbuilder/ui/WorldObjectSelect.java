package io.tidalisland.worldbuilder.ui;

import io.tidalisland.worldbuilder.EditorState;
import io.tidalisland.worldobjects.WorldObject;
import io.tidalisland.worldobjects.WorldObjectRegistry;
import io.tidalisland.worldobjects.WorldObjectType;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A panel for selecting world objects.
 */
public class WorldObjectSelect extends JPanel {

  private static final int BUTTON_SIZE = 32;

  /** Creates a new world object select panel. */
  public WorldObjectSelect(EditorState state, ButtonGroup objGroup, ButtonGroup tileGroup) {
    setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
    setBackground(Color.BLACK);
    setAlignmentX(Component.LEFT_ALIGNMENT);

    for (WorldObjectType type : WorldObjectRegistry.getAllTypes()) {
      WorldObject obj = WorldObjectRegistry.create(type);

      ImageIcon icon = new ImageIcon(obj.getSpriteSet().getImage());
      CustomToggleButton btn = new CustomToggleButton(icon);
      btn.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));

      btn.addActionListener(e -> {
        state.setSelectedWorldObject(type);
        state.setSelectedTile(null);
        tileGroup.clearSelection();
      });

      objGroup.add(btn);
      add(btn);
    }
  }
}
