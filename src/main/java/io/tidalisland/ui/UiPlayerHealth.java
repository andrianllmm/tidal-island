package io.tidalisland.ui;

import io.tidalisland.entities.Player;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.components.UiProgressBar;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Color;

/** Displays the player's health as a progress bar. */
public class UiPlayerHealth extends UiPanel {

  private final Player player;
  private final UiLabel label;
  private final UiProgressBar healthBar;

  /** Strategy for player health color. */
  private static class HealthColorStrategy implements UiProgressBar.ProgressColorStrategy {
    @Override
    public Color colorFor(double progress) {
      float hue = 0.33f;
      float saturation = 0.4f;
      float brightness = 0.6f;
      return Color.getHSBColor(hue, saturation, brightness);
    }
  }

  /** Creates a new player health bar. */
  public UiPlayerHealth(Player player) {
    super(200, 60);
    this.player = player;

    style = UiStyleDirector.makeTransparent();
    getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

    // Create label
    label = new UiLabel("HP", width, 20);
    label.setY(10);
    label.setWrapText(false);
    add(label);

    // Create progress bar
    healthBar =
        new UiProgressBar(200, 10, () -> (double) player.getCurrentHealth() / player.getMaxHealth(),
            new HealthColorStrategy());
    healthBar.setX((width - 200) / 2);
    healthBar.setY(35);
    add(healthBar);
  }

  @Override
  protected void onUpdate(KeyHandler keys, MouseHandler mouse) {
    label.setText(player.getCurrentHealth() + " / " + player.getMaxHealth() + " HP");
  }
}
