package io.tidalisland.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Position Tests")
class PositionTest {

  @Test
  @DisplayName("Should create position with correct coordinates")
  void testPositionCreation() {
    Position pos = new Position(10, 20);

    assertThat(pos.getX()).isEqualTo(10);
    assertThat(pos.getY()).isEqualTo(20);
  }

  @ParameterizedTest
  @CsvSource({"10, 20, 5, 3, 15, 23", "0, 0, -5, -10, -5, -10", "-10, -20, 15, 25, 5, 5"})
  @DisplayName("Should move position by delta")
  void testMove(int startX, int startY, int dx, int dy, int expectedX, int expectedY) {
    Position pos = new Position(startX, startY);
    pos.move(dx, dy);

    assertThat(pos.getX()).isEqualTo(expectedX);
    assertThat(pos.getY()).isEqualTo(expectedY);
  }

  @ParameterizedTest
  @CsvSource({"UP, 10, 20, 5, 10, 15", "DOWN, 10, 20, 5, 10, 25", "LEFT, 10, 20, 5, 5, 20",
      "RIGHT, 10, 20, 5, 15, 20", "NONE, 10, 20, 5, 10, 20"})
  @DisplayName("Should move in direction")
  void testMoveDirection(Direction dir, int startX, int startY, int distance, int expectedX,
      int expectedY) {
    Position pos = new Position(startX, startY);
    pos.move(dir, distance);

    assertThat(pos.getX()).isEqualTo(expectedX);
    assertThat(pos.getY()).isEqualTo(expectedY);
  }

  @Test
  @DisplayName("Should calculate distance correctly")
  void testDistanceTo() {
    Position p1 = new Position(0, 0);
    Position p2 = new Position(3, 4);

    // 3-4-5 triangle
    assertThat(p1.distanceTo(p2)).isEqualTo(5);
    assertThat(p2.distanceTo(p1)).isEqualTo(5);
  }

  @Test
  @DisplayName("Should calculate zero distance to self")
  void testDistanceToSelf() {
    Position pos = new Position(10, 20);
    assertThat(pos.distanceTo(pos)).isEqualTo(0);
  }

  @Test
  @DisplayName("Should add positions correctly")
  void testAdd() {
    Position p1 = new Position(10, 20);
    Position p2 = new Position(5, 15);
    Position result = p1.add(p2);

    assertThat(result.getX()).isEqualTo(15);
    assertThat(result.getY()).isEqualTo(35);
  }

  @Test
  @DisplayName("Should subtract positions correctly")
  void testSubtract() {
    Position p1 = new Position(20, 30);
    Position p2 = new Position(5, 10);
    Position result = p1.subtract(p2);

    assertThat(result.getX()).isEqualTo(15);
    assertThat(result.getY()).isEqualTo(20);
  }

  @Test
  @DisplayName("Should create independent copy")
  void testCopy() {
    Position original = new Position(5, 15);
    Position copy = original.copy();

    assertThat(copy).isNotSameAs(original);
    assertThat(copy.getX()).isEqualTo(original.getX());
    assertThat(copy.getY()).isEqualTo(original.getY());

    // Modify copy shouldn't affect original
    copy.setX(100);
    assertThat(original.getX()).isEqualTo(5);
  }

  @Test
  @DisplayName("Should set position correctly")
  void testSetPosition() {
    Position pos = new Position(0, 0);
    Position newPos = new Position(50, 100);

    pos.setPosition(newPos);

    assertThat(pos.getX()).isEqualTo(50);
    assertThat(pos.getY()).isEqualTo(100);
  }

  @Test
  @DisplayName("Should implement equals correctly")
  void testEquals() {
    Position p1 = new Position(10, 20);
    Position p2 = new Position(10, 20);
    Position p3 = new Position(15, 25);

    assertThat(p1).isEqualTo(p2);
    assertThat(p1).isNotEqualTo(p3);
    assertThat(p1).isEqualTo(p1);
    assertThat(p1).isNotEqualTo(null);
    assertThat(p1).isNotEqualTo("not a position");
  }

  @Test
  @DisplayName("Should implement hashCode correctly")
  void testHashCode() {
    Position p1 = new Position(10, 20);
    Position p2 = new Position(10, 20);

    assertThat(p1.hashCode()).isEqualTo(p2.hashCode());
  }
}
