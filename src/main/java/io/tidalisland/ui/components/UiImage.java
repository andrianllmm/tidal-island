package io.tidalisland.ui.components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Displays an image or icon in the UI.
 */
public class UiImage extends UiComponent {
  private BufferedImage image;

  public UiImage(BufferedImage image, int x, int y, int width, int height) {
    super(x, y, width, height);
    this.image = image;
  }

  public UiImage(BufferedImage image, int x, int y) {
    this(image, x, y, image.getWidth(), image.getHeight());
  }

  @Override
  public void update() {}

  @Override
  public void render(Graphics2D g) {
    if (image != null) {
      g.drawImage(image, x, y, width, height, null);
    }
  }
}
