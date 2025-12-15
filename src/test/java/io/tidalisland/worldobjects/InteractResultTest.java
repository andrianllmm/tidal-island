package io.tidalisland.worldobjects;

import static org.assertj.core.api.Assertions.*;

import io.tidalisland.items.Wood;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("InteractResult Tests")
class InteractResultTest {

  @Test
  @DisplayName("Should create result with drops and destroyed flag")
  void testResultCreation() {
    Drop drop = new Drop(new Wood(), 3);
    InteractResult result = new InteractResult(List.of(drop), true);

    assertThat(result.drops).hasSize(1);
    assertThat(result.drops.get(0)).isSameAs(drop);
    assertThat(result.destroyed).isTrue();
  }

  @Test
  @DisplayName("Should create result with no drops")
  void testNoDrops() {
    InteractResult result = new InteractResult(List.of(), false);

    assertThat(result.drops).isEmpty();
    assertThat(result.destroyed).isFalse();
  }

  @Test
  @DisplayName("Should create result with multiple drops")
  void testMultipleDrops() {
    Drop drop1 = new Drop(new Wood(), 3);
    Drop drop2 = new Drop(new Wood(), 5);
    InteractResult result = new InteractResult(List.of(drop1, drop2), true);

    assertThat(result.drops).hasSize(2);
  }
}
