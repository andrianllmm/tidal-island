package io.tidalisland.ui;

import io.tidalisland.inventory.Inventory;
import io.tidalisland.items.Item;
import io.tidalisland.items.ItemRegistry;
import io.tidalisland.ui.components.UiGridPanel;
import io.tidalisland.ui.components.UiImage;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Inventory window UI.
 */
public class UiInventoryPanel extends UiPanel {
  private final Inventory inventory;
  private final UiGridPanel grid;

  /**
   * Initializes a new inventory panel.
   */
  public UiInventoryPanel(Inventory inventory, int x, int y) {
    super(x, y);
    this.inventory = inventory;

    grid = new UiGridPanel(10, 10, 64, 64, 5, 5, 8);
    width = grid.getWidth();
    height = grid.getHeight();

    setBorder(Color.WHITE, 4);
    setRounded(true, 32);

    add(grid);

    setVisible(false);
    refresh();
  }

  /**
   * Rebuilds the grid based on inventory contents.
   */
  public void refresh() {
    if (!inventory.isDirty()) {
      return;
    }

    grid.clear();

    for (var entry : inventory.view().entrySet()) {
      String id = entry.getKey();
      int qty = entry.getValue();

      Item item = ItemRegistry.get(id);

      UiPanel slot = new UiPanel(0, 0, 0, 0, null);
      grid.add(slot);

      UiImage icon = new UiImage(item.getSprite().getFrame().getImage(), 0, 0);
      slot.add(icon, true);

      UiLabel label = new UiLabel(String.valueOf(qty));
      slot.add(label, true);
    }

    inventory.clearDirty();
  }

  /**
   * Updates the inventory panel.
   */
  @Override
  public void update() {
    if (!isVisible()) {
      return;
    }
    refresh();
  }

  /**
   * Renders the inventory panel.
   */
  @Override
  public void render(Graphics2D g) {
    if (!isVisible()) {
      return;
    }
    super.render(g);
  }
}
