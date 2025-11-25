package io.tidalisland.ui;

import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.inventory.Inventory;
import java.awt.Graphics2D;

/**
 * Manages all UI elements.
 */
public class UiManager {
  private KeyHandler keys;
  private MouseHandler mouse;
  private UiInventoryPanel inventoryPanel;

  public UiManager(KeyHandler keys, MouseHandler mouse, Inventory inventory) {
    this.keys = keys;
    this.mouse = mouse;
    inventoryPanel = new UiInventoryPanel(inventory, 20, 20);
  }

  public UiInventoryPanel getInventoryPanel() {
    return inventoryPanel;
  }

  /**
   * Updates UI state.
   */
  public void update() {
    inventoryPanel.update();
    if (keys.isJustPressed("toggle_inventory")) {
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
