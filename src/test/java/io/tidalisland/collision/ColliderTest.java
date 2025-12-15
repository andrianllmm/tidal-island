package io.tidalisland.collision;

import static org.assertj.core.api.Assertions.*;

import io.tidalisland.utils.Direction;
import io.tidalisland.utils.Position;
import io.tidalisland.utils.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Collider Tests")
class ColliderTest {

  private Collider collider;

  @BeforeEach
  void setUp() {
    collider = new Collider(10, 10, 50, 50);
  }

  @Test
  @DisplayName("Should create collider with correct bounds")
  void testColliderCreation() {
    assertThat(collider.getX()).isEqualTo(10);
    assertThat(collider.getY()).isEqualTo(10);
    assertThat(collider.getWidth()).isEqualTo(50);
    assertThat(collider.getHeight()).isEqualTo(50);
  }

  @Test
  @DisplayName("Should create collider from Position and Size")
  void testColliderCreationFromPosAndSize() {
    Position pos = new Position(20, 30);
    Size size = new Size(40, 60);
    Collider c = new Collider(pos, size);

    assertThat(c.getX()).isEqualTo(20);
    assertThat(c.getY()).isEqualTo(30);
    assertThat(c.getWidth()).isEqualTo(40);
    assertThat(c.getHeight()).isEqualTo(60);
  }


  @Test
  @DisplayName("Should detect intersection")
  void testIntersects() {
    Collider other = new Collider(40, 40, 30, 30);

    assertThat(collider.intersects(other)).isTrue();
    assertThat(other.intersects(collider)).isTrue();
  }

  @Test
  @DisplayName("Should detect non-intersection")
  void testNoIntersection() {
    Collider far = new Collider(200, 200, 50, 50);

    assertThat(collider.intersects(far)).isFalse();
  }

  @Test
  @DisplayName("Should not detect edge touching as intersection")
  void testEdgeTouching() {
    Collider adjacent = new Collider(60, 10, 50, 50);

    assertThat(collider.intersects(adjacent)).isFalse();
  }

  @Test
  @DisplayName("Should detect point inside")
  void testContainsPoint() {
    assertThat(collider.contains(30, 30)).isTrue();
    assertThat(collider.contains(10, 10)).isTrue(); // Edge
    assertThat(collider.contains(59, 59)).isTrue();
    assertThat(collider.contains(5, 30)).isFalse(); // Outside
    assertThat(collider.contains(100, 100)).isFalse();
  }

  @Test
  @DisplayName("Should detect collider fully inside")
  void testContainsCollider() {
    Collider inside = new Collider(20, 20, 20, 20);
    Collider outside = new Collider(5, 5, 10, 10);
    Collider overlapping = new Collider(50, 50, 30, 30);

    assertThat(collider.contains(inside)).isTrue();
    assertThat(collider.contains(outside)).isFalse();
    assertThat(collider.contains(overlapping)).isFalse();
  }

  @Test
  @DisplayName("Should update position")
  void testUpdatePosition() {
    collider.updatePosition(100, 150);

    assertThat(collider.getX()).isEqualTo(100);
    assertThat(collider.getY()).isEqualTo(150);

    Position newPos = new Position(200, 250);
    collider.updatePosition(newPos);

    assertThat(collider.getX()).isEqualTo(200);
    assertThat(collider.getY()).isEqualTo(250);
  }

  @Test
  @DisplayName("Should move by delta")
  void testMove() {
    collider.move(10, 20);

    assertThat(collider.getX()).isEqualTo(20);
    assertThat(collider.getY()).isEqualTo(30);
  }

  @Test
  @DisplayName("Should move in direction")
  void testMoveDirection() {
    collider.move(Direction.RIGHT, 15);
    assertThat(collider.getX()).isEqualTo(25);

    collider.move(Direction.DOWN, 10);
    assertThat(collider.getY()).isEqualTo(20);

    collider.move(Direction.LEFT, 5);
    assertThat(collider.getX()).isEqualTo(20);

    collider.move(Direction.UP, 10);
    assertThat(collider.getY()).isEqualTo(10);
  }

  @Test
  @DisplayName("Should not move for NONE direction")
  void testMoveNoneDirection() {
    int origX = collider.getX();
    int origY = collider.getY();

    collider.move(Direction.NONE, 100);

    assertThat(collider.getX()).isEqualTo(origX);
    assertThat(collider.getY()).isEqualTo(origY);
  }


  @Test
  @DisplayName("Should apply offset correctly")
  void testSetOffset() {
    collider.setOffset(5, 10);
    collider.updatePosition(100, 200);

    assertThat(collider.getX()).isEqualTo(105);
    assertThat(collider.getY()).isEqualTo(210);
  }

  @Test
  @DisplayName("Should create independent copy")
  void testCopy() {
    collider.setOffset(5, 10);
    Collider copy = collider.copy();

    assertThat(copy).isNotSameAs(collider);
    assertThat(copy.getX()).isEqualTo(collider.getX());
    assertThat(copy.getY()).isEqualTo(collider.getY());
    assertThat(copy.getWidth()).isEqualTo(collider.getWidth());
    assertThat(copy.getHeight()).isEqualTo(collider.getHeight());

    // Modify copy shouldn't affect original
    copy.setX(999);
    assertThat(collider.getX()).isEqualTo(10);
  }
}
