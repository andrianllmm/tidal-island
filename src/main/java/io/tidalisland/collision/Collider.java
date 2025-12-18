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

  /**
   * Creates a new collider with the specified position and size.
   *
   * @param x the x-coordinate
   * @param y the y-coordinate
   * @param width the width
   * @param height the height
   */
  public Collider(int x, int y, int width, int height) {
    this.rect = new Rectangle(x, y, width, height);
  }

  /**
   * Creates a new collider with the specified position and size using {@link Position} and
   * {@link Size} objects.
   *
   * @param position the position
   * @param size the size
   */
  public Collider(Position position, Size size) {
    this(position.getX(), position.getY(), size.getWidth(), size.getHeight());
  }

  /**
   * Checks if this collider intersects with another collider.
   *
   * @param other the other collider
   * @return true if the colliders intersect, false otherwise
   */
  public boolean intersects(Collider other) {
    return rect.intersects(other.rect);
  }

  /**
   * Checks if a point is inside this collider.
   *
   * @param px the x-coordinate of the point
   * @param py the y-coordinate of the point
   */
  public boolean contains(int px, int py) {
    return rect.contains(px, py);
  }

  /**
   * Checks if a collider is inside this collider.
   *
   * @param other the other collider
   * @param tolerance how far the colliders can be apart
   * @return true if the other collider is inside this collider, false otherwise
   */
  public boolean contains(Collider other, int tolerance) {
    return other.getX() >= this.getX() - tolerance && other.getY() >= this.getY() - tolerance
        && other.getX() + other.getWidth() <= this.getX() + this.getWidth() + tolerance
        && other.getY() + other.getHeight() <= this.getY() + this.getHeight() + tolerance;
  }

  /**
   * Checks if a collider is inside this collider.
   *
   * @param other the other collider
   * @return true if the other collider is inside this collider, false otherwise
   */
  public boolean contains(Collider other) {
    return contains(other, 0);
  }

  /**
   * Checks if this collider is in front of another collider within a given range.
   *
   * @param other the other collider
   * @param direction the direction to check
   * @param range the maximum distance allowed to consider "in front"
   * @return true if this collider is in front of the other collider within the range, false
   *         otherwise
   */
  public boolean isInFrontOf(Collider other, Direction direction, int range) {
    switch (direction) {
      case UP: {
        if (!overlapsHorizontally(other)) {
          return false;
        }
        int distance = this.top() - other.bottom(); // distance from collider to object above
        return distance >= 0 && distance <= range;
      }
      case DOWN: {
        if (!overlapsHorizontally(other)) {
          return false;
        }
        int distance = other.top() - this.bottom(); // distance from collider to object below
        return distance >= 0 && distance <= range;
      }
      case LEFT: {
        if (!overlapsVertically(other)) {
          return false;
        }
        int distance = this.left() - other.right(); // distance from collider to object on the left
        return distance >= 0 && distance <= range;
      }
      case RIGHT: {
        if (!overlapsVertically(other)) {
          return false;
        }
        int distance = other.left() - this.right(); // distance from collider to object on the right
        return distance >= 0 && distance <= range;
      }
      case NONE: {
        return false;
      }
      default: {
        return false;
      }
    }
  }

  public boolean overlapsHorizontally(Collider other) {
    return this.right() > other.left() && this.left() < other.right();
  }

  public boolean overlapsVertically(Collider other) {
    return this.bottom() > other.top() && this.top() < other.bottom();
  }

  /**
   * Updates the collider position.
   *
   * @param newX the new x-coordinate
   * @param newY the new y-coordinate
   */
  public void updatePosition(int newX, int newY) {
    rect.x = newX + offsetX;
    rect.y = newY + offsetY;
  }

  /**
   * Updates the collider position from a Position.
   *
   * @param position the position to copy from
   * @see #updatePosition(int, int)
   */
  public void updatePosition(Position position) {
    updatePosition(position.getX(), position.getY());
  }

  /**
   * Moves the collider with a given distance.
   *
   * @param dx the change in x-coordinate
   * @param dy the change in y-coordinate
   */
  public void move(int dx, int dy) {
    rect.x += dx;
    rect.y += dy;
  }

  /**
   * Moves the collider in a given direction.
   *
   * @param direction the direction to move
   * @param distance the distance to move
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
   * Get a copy of this collider.
   *
   * @return a new collider with the same position and size
   */
  public Collider copy() {
    Collider copy = new Collider(rect.x, rect.y, rect.width, rect.height);
    copy.setOffset(offsetX, offsetY);
    return copy;
  }

  /**
   * Draws the collider for debugging.
   *
   * @param g the graphics context
   * @param camera the camera
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

  /**
   * Gets the left side of this collider.
   */
  public int left() {
    return rect.x;
  }

  /**
   * Gets the right side of this collider.
   */
  public int right() {
    return rect.x + rect.width;
  }

  /**
   * Gets the top side of this collider.
   */
  public int top() {
    return rect.y;
  }

  /**
   * Gets the bottom side of this collider.
   */
  public int bottom() {
    return rect.y + rect.height;
  }

  /**
   * Gets the center x-coordinate of this collider.
   */
  public int centerX() {
    return rect.x + rect.width / 2;
  }

  /**
   * Gets the center y-coordinate of this collider.
   */
  public int centerY() {
    return rect.y + rect.height / 2;
  }

  @Override
  public String toString() {
    return "Collider[x=" + rect.x + ", y=" + rect.y + ", width=" + rect.width + ", height="
        + rect.height + "]";
  }

  public Rectangle getRect() {
    return rect;
  }

  public Position getPosition() {
    return new Position(rect.x, rect.y);
  }

  public int getX() {
    return rect.x;
  }

  public int getY() {
    return rect.y;
  }

  public Size getSize() {
    return new Size(rect.width, rect.height);
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

  public void setOffset(int offsetX, int offsetY) {
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  }
}
