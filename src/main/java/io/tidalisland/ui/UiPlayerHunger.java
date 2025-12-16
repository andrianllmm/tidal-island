package io.tidalisland.ui;

import io.tidalisland.entities.Player;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.components.UiProgressBar;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Color;

/** Displays the player's hunger as a progress bar. */
public class UiPlayerHunger extends UiPanel {

  private final Player player;
  private final UiLabel label;
  private final UiProgressBar hungerBar;

  /** Strategy for player hunger color. */
  private static class HungerColorStrategy implements UiProgressBar.ProgressColorStrategy {
    @Override
    public Color colorFor(double progress) {
      float hue = 0.1f;
      float saturation = 0.4f;
      float brightness = 0.6f;
      return Color.getHSBColor(hue, saturation, brightness);
    }
  }

  /** Creates a new player hunger bar. */
  public UiPlayerHunger(Player player) {
    super(240, 60);
    this.player = player;

    style = UiStyleDirector.makeTransparent();

    // Create label
    label = new UiLabel("Hunger", width, 20);
    label.setY(10);
    label.setWrapText(false);
    add(label);

    // Create progress bar
    hungerBar =
        new UiProgressBar(200, 10, () -> (double) player.getCurrentHunger() / player.getMaxHunger(),
            new HungerColorStrategy());
    hungerBar.setX((width - 200) / 2);
    hungerBar.setY(35);
    add(hungerBar);
  }

  @Override
  protected void onUpdate(KeyHandler keys, MouseHandler mouse) {
    label.setText(player.getCurrentHunger() + " / " + player.getMaxHunger() + " Hunger");
  }
}
