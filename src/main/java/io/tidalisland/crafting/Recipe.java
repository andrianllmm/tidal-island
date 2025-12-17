package io.tidalisland.crafting;

import io.tidalisland.inventory.Inventory;
import io.tidalisland.items.Item;
import io.tidalisland.items.ItemStack;
import io.tidalisland.items.ItemType;
import java.util.Map;

/**
 * Represents a recipe for crafting.
 */
public class Recipe {

  private final Map<ItemType, Integer> ingredients;
  private final ItemStack<? extends Item> result;

  public Recipe(Map<ItemType, Integer> ingredients, ItemStack<? extends Item> result) {
    this.ingredients = ingredients;
    this.result = result;
  }

  /**
   * Returns the ingredients of this recipe.
   *
   * @return a map of item type -> quantity
   */
  public Map<ItemType, Integer> getIngredients() {
    return ingredients;
  }

  /**
   * Get the result of this recipe.
   *
   * @return the result
   */
  public ItemStack<? extends Item> getResult() {
    return result;
  }

  /**
   * Checks if this recipe can be crafted with the given inventory.
   *
   * @param inventory the inventory
   * @return true if the recipe can be crafted, false otherwise
   */
  public boolean canCraft(Inventory inventory) {
    for (Map.Entry<ItemType, Integer> entry : ingredients.entrySet()) {
      if (inventory.getQuantity(entry.getKey()) < entry.getValue()) {
        return false;
      }
    }
    return true;
  }
}

