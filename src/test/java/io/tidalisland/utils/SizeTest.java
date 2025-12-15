package io.tidalisland.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Size Tests")
class SizeTest {

  @Test
  @DisplayName("Should create size with correct dimensions")
  void testSizeCreation() {
    Size size = new Size(100, 200);

    assertThat(size.getWidth()).isEqualTo(100);
    assertThat(size.getHeight()).isEqualTo(200);
  }

  @ParameterizedTest
  @CsvSource({"100, 200, 2.0, 1.5, 200, 300", "50, 50, 0.5, 0.5, 25, 25",
      "10, 20, 1.0, 1.0, 10, 20", "100, 100, 0, 0, 0, 0"})
  @DisplayName("Should scale correctly")
  void testScale(int width, int height, double scaleX, double scaleY, int expectedW,
      int expectedH) {
    Size size = new Size(width, height);
    size.scale(scaleX, scaleY);

    assertThat(size.getWidth()).isEqualTo(expectedW);
    assertThat(size.getHeight()).isEqualTo(expectedH);
  }

  @Test
  @DisplayName("Should create independent copy")
  void testCopy() {
    Size original = new Size(50, 75);
    Size copy = original.copy();

    assertThat(copy).isNotSameAs(original);
    assertThat(copy.getWidth()).isEqualTo(original.getWidth());
    assertThat(copy.getHeight()).isEqualTo(original.getHeight());

    copy.setWidth(200);
    assertThat(original.getWidth()).isEqualTo(50);
  }

  @Test
  @DisplayName("Should implement equals correctly")
  void testEquals() {
    Size s1 = new Size(100, 200);
    Size s2 = new Size(100, 200);
    Size s3 = new Size(150, 200);

    assertThat(s1).isEqualTo(s2);
    assertThat(s1).isNotEqualTo(s3);
    assertThat(s1).isEqualTo(s1);
    assertThat(s1).isNotEqualTo(null);
  }

  @Test
  @DisplayName("Should implement hashCode correctly")
  void testHashCode() {
    Size s1 = new Size(100, 200);
    Size s2 = new Size(100, 200);

    assertThat(s1.hashCode()).isEqualTo(s2.hashCode());
  }

  @Test
  @DisplayName("Should set size correctly")
  void testSetDimensions() {
    Size size = new Size(10, 20);

    size.setSize(new Size(50, 100));

    assertThat(size.getWidth()).isEqualTo(50);
    assertThat(size.getHeight()).isEqualTo(100);
  }
}
