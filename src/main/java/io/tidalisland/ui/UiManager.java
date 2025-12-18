package io.tidalisland.ui;

import io.tidalisland.config.Config;
import io.tidalisland.crafting.CraftingManager;
import io.tidalisland.entities.Player;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.inventory.Inventory;
import io.tidalisland.states.GameStateManager;
import io.tidalisland.states.PauseState;
import io.tidalisland.tide.TidalManager;
import io.tidalisland.ui.components.UiButton;
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
  private final UiInventoryPanel invPanel;
  private final UiCraftingPanel craftingPanel;

  private final GameStateManager gsm;
  private final KeyHandler keys;
  private final MouseHandler mouse;

  /**
   * Initializes UI manager.
   */
  public UiManager(GameStateManager gsm, KeyHandler keys, MouseHandler mouse, Inventory inventory,
      WorldObjectManager worldObjectManager, TidalManager tidalManager, Player player) {
    this.gsm = gsm;
    this.keys = keys;
    this.mouse = mouse;

    // Layout
    final int topHeight = 60;
    final int bottomHeight = 60;
    final int centerHeight = Config.screenHeight() - bottomHeight - topHeight - 60;

    // Create root panel
    this.root = new UiPanel(Config.screenWidth(), Config.screenHeight(), 0, 0);
    UiStyle style = UiStyleDirector.fromTransparent().padding(20).build();
    this.root.setStyle(style);
    this.root.setLayout(new VerticalStackLayout(0));

    // Components

    invPanel = new UiInventoryPanel(inventory, worldObjectManager, player);
    craftingPanel = new UiCraftingPanel(inventory, new CraftingManager());

    UiPlayerHealth healthBar = new UiPlayerHealth(player);
    UiPlayerHunger hungerBar = new UiPlayerHunger(player);
    UiTideTimer tideTimer = new UiTideTimer(tidalManager);

    final int btnWidth = 40;
    final int btnHeight = 36;
    UiButton pauseButton = new UiButton("| |", btnWidth, btnHeight);
    pauseButton.style(s -> s.fontSize(20));
    pauseButton.setOnClick(() -> gsm.push(new PauseState(gsm, keys, mouse)));
    UiButton toggleInventoryButton = new UiButton("i", btnWidth, btnHeight);
    toggleInventoryButton.style(s -> s.fontSize(20));
    toggleInventoryButton.setOnClick(() -> invPanel.setVisible(!invPanel.isVisible()));
    UiButton toggleCraftingButton = new UiButton("c", btnWidth, btnHeight);
    toggleCraftingButton.style(s -> s.fontSize(20));
    toggleCraftingButton.setOnClick(() -> craftingPanel.setVisible(!craftingPanel.isVisible()));

    // Layout panels

    UiPanel top = new UiPanel(Config.screenWidth(), topHeight);
    top.setStyle(UiStyleDirector.fromTransparent().padding(16).build());
    top.setLayout(new HorizontalStackLayout(16));
    top.getLayout().setAlignment(HorizontalAlignment.RIGHT, VerticalAlignment.TOP);

    UiPanel center = new UiPanel(Config.screenWidth(), centerHeight);
    center.setLayout(new HorizontalStackLayout(0));
    center.setStyle(UiStyleDirector.fromTransparent().padding(0).build());
    center.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

    UiPanel left = new UiPanel(Config.screenWidth() / 2 - 20, centerHeight);
    left.setLayout(new VerticalStackLayout(16));
    left.setStyle(UiStyleDirector.fromTransparent().padding(16).build());
    left.getLayout().setAlignment(HorizontalAlignment.LEFT);

    UiPanel right = new UiPanel(Config.screenWidth() / 2 - 20, centerHeight);
    right.setLayout(new VerticalStackLayout(16));
    right.setStyle(UiStyleDirector.fromTransparent().padding(16).build());
    right.getLayout().setAlignment(HorizontalAlignment.RIGHT);


    UiPanel bottom = new UiPanel(Config.screenWidth(), bottomHeight);
    bottom.setStyle(UiStyleDirector.fromTransparent().padding(16).build());
    bottom.setLayout(new HorizontalStackLayout(16));
    bottom.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);

    root.add(top);
    root.add(center);
    root.add(bottom);
    center.add(left);
    center.add(right);

    top.add(toggleInventoryButton);
    top.add(toggleCraftingButton);
    top.add(pauseButton);

    left.add(invPanel);
    right.add(craftingPanel);

    bottom.add(healthBar);
    bottom.add(hungerBar);
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
