package io.tidalisland.worldobjects;

import static org.assertj.core.api.Assertions.*;

import io.tidalisland.items.Wood;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

@DisplayName("Drop Tests")
class DropTest {

  @Test
  @DisplayName("Should create fixed quantity drop")
  void testFixedDrop() {
    Wood wood = new Wood();
    Drop drop = new Drop(wood, 5);

    assertThat(drop.getItem()).isSameAs(wood);
    assertThat(drop.getQuantity()).isEqualTo(5);
  }

  @RepeatedTest(10)
  @DisplayName("Should always return quantity within range")
  void testRandomDropRange() {
    Wood wood = new Wood();
    Drop drop = new Drop(wood, 1, 10);

    int quantity = drop.getQuantity();

    assertThat(quantity).isBetween(1, 10);
  }

  @Test
  @DisplayName("Should handle min equals max")
  void testMinEqualsMax() {
    Wood wood = new Wood();
    Drop drop = new Drop(wood, 5, 5);

    assertThat(drop.getQuantity()).isEqualTo(5);
  }

  @Test
  @DisplayName("Should return same quantity for fixed drop on multiple calls")
  void testFixedDropConsistency() {
    Wood wood = new Wood();
    Drop drop = new Drop(wood, 5);

    assertThat(drop.getQuantity()).isEqualTo(5);
    assertThat(drop.getQuantity()).isEqualTo(5);
    assertThat(drop.getQuantity()).isEqualTo(5);
  }
}
