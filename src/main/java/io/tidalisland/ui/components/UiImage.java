package io.tidalisland.ui.components;

import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A component that displays an image.
 */
public class UiImage extends UiComponent {
  private BufferedImage image;

  /**
   * Creates an image component.
   */
  public UiImage(BufferedImage image, int width, int height, int x, int y) {
    super(width, height, x, y);
    this.image = image;
  }

  /**
   * Creates an image component.
   */
  public UiImage(BufferedImage image, int width, int height) {
    this(image, width, height, 0, 0);
  }

  /**
   * Creates an image component.
   */
  public UiImage(BufferedImage image) {
    this(image, image.getWidth(), image.getHeight());
  }

  /**
   * Creates an image component from a file path.
   */
  public UiImage(String path, int width, int height, int x, int y) {
    super(width, height, x, y);
    try {
      this.image = ImageIO.read(getClass().getResourceAsStream(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates an image component from a file path.
   */
  public UiImage(String path, int width, int height) {
    this(path, width, height, 0, 0);
  }

  /**
   * Creates an image component from a file path.
   */
  public UiImage(String path) {
    this(path, 0, 0);
    width = image.getWidth();
    height = image.getHeight();
  }

  @Override
  protected void onUpdate(KeyHandler keys, MouseHandler mouse) {
    // Does nothing
  }

  @Override
  protected void onRender(Graphics g) {
    if (image == null) {
      return;
    }

    g.drawImage(image, getAbsX(), getAbsY(), width, height, null);
  }

  /**
   * Resizes the image to the original size.
   */
  public void resizeToOriginal() {
    width = image.getWidth();
    height = image.getHeight();
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }
}
