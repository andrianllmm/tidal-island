package io.tidalisland.ui;

import io.tidalisland.entities.Player;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.inventory.Inventory;
import io.tidalisland.items.Item;
import io.tidalisland.items.ItemStack;
import io.tidalisland.items.Placeable;
import io.tidalisland.ui.components.UiButton;
import io.tidalisland.ui.components.UiImage;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.layout.GridLayout;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.layout.VerticalStackLayout;
import io.tidalisland.ui.styles.UiStyleDirector;
import io.tidalisland.worldobjects.WorldObjectManager;
import java.awt.Color;

/**
 * UI panel for the inventory.
 */
public class UiInventoryPanel extends UiPanel {

  private final Inventory inventory;
  private final WorldObjectManager worldObjectManager;
  private final Player player;
  private ItemStack<? extends Item> selectedStack;
  private final UiLabel titleLabel;
  private final UiPanel itemsPanel;
  private final UiPanel actionsPanel;

  /**
   * Creates a new inventory panel.
   */
  public UiInventoryPanel(Inventory inventory, WorldObjectManager wom, Player player) {
    super(276, 424);
    this.inventory = inventory;
    this.worldObjectManager = wom;
    this.player = player;

    setLayout(new VerticalStackLayout(8));

    // Title label
    titleLabel = new UiLabel("Inventory", 268, 24);
    add(titleLabel);

    // Items panel (grid)
    itemsPanel = new UiPanel(276, 256);
    itemsPanel.setLayout(new GridLayout(4, 64, 64, 4, 4));
    itemsPanel.setStyle(UiStyleDirector.makeTransparent());
    add(itemsPanel);

    actionsPanel = new UiPanel(276, 24);
    actionsPanel.setLayout(new VerticalStackLayout(4));
    actionsPanel.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);
    actionsPanel.setStyle(UiStyleDirector.makeTransparent());
    add(actionsPanel);

    visible = false;

    refresh();
    inventory.addListener(evt -> refresh());
  }

  /**
   * Rebuilds the items grid based on inventory contents.
   */
  public void refresh() {
    itemsPanel.getChildren().clear();
    actionsPanel.getChildren().clear();

    for (ItemStack<? extends Item> stack : inventory.getStacks()) {
      UiPanel slot = new UiPanel(64, 64);
      slot.setStyle(UiStyleDirector.makeTransparent());
      slot.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
      slot.style(s -> s.borderColor(Color.WHITE).cornerRadius(8));

      if (stack == selectedStack) {
        slot.style(s -> s.borderWidth(2));
      } else {
        slot.style(s -> s.borderWidth(0));
      }

      // Click handler
      slot.setOnClick(() -> {
        selectedStack = stack;
        runAfterUpdate(this::refresh);
      });

      Item item = stack.getItem();
      UiImage icon = new UiImage(item.getSprite().getImage(), 36, 36);
      slot.add(icon);

      UiLabel label = new UiLabel(String.valueOf(stack.getQuantity()), 36, 12);
      label.style(s -> s.fontSize(12));
      slot.add(label);

      itemsPanel.add(slot);
    }

    rebuildActions();
  }

  private void rebuildActions() {
    actionsPanel.getChildren().clear();

    if (selectedStack == null) {
      return;
    }

    Item item = selectedStack.getItem();

    if (item instanceof Placeable placeable) {
      UiButton placeButton = new UiButton("Place", 64, 24);
      placeButton.setOnClick(() -> {
        if (placeable.place(worldObjectManager, player)) {
          inventory.remove(item, 1);
          selectedStack = null;
          runAfterUpdate(this::refresh);
        }
      });

      actionsPanel.add(placeButton);
    }
  }

  @Override
  public void onUpdate(KeyHandler keys, MouseHandler mouse) {
    if (keys.isJustPressed("toggle_inventory")) {
      toggleVisible();
    }
  }
}
