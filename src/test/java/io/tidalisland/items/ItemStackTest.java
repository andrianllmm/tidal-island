package io.tidalisland.items;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ItemStack Tests")
class ItemStackTest {

  private Wood wood;
  private ItemStack<Wood> stack;

  @BeforeEach
  void setUp() {
    wood = new Wood();
    stack = new ItemStack<>(wood, 5);
  }

  @Test
  @DisplayName("Should create stack with correct quantity")
  void testStackCreation() {
    assertThat(stack.getQuantity()).isEqualTo(5);
    assertThat(stack.getItem()).isSameAs(wood);
  }

  @Test
  @DisplayName("Should throw exception for over-capacity stack")
  void testOverCapacityStack() {
    int maxStack = wood.getMaxStackSize();

    assertThatThrownBy(() -> new ItemStack<>(wood, maxStack + 1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Should add items to stack")
  void testAddItems() {
    stack.add(3);
    assertThat(stack.getQuantity()).isEqualTo(8);
  }


  @Test
  @DisplayName("Should fill on stack overflow")
  void testStackOverflow() {
    int maxStack = wood.getMaxStackSize();
    ItemStack<Wood> fullStack = new ItemStack<>(wood, 0);
    fullStack.add(maxStack + 1);

    assertThat(fullStack.getQuantity()).isEqualTo(maxStack);
  }

  @Test
  @DisplayName("Should remove items from stack")
  void testRemoveItems() {
    stack.remove(2);
    assertThat(stack.getQuantity()).isEqualTo(3);
  }

  @Test
  @DisplayName("Should remove all when removing too many items")
  void testRemoveTooMany() {
    stack.remove(100);
    assertThat(stack.getQuantity()).isEqualTo(0);
  }

  @Test
  @DisplayName("Should handle single-item stacks")
  void testSingleItemStack() {
    RaftItem raft = new RaftItem();
    ItemStack<RaftItem> raftStack = new ItemStack<>(raft, 1);

    assertThat(raftStack.isFull()).isTrue();
    assertThat(raftStack.getRemainingCapacity()).isEqualTo(0);
  }

  @Test
  @DisplayName("Should detect full stack")
  void testIsFull() {
    int maxStack = wood.getMaxStackSize();
    ItemStack<Wood> fullStack = new ItemStack<>(wood, maxStack);

    assertThat(fullStack.isFull()).isTrue();
    assertThat(stack.isFull()).isFalse();
  }

  @Test
  @DisplayName("Should calculate remaining capacity")
  void testRemainingCapacity() {
    int maxStack = wood.getMaxStackSize();

    assertThat(stack.getRemainingCapacity()).isEqualTo(maxStack - 5);

    stack.add(3);
    assertThat(stack.getRemainingCapacity()).isEqualTo(maxStack - 8);
  }
}
