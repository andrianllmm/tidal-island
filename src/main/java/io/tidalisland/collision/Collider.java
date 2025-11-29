package io.tidalisland.collision;

import io.tidalisland.graphics.Camera;
import io.tidalisland.utils.Direction;
import io.tidalisland.utils.Position;
import io.tidalisland.utils.Size;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Represents a 2D collider.
 */
public class Collider {
  private final Rectangle rect;
  private int offsetX = 0;
  private int offsetY = 0;

  public Collider(int x, int y, int width, int height) {
    this.rect = new Rectangle(x, y, width, height);
  }

  public Collider(Position position, Size size) {
    this(position.getX(), position.getY(), size.getWidth(), size.getHeight());
  }

  /**
   * Sets the offset of the collider.
   */
  public void setOffset(int offsetX, int offsetY) {
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  }

  /**
   * Checks if this collider intersects with another collider.
   */
  public boolean intersects(Collider other) {
    return rect.intersects(other.rect);
  }

  /**
   * Updates the collider position.
   */
  public void updatePosition(int newX, int newY) {
    rect.x = newX + offsetX;
    rect.y = newY + offsetY;
  }

  public void updatePosition(Position position) {
    updatePosition(position.getX(), position.getY());
  }

  /**
   * Moves the collider with a given distance.
   */
  public void move(int dx, int dy) {
    rect.x += dx;
    rect.y += dy;
  }

  /**
   * Moves the collider in a given direction.
   */
  public void move(Direction direction, int distance) {
    switch (direction) {
      case UP -> rect.y -= distance;
      case DOWN -> rect.y += distance;
      case LEFT -> rect.x -= distance;
      case RIGHT -> rect.x += distance;
      default -> {
      }
    }
  }

  /**
   * Checks if a point is inside this collider.
   */
  public boolean contains(int px, int py) {
    return rect.contains(px, py);
  }

  /**
   * Get a copy of this collider.
   */
  public Collider copy() {
    Collider copy = new Collider(rect.x, rect.y, rect.width, rect.height);
    copy.setOffset(offsetX, offsetY);
    return copy;
  }

  /**
   * Draws the collider for debugging.
   */
  public void draw(Graphics g, Camera camera) {
    if (g == null || camera == null) {
      return;
    }

    Color original = g.getColor();

    // Set debug color (semi-transparent red)
    g.setColor(new Color(255, 0, 0, 128));

    int screenX = rect.x - camera.getPosition().getX();
    int screenY = rect.y - camera.getPosition().getY();

    g.drawRect(screenX, screenY, rect.width, rect.height);

    g.setColor(original);
  }

  @Override
  public String toString() {
    return "Collider[x=" + rect.x + ", y=" + rect.y + ", width=" + rect.width + ", height="
        + rect.height + "]";
  }

  public Rectangle getRect() {
    return rect;
  }

  public int getX() {
    return rect.x;
  }

  public int getY() {
    return rect.y;
  }

  public int getWidth() {
    return rect.width;
  }

  public int getHeight() {
    return rect.height;
  }

  public void setX(int x) {
    rect.x = x;
  }

  public void setY(int y) {
    rect.y = y;
  }

  public void setWidth(int width) {
    rect.width = width;
  }

  public void setHeight(int height) {
    rect.height = height;
  }

}
