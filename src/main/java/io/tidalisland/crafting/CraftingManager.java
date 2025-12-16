package io.tidalisland.crafting;

import io.tidalisland.inventory.Inventory;
import io.tidalisland.items.ItemRegistry;
import java.util.List;

/**
 * Manages crafting.
 */
public class CraftingManager {

  private final RecipeBook recipeBook;

  public CraftingManager() {
    this.recipeBook = RecipeBookLoader.load("/recipes/recipebook.json");
  }

  /**
   * Gets the recipe book.
   *
   * @return the recipe book
   */
  public RecipeBook getRecipeBook() {
    return recipeBook;
  }

  /**
   * Gets all recipes.
   *
   * @return a list of recipes
   */
  public List<Recipe> getAllRecipes() {
    return recipeBook.getAllRecipes();
  }

  /**
   * Gets all available recipes for the given inventory.
   *
   * @param inventory the inventory
   * @return a list of recipes
   */
  public List<Recipe> getAvailableRecipes(Inventory inventory) {
    return recipeBook.getAllRecipes().stream().filter(r -> r.canCraft(inventory)).toList();
  }

  /**
   * Crafts a recipe with the given inventory.
   *
   * @param recipe the recipe
   * @param inventory the inventory
   * @return true if the recipe was crafted, false otherwise
   */
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
