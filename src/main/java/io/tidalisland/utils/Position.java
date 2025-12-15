package io.tidalisland.utils;

import java.util.Objects;

/**
 * 2D position.
 */
public class Position {
  private int x;
  private int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }


  /**
   * Moves the position by the given x and y distance.
   */
  public void move(int dx, int dy) {
    x += dx;
    y += dy;
  }

  /**
   * Moves the position in the given direction by the given distance.
   */
  public void move(Direction direction, int d) {
    switch (direction) {
      case UP -> move(0, -d);
      case DOWN -> move(0, d);
      case LEFT -> move(-d, 0);
      case RIGHT -> move(d, 0);
      default -> {
      }
    }
  }

  /**
   * Calculates the distance between this position and the given position.
   */
  public int distanceTo(Position other) {
    int dx = other.x - x;
    int dy = other.y - y;
    return (int) Math.sqrt(dx * dx + dy * dy);
  }

  /**
   * Adds the given position to this position.
   */
  public Position add(Position other) {
    return new Position(x + other.getX(), y + other.getY());
  }

  /**
   * Subtracts the given position from this position.
   */
  public Position subtract(Position other) {
    return new Position(x - other.getX(), y - other.getY());
  }

  /**
   * Creates a copy of this position.
   */
  public Position copy() {
    return new Position(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Position)) {
      return false;
    }
    final Position other = (Position) obj;
    return this.x == other.getX() && this.y == other.getY();
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setPosition(Position position) {
    setX(position.getX());
    setY(position.getY());
  }
}
