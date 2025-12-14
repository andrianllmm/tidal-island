package io.tidalisland.crafting;

import io.tidalisland.inventory.Inventory;
import io.tidalisland.items.ItemRegistry;
import java.util.List;

/**
 * Manages crafting.
 */
public class CraftingManager {
  private final List<Recipe> recipes;

  public CraftingManager() {
    this.recipes = RecipeBook.getAllRecipes();
  }

  /** Returns all recipes. */
  public List<Recipe> getAllRecipes() {
    return recipes;
  }

  /** Returns all recipes that can be crafted using the given inventory. */
  public List<Recipe> getAvailableRecipes(Inventory inventory) {
    return recipes.stream().filter(r -> r.canCraft(inventory)).toList();
  }

  /** Crafts a recipe with the given inventory. */
  public boolean craft(Recipe recipe, Inventory inventory) {
    if (!recipe.canCraft(inventory)) {
      return false;
    }

    recipe.getIngredients()
        .forEach((itemType, qty) -> inventory.remove(ItemRegistry.create(itemType), qty));
    inventory.add(recipe.getResult().getItem(), recipe.getResult().getQuantity());
    return true;
  }
}
