package io.tidalisland.crafting;

import io.tidalisland.items.ItemStack;
import io.tidalisland.items.Plank;
import io.tidalisland.items.RaftItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Represents the recipe book containing a list of {@link Recipe}s.
 */
public class RecipeBook {

  private static final List<Recipe> RECIPES = new ArrayList<>();

  static {
    RECIPES.add(new Recipe(Map.of("wood", 1), new ItemStack<Plank>(new Plank(), 2)));
    RECIPES
        .add(new Recipe(Map.of("wood", 4, "plank", 8), new ItemStack<RaftItem>(new RaftItem(), 1)));
  }

  /**
   * Gets all recipes.
   *
   * @return a list of recipes
   */
  public static List<Recipe> getAllRecipes() {
    return Collections.unmodifiableList(RECIPES);
  }
}
