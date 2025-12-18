package io.tidalisland.ui;

import io.tidalisland.entities.Player;
import io.tidalisland.input.Action;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.ui.components.UiButton;
import io.tidalisland.ui.components.UiImage;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.components.UiProgressBar;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.layout.VerticalStackLayout;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Color;

/**
 * UI panel for equipment.
 */
public class UiEquipmentPanel extends UiPanel {
  private final Player player;

  private UiPanel slot;
  private UiLabel title;
  private UiProgressBar durabilityBar;
  private UiButton unequipButton;

  /**
   * Creates a new equipment panel.
   */
  public UiEquipmentPanel(Player player, int width, int height) {
    super(width, height);
    this.player = player;

    style(s -> s.padding(8));
    setLayout(new VerticalStackLayout(8));
    getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.TOP);

    title = new UiLabel("Equipment", width - 16, 24);
    title.style(s -> s.fontSize(16));
    add(title);

    slot = new UiPanel(64, 64);
    slot.setStyle(UiStyleDirector.makeTransparent());
    slot.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    slot.style(s -> s.borderWidth(2).borderColor(new Color(60, 60, 60)).cornerRadius(8));
    add(slot);

    durabilityBar = new UiProgressBar(64, 10, () -> player.getEquipment().getDurabilityPercent(),
        (double progress) -> new Color(150, 150, 150, (int) (250 * progress)));
    durabilityBar.style(s -> s.borderWidth(2).borderColor(new Color(60, 60, 60)));
    add(durabilityBar);

    unequipButton = new UiButton("Unequip", 80, 24);
    unequipButton.getLabel().style(s -> s.fontSize(14));
    unequipButton.style(s -> s.borderWidth(0));
    unequipButton.setOnClick(() -> {
      player.getEquipment().unequip();
      runAfterUpdate(this::refresh);
    });
    add(unequipButton);

    refresh();
    player.getEquipment().addListener(evt -> refresh());
  }

  /**
   * Rebuilds the equipment slots based on equipment.
   */
  public void refresh() {
    slot.getChildren().clear();

    if (player.getEquipment().hasToolEquipped()) {
      UiImage icon =
          new UiImage(player.getEquipment().getEquippedTool().getSprite().getImage(), 36, 36);
      slot.add(icon);
    }
  }
}
