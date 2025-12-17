package io.tidalisland.crafting;

import static org.assertj.core.api.Assertions.*;

import io.tidalisland.inventory.Inventory;
import io.tidalisland.items.*;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CraftingManager Tests")
class CraftingManagerTest {

  private CraftingManager craftingManager;
  private Inventory inventory;

  @BeforeEach
  void setUp() {
    craftingManager = new CraftingManager();
    inventory = new Inventory(24);
  }

  @Test
  @DisplayName("Should craft recipe successfully")
  void testCraftSuccess() {
    Recipe recipe = craftingManager.getAllRecipes().get(0);
    for (Map.Entry<ItemType, Integer> entry : recipe.getIngredients().entrySet()) {
      inventory.add(ItemRegistry.create(entry.getKey()), entry.getValue());
    }

    boolean crafted = craftingManager.craft(recipe, inventory);

    assertThat(crafted).isTrue();
  }

  @Test
  @DisplayName("Should fail to craft without sufficient items")
  void testCraftFailure() {
    Recipe recipe = craftingManager.getAllRecipes().get(0);

    boolean crafted = craftingManager.craft(recipe, inventory);

    assertThat(crafted).isFalse();
  }

  @Test
  @DisplayName("Should get all recipes")
  void testGetAllRecipes() {
    List<Recipe> recipes = craftingManager.getAllRecipes();

    assertThat(recipes).isNotEmpty();
    assertThat(recipes).hasSizeGreaterThanOrEqualTo(2);
  }

  @Test
  @DisplayName("Should get available recipes with sufficient items")
  void testGetAvailableRecipes() {
    inventory.add(new Wood(), 10);

    List<Recipe> available = craftingManager.getAvailableRecipes(inventory);

    assertThat(available).isNotEmpty();
    assertThat(available).allMatch(r -> r.canCraft(inventory));
  }

  @Test
  @DisplayName("Should return empty list when no recipes available")
  void testNoAvailableRecipes() {
    List<Recipe> available = craftingManager.getAvailableRecipes(inventory);

    assertThat(available).isEmpty();
  }
}
