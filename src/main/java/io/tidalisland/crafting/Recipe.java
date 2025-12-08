package io.tidalisland.crafting;

import io.tidalisland.inventory.Inventory;
import io.tidalisland.items.Item;
import io.tidalisland.items.ItemStack;
import java.util.Map;

/**
 * Represents a recipe for crafting.
 */
public class Recipe {
  private final Map<String, Integer> ingredients; // item type -> quantity
  private final ItemStack<? extends Item> result;

  public Recipe(Map<String, Integer> ingredients, ItemStack<? extends Item> result) {
    this.ingredients = ingredients;
    this.result = result;
  }

  /** Returns the ingredients of this recipe. */
  public Map<String, Integer> getIngredients() {
    return ingredients;
  }

  /** Get the result of this recipe. */
  public ItemStack<? extends Item> getResult() {
    return result;
  }

  /** Check if an inventory has enough items to craft this recipe. */
  public boolean canCraft(Inventory inventory) {
    for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
      if (inventory.getQuantity(entry.getKey()) < entry.getValue()) {
        return false;
      }
    }
    return true;
  }
}

