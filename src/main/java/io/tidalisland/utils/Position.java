package io.tidalisland.utils;

import java.util.Objects;

/**
 * Represents a position in a 2D space.
 */
public class Position {
  private int x;
  private int y;

  /**
   * Creates a new position at the specified coordinates.
   *
   * @param x the x-coordinate
   * @param y the y-coordinate
   */
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Moves this position by the specified delta values.
   *
   * @param dx the change in x-coordinate
   * @param dy the change in y-coordinate
   */
  public void move(int dx, int dy) {
    x += dx;
    y += dy;
  }

  /**
   * Moves this position in the specified direction by the given distance. If the direction is
   * {@code NONE}, the position remains unchanged.
   *
   * @param direction the direction to move
   * @param distance the distance to move
   */
  public void move(Direction direction, int distance) {
    switch (direction) {
      case UP -> move(0, -distance);
      case DOWN -> move(0, distance);
      case LEFT -> move(-distance, 0);
      case RIGHT -> move(distance, 0);
      default -> {
      }
    }
  }

  /**
   * Calculates the Euclidean distance between this position and another position.
   *
   * @param other the other position
   * @return the distance as an integer
   */
  public int distanceTo(Position other) {
    int dx = other.x - x;
    int dy = other.y - y;
    return (int) Math.sqrt(dx * dx + dy * dy);
  }

  /**
   * Returns a new position that is the sum of this position and the given position.
   *
   * @param other the position to add
   * @return the resulting position
   */
  public Position add(Position other) {
    return new Position(x + other.getX(), y + other.getY());
  }

  /**
   * Returns a new position that is the difference between this position and the given position.
   *
   * @param other the position to subtract
   * @return the resulting position
   */
  public Position subtract(Position other) {
    return new Position(x - other.getX(), y - other.getY());
  }

  /**
   * Creates and returns a copy of this position.
   *
   * @return a new Position with the same coordinates
   */
  public Position copy() {
    return new Position(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Position)) {
      return false;
    }
    Position other = (Position) obj;
    return x == other.getX() && y == other.getY();
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

  /*
   * Updates this position to match another position.
   *
   * @param position the position to copy coordinates from
   */
  public void setPosition(Position position) {
    setX(position.getX());
    setY(position.getY());
  }
}
