package io.tidalisland.ui;

import io.tidalisland.config.Config;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.inventory.Inventory;
import io.tidalisland.ui.components.UiComponent;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.styles.UiStyle;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Graphics2D;

/**
 * Manages all UI components using a root panel.
 */
public class UiManager {
  private final UiPanel root;
  private final KeyHandler keys;
  private final MouseHandler mouse;

  /**
   * Initializes UI manager.
   */
  public UiManager(KeyHandler keys, MouseHandler mouse, Inventory inventory) {
    this.keys = keys;
    this.mouse = mouse;

    // Create root panel
    this.root = new UiPanel(Config.screenWidth(), Config.screenHeight(), 0, 0);
    UiStyle style = UiStyleDirector.fromTransparent().padding(24).build();
    this.root.setStyle(style);

    // Components
    UiInventoryPanel inv = new UiInventoryPanel(inventory);
    root.add(inv);
  }

  /**
   * Adds a top-level UI component to the root panel.
   */
  public void addComponent(UiComponent component) {
    root.add(component);
  }

  /**
   * Removes a top-level UI component from the root panel.
   */
  public void removeComponent(UiComponent component) {
    root.remove(component);
  }

  /**
   * Updates all UI components via the root panel.
   */
  public void update() {
    root.update(keys, mouse);
  }

  /**
   * Renders all UI components via the root panel.
   */
  public void render(Graphics2D g) {
    root.render(g);
  }

  /**
   * Returns the root panel containing all UI components.
   */
  public UiPanel getRoot() {
    return root;
  }
}
