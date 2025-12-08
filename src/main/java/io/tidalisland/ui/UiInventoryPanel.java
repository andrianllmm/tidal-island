package io.tidalisland.ui;

import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.inventory.Inventory;
import io.tidalisland.items.Item;
import io.tidalisland.items.ItemStack;
import io.tidalisland.ui.components.UiImage;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.layout.GridLayout;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.layout.VerticalStackLayout;
import io.tidalisland.ui.styles.UiStyleDirector;

/**
 * Inventory window UI.
 */
public class UiInventoryPanel extends UiPanel {
  private final Inventory inventory;
  private final UiPanel itemsPanel;
  private final UiLabel titleLabel;

  /**
   * Creates a new inventory panel.
   */
  public UiInventoryPanel(Inventory inventory) {
    super(276, 424);
    this.inventory = inventory;

    setLayout(new VerticalStackLayout(8));

    // Title label
    titleLabel = new UiLabel("Inventory", 268, 24);
    add(titleLabel);

    // Items panel (grid)
    itemsPanel = new UiPanel(276, 256);
    itemsPanel.setLayout(new GridLayout(4, 64, 64, 4, 4));
    itemsPanel.setStyle(UiStyleDirector.makeTransparent());
    add(itemsPanel);

    visible = false;

    refresh();
  }

  /**
   * Rebuilds the items grid based on inventory contents.
   */
  public void refresh() {
    if (!inventory.isDirty()) {
      return;
    }

    itemsPanel.getChildren().clear(); // clear previous items

    for (ItemStack stack : inventory.getStacks()) {
      UiPanel slot = new UiPanel(64, 64);
      slot.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
      slot.setStyle(UiStyleDirector.makeTransparent());
      itemsPanel.add(slot);

      Item item = stack.getItem();
      UiImage icon = new UiImage(item.getSprite().getFrame().getImage(), 36, 36);
      slot.add(icon);

      int qty = stack.getQuantity();
      UiLabel label = new UiLabel(String.valueOf(qty), 36, 12);
      label.style(s -> s.fontSize(12));
      slot.add(label);
    }

    inventory.clearDirty();
  }

  @Override
  public void onUpdate(KeyHandler keys, MouseHandler mouse) {
    if (keys.isJustPressed("toggle_inventory")) {
      toggleVisible();
    }
    refresh();
  }
}
