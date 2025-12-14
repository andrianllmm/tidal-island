package io.tidalisland.ui.components;

import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.function.DoubleSupplier;

/**
 * A progress bar UI component.
 */
public class UiProgressBar extends UiComponent {
  /**
   * A strategy for determining the color of a progress bar.
   */
  @FunctionalInterface
  public interface ProgressColorStrategy {
    Color colorFor(double progress);
  }

  private final DoubleSupplier progressSupplier;
  private Color backgroundColor = new Color(50, 50, 50);
  private Color borderColor = Color.WHITE;
  private ProgressColorStrategy colorStrategy;

  /** Creates a new progress bar. */
  public UiProgressBar(
      int width,
      int height,
      DoubleSupplier progressSupplier,
      ProgressColorStrategy colorStrategy) {
    super(width, height);
    this.progressSupplier = progressSupplier;
    this.colorStrategy = colorStrategy;
  }

  @Override
  protected void onUpdate(KeyHandler keys, MouseHandler mouse) {
    // No logic
  }

  @Override
  protected void onRender(Graphics g) {
    double progress = clamp(progressSupplier.getAsDouble());

    int x = getAbsX();
    int y = getAbsY();

    // Background
    g.setColor(backgroundColor);
    g.fillRect(x, y, width, height);

    // Progress
    int filled = (int) (width * progress);
    g.setColor(colorStrategy.colorFor(progress));
    g.fillRect(x, y, filled, height);

    // Border
    Graphics2D g2 = (Graphics2D) g;
    Stroke old = g2.getStroke();

    g2.setColor(borderColor);
    g2.setStroke(new BasicStroke(2f));
    g2.drawRect(x, y, width, height);

    g2.setStroke(old);
  }

  private double clamp(double v) {
    return Math.max(0.0, Math.min(1.0, v));
  }
}
