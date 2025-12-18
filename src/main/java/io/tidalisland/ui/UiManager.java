package io.tidalisland.ui;

import io.tidalisland.config.Config;
import io.tidalisland.crafting.CraftingManager;
import io.tidalisland.entities.Player;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.inventory.Inventory;
import io.tidalisland.tide.TidalManager;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.HorizontalStackLayout;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.layout.VerticalStackLayout;
import io.tidalisland.ui.styles.UiStyle;
import io.tidalisland.ui.styles.UiStyleDirector;
import io.tidalisland.worldobjects.WorldObjectManager;
import java.awt.Graphics;

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
  public UiManager(KeyHandler keys, MouseHandler mouse, Inventory inventory,
      WorldObjectManager worldObjectManager, TidalManager tidalManager, Player player) {

    this.keys = keys;
    this.mouse = mouse;

    // Create root panel
    this.root = new UiPanel(Config.screenWidth(), Config.screenHeight(), 0, 0);
    UiStyle style = UiStyleDirector.fromTransparent().padding(20).build();
    this.root.setStyle(style);
    this.root.setLayout(new VerticalStackLayout(0));

    // Layout
    final int bottomHeight = 60;
    final int centerHeight = Config.screenHeight() - bottomHeight - 60;

    UiPanel center = new UiPanel(Config.screenWidth(), centerHeight);
    center.setLayout(new HorizontalStackLayout(0));
    center.setStyle(UiStyleDirector.fromTransparent().padding(0).build());
    center.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    root.add(center);

    UiPanel left = new UiPanel(Config.screenWidth() / 2 - 20, centerHeight);
    left.setLayout(new VerticalStackLayout(16));
    left.setStyle(UiStyleDirector.fromTransparent().padding(16).build());
    left.getLayout().setAlignment(HorizontalAlignment.LEFT);

    UiPanel right = new UiPanel(Config.screenWidth() / 2 - 20, centerHeight);
    right.setStyle(UiStyleDirector.fromTransparent().padding(16).build());
    right.setLayout(new VerticalStackLayout(16));
    right.getLayout().setAlignment(HorizontalAlignment.RIGHT);

    center.add(left);
    center.add(right);

    UiPanel bottom = new UiPanel(Config.screenWidth(), bottomHeight);
    bottom.setStyle(UiStyleDirector.fromTransparent().padding(16).build());
    bottom.setLayout(new HorizontalStackLayout(16));
    bottom.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);
    root.add(bottom);

    // Components
    UiInventoryPanel inv = new UiInventoryPanel(inventory, worldObjectManager, player);
    left.add(inv);

    UiEquipmentPanel equipment = new UiEquipmentPanel(player);
    left.add(equipment);

    UiCraftingPanel crafting = new UiCraftingPanel(inventory, new CraftingManager());
    right.add(crafting);

    UiPlayerHealth healthBar = new UiPlayerHealth(player);
    bottom.add(healthBar);

    UiPlayerHunger hungerBar = new UiPlayerHunger(player);
    bottom.add(hungerBar);

    UiTideTimer tideTimer = new UiTideTimer(tidalManager);
    bottom.add(tideTimer);
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
  public void render(Graphics g) {
    root.render(g);
  }

  /**
   * Returns the root panel containing all UI components.
   */
  public UiPanel getRoot() {
    return root;
  }
}
