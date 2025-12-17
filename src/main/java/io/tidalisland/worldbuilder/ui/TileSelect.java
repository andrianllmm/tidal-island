package io.tidalisland.worldbuilder.ui;

import io.tidalisland.tiles.Tile;
import io.tidalisland.tiles.TileSet;
import io.tidalisland.worldbuilder.EditorState;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A panel for selecting tiles.
 */
public class TileSelect extends JPanel {

  private static final int BUTTON_SIZE = 32;

  /** Creates a new tile select panel. */
  public TileSelect(EditorState state, ButtonGroup tileGroup, ButtonGroup objGroup) {
    setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
    setBackground(Color.BLACK);
    setAlignmentX(Component.LEFT_ALIGNMENT);

    TileSet tileSet = state.getTileSet();
    for (int i = 0; i < tileSet.size(); i++) {
      Tile tile = tileSet.get(i);

      ImageIcon icon = new ImageIcon(tile.getSprite().getImage());
      CustomToggleButton btn = new CustomToggleButton(icon);
      btn.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));

      int idx = i;
      btn.addActionListener(e -> {
        state.setSelectedTile(tileSet.get(idx));
        state.setSelectedWorldObject(null);
        objGroup.clearSelection();
      });

      tileGroup.add(btn);
      add(btn);
    }
  }
}
