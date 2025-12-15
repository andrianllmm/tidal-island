package io.tidalisland.inventory;

import static org.assertj.core.api.Assertions.*;

import io.tidalisland.items.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Inventory Tests")
class InventoryTest {

  private Inventory inventory;
  private Wood wood;
  private Stone stone;

  @BeforeEach
  void setUp() {
    inventory = new Inventory(24);
    wood = new Wood();
    stone = new Stone();
  }

  @Test
  @DisplayName("Should create inventory with correct max slots")
  void testInventoryCreation() {
    assertThat(inventory.getMaxSlots()).isEqualTo(24);
    assertThat(inventory.isEmpty()).isTrue();
    assertThat(inventory.getUsedSlots()).isEqualTo(0);
  }

  @Test
  @DisplayName("Should throw exception for invalid max slots")
  void testInvalidMaxSlots() {
    assertThatThrownBy(() -> new Inventory(0)).isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> new Inventory(-5)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Should add items successfully")
  void testAddItems() {
    boolean added = inventory.add(wood, 5);

    assertThat(added).isTrue();
    assertThat(inventory.getQuantity(wood)).isEqualTo(5);
    assertThat(inventory.isEmpty()).isFalse();
    assertThat(inventory.getUsedSlots()).isEqualTo(1);
  }

  @Test
  @DisplayName("Should not add zero or negative quantity")
  void testAddInvalidQuantity() {
    assertThat(inventory.add(wood, 0)).isFalse();
    assertThat(inventory.add(wood, -5)).isFalse();
    assertThat(inventory.getQuantity(wood)).isEqualTo(0);
  }

  @Test
  @DisplayName("Should stack items up to max stack size")
  void testStackItems() {
    int maxStack = wood.getMaxStackSize();

    inventory.add(wood, maxStack);
    assertThat(inventory.getUsedSlots()).isEqualTo(1);

    inventory.add(wood, 1);
    assertThat(inventory.getUsedSlots()).isEqualTo(2);
    assertThat(inventory.getQuantity(wood)).isEqualTo(maxStack + 1);
  }

  @Test
  @DisplayName("Should reject items when inventory is full")
  void testFullInventory() {
    // Fill inventory
    int maxStack = wood.getMaxStackSize();
    inventory.add(wood, maxStack * 24);

    assertThat(inventory.getUsedSlots()).isEqualTo(24);

    // Try to add more
    boolean added = inventory.add(wood, 1);
    assertThat(added).isFalse();
  }

  @Test
  @DisplayName("Should remove items successfully")
  void testRemoveItems() {
    inventory.add(wood, 10);

    boolean removed = inventory.remove(wood, 3);

    assertThat(removed).isTrue();
    assertThat(inventory.getQuantity(wood)).isEqualTo(7);
  }

  @Test
  @DisplayName("Should remove all of an item type when removing exact amount")
  void testRemoveAll() {
    inventory.add(wood, 5);

    boolean removed = inventory.remove(wood, 5);

    assertThat(removed).isTrue();
    assertThat(inventory.getQuantity(wood)).isEqualTo(0);
    assertThat(inventory.isEmpty()).isTrue();
  }

  @Test
  @DisplayName("Should remove all of an item type when removing more than available")
  void testRemoveTooMany() {
    inventory.add(wood, 5);

    boolean removed = inventory.remove(wood, 10);

    assertThat(removed).isTrue();
    assertThat(inventory.getQuantity(wood)).isEqualTo(0);
  }

  @Test
  @DisplayName("Should not remove zero or negative quantity")
  void testRemoveInvalidQuantity() {
    inventory.add(wood, 10);

    assertThat(inventory.remove(wood, 0)).isFalse();
    assertThat(inventory.remove(wood, -5)).isFalse();
    assertThat(inventory.getQuantity(wood)).isEqualTo(10);
  }

  @Test
  @DisplayName("Should track used slots correctly")
  void testUsedSlots() {
    inventory.add(wood, wood.getMaxStackSize()); // 1 stack
    assertThat(inventory.getUsedSlots()).isEqualTo(1);

    inventory.add(wood, wood.getMaxStackSize()); // 2 stacks total
    assertThat(inventory.getUsedSlots()).isEqualTo(2);

    inventory.remove(wood, wood.getMaxStackSize() * 2); // Remove all wood
    assertThat(inventory.getUsedSlots()).isEqualTo(0);
    assertThat(inventory.isEmpty()).isTrue();
  }

  @Test
  @DisplayName("Should handle items with different stack sizes")
  void testDifferentStackSizes() {
    Leaf leaf = new Leaf();
    Wood wood = new Wood();
    int leafQty = leaf.getMaxStackSize() * 2;
    int woodQty = wood.getMaxStackSize() * 3;

    inventory.add(leaf, leafQty); // Should use 2 slots
    inventory.add(wood, woodQty); // Should use 3 slots

    assertThat(inventory.getUsedSlots()).isEqualTo(5);
    assertThat(inventory.getQuantity(leaf)).isEqualTo(leafQty);
    assertThat(inventory.getQuantity(wood)).isEqualTo(woodQty);
  }

  @Test
  @DisplayName("Should get quantity by item type")
  void testGetQuantityByType() {
    inventory.add(wood, 10);

    assertThat(inventory.getQuantity("wood")).isEqualTo(10);
    assertThat(inventory.getQuantity("stone")).isEqualTo(0);
  }

  @Test
  @DisplayName("Should get all item types")
  void testGetItems() {
    inventory.add(wood, 5);
    inventory.add(stone, 3);

    assertThat(inventory.getItems()).containsExactlyInAnyOrder("wood", "stone");
  }
}
