package io.tidalisland.ui;

import io.tidalisland.entities.Player;
import io.tidalisland.input.Action;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.inventory.Inventory;
import io.tidalisland.inventory.InventoryController;
import io.tidalisland.items.Edible;
import io.tidalisland.items.Item;
import io.tidalisland.items.ItemStack;
import io.tidalisland.items.Placeable;
import io.tidalisland.items.Tool;
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

  private final InventoryController controller;

  private ItemStack<? extends Item> selectedStack;

  private final UiLabel title;
  private final ItemsPanel itemsPanel;
  private final ActionsPanel actionsPanel;

  /**
   * Creates a new inventory panel.
   */
  public UiInventoryPanel(Inventory inventory, WorldObjectManager wom, Player player) {
    super(276, 380);
    this.controller = new InventoryController(inventory, player, wom);

    setVisible(false);
    style(s -> s.padding(8));
    setLayout(new VerticalStackLayout(8));
    getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

    // Title label
    title = new UiLabel("Inventory", 268, 24);
    title.style(s -> s.fontSize(16));
    add(title);

    // Items grid
    itemsPanel = new ItemsPanel();
    add(itemsPanel);

    // Actions panel
    actionsPanel = new ActionsPanel();
    add(actionsPanel);

    refresh();
    inventory.addListener(evt -> refresh());
  }

  @Override
  public void onUpdate(KeyHandler keys, MouseHandler mouse) {
    if (keys.isJustPressed(Action.TOGGLE_INVENTORY)) {
      toggleVisible();
    }
  }

  private void refresh() {
    itemsPanel.refresh();
    actionsPanel.refresh();
  }

  /**
   * Displays items in a grid.
   */
  class ItemsPanel extends UiPanel {
    public ItemsPanel() {
      super(276, 256);
      setLayout(new GridLayout(4, 64, 64, 4, 4));
      setStyle(UiStyleDirector.makeTransparent());
    }

    public void refresh() {
      beginBatch();
      getChildren().clear();

      for (ItemStack<? extends Item> stack : controller.getInventory().getStacks()) {
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
          selectedStack = (selectedStack == stack) ? null : stack;
          runAfterUpdate(UiInventoryPanel.this::refresh);
        });

        Item item = stack.getItem();
        UiImage icon = new UiImage(item.getSprite().getImage(), 36, 36);
        slot.add(icon);

        UiLabel label = new UiLabel(String.valueOf(stack.getQuantity()), 36, 12);
        label.style(s -> s.fontSize(12));
        slot.add(label);

        add(slot);
      }

      endBatch();
    }
  }

  /**
   * Displays actions for selected item.
   */
  class ActionsPanel extends UiPanel {
    public ActionsPanel() {
      super(276, 24);
      setLayout(new VerticalStackLayout(4));
      getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM);
      setStyle(UiStyleDirector.makeTransparent());
    }

    public void refresh() {
      beginBatch();
      getChildren().clear();

      if (selectedStack == null) {
        endBatch();
        return;
      }

      Item item = selectedStack.getItem();

      if (item instanceof Placeable) {
        UiButton placeButton = new UiButton("Place", 64, 24);
        placeButton.style(s -> s.borderWidth(0));
        placeButton.setOnClick(() -> {
          if (controller.placeItem(item)) {
            selectedStack = null;
            runAfterUpdate(UiInventoryPanel.this::refresh);
          }
        });

        add(placeButton);
      }

      if (item instanceof Edible) {
        UiButton eatButton = new UiButton("Eat", 64, 24);
        eatButton.style(s -> s.borderWidth(0));
        eatButton.setOnClick(() -> {
          if (controller.eatItem(item)) {
            selectedStack = null;
            runAfterUpdate(UiInventoryPanel.this::refresh);
          }
        });

        add(eatButton);
      }

      if (item instanceof Tool) {
        UiButton equipButton = new UiButton("Equip", 64, 24);
        equipButton.style(s -> s.borderWidth(0));
        equipButton.setOnClick(() -> {
          if (controller.equipItem(item)) {
            selectedStack = null;
            runAfterUpdate(UiInventoryPanel.this::refresh);
          }
        });
        add(equipButton);
      }

      endBatch();
    }
  }
}
