package io.tidalisland.ui;

import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.tide.TidalManager;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.components.UiProgressBar;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.styles.Colors;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Color;

/**
 * Displays the tide timer.
 */
public class UiTideTimer extends UiPanel {

  private final TidalManager tidalManager;
  private final UiLabel timerLabel;
  private final UiProgressBar progressBar;

  /** Strategy for tide color in the progress bar. */
  public static class TideColorStrategy implements UiProgressBar.ProgressColorStrategy {
    @Override
    public Color colorFor(double progress) {
      return Colors.RED;
    }
  }

  /** Creates a new tide timer. */
  public UiTideTimer(TidalManager tidalManager) {
    super(200, 60); // panel width x height
    this.tidalManager = tidalManager;

    style = UiStyleDirector.makeTransparent();
    getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

    // Create label
    timerLabel = new UiLabel("", width, 20);
    timerLabel.setY(10);
    timerLabel.setWrapText(false);
    add(timerLabel);

    // Create progress bar
    progressBar =
        new UiProgressBar(200, 10, tidalManager::getFloodProgress, new TideColorStrategy());
    progressBar.setX((width - 200) / 2);
    progressBar.setY(35);
    add(progressBar);
  }

  @Override
  protected void onUpdate(KeyHandler keys, MouseHandler mouse) {
    int seconds = (int) Math.ceil(tidalManager.getTimeUntilNextFlood());
    timerLabel.setText("Tide rises in " + seconds + "s!");
  }
}
