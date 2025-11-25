package io.tidalisland.ui;

import io.tidalisland.engine.KeyHandler;
import io.tidalisland.inventory.Inventory;
import java.awt.Graphics2D;

/**
 * Manages all UI elements.
 */
public class UiManager {
  private UiInventoryPanel inventoryPanel;

  public UiManager(Inventory inventory) {
    inventoryPanel = new UiInventoryPanel(inventory, 20, 20);
  }

  public UiInventoryPanel getInventoryPanel() {
    return inventoryPanel;
  }

  /**
   * Updates UI state.
   */
  public void update(KeyHandler keyH) {
    inventoryPanel.update();
    if (keyH.isJustPressed("toggle_inventory")) {
      inventoryPanel.setVisible(!inventoryPanel.isVisible());
    }
  }

  /**
   * Renders UI.
   */
  public void render(Graphics2D g) {
    inventoryPanel.render(g);
  }
}
