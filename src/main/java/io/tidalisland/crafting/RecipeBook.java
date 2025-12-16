package io.tidalisland.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the recipe book containing a list of {@link Recipe}s.
 */
public class RecipeBook {

  private List<Recipe> recipes = new ArrayList<>();

  /**
   * Creates a new recipe book.
   *
   * @param recipes the list of recipes
   */
  public RecipeBook(List<Recipe> recipes) {
    this.recipes = recipes;
  }

  /**
   * Gets all recipes.
   *
   * @return a list of recipes
   */
  public List<Recipe> getAllRecipes() {
    return Collections.unmodifiableList(recipes);
  }
}
