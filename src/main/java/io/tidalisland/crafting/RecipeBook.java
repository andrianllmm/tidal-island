package io.tidalisland.crafting;

import io.tidalisland.items.ItemStack;
import io.tidalisland.items.Plank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Represents the recipe book.
 */
public class RecipeBook {
  private static final List<Recipe> RECIPES = new ArrayList<>();

  static {
    RECIPES.add(new Recipe(Map.of("wood", 1), new ItemStack<Plank>(new Plank(), 2)));
  }

  public static List<Recipe> getAllRecipes() {
    return Collections.unmodifiableList(RECIPES);
  }
}
