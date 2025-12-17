package io.tidalisland.crafting;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tidalisland.items.Item;
import io.tidalisland.items.ItemRegistry;
import io.tidalisland.items.ItemStack;
import io.tidalisland.items.ItemType;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loader for {@link RecipeBook}s.
 */
public class RecipeBookLoader {

  /**
   * Loads a recipe book from the JSON file.
   */
  public static RecipeBook load(String path) {
    ObjectMapper mapper = new ObjectMapper();
    try (InputStream is = RecipeBookLoader.class.getResourceAsStream(path)) {
      if (is == null) {
        throw new RuntimeException("Recipe book file not found: " + path);
      }

      List<RecipeData> recipeData = mapper.readValue(is, new TypeReference<>() {});
      List<Recipe> recipes = new ArrayList<>();
      for (RecipeData rd : recipeData) {
        Item item = ItemRegistry.create(rd.result.itemId);
        ItemStack<? extends Item> stack = new ItemStack<>(item, rd.result.quantity);
        Map<ItemType, Integer> ingredients = new HashMap<>();
        for (Map.Entry<String, Integer> entry : rd.ingredients.entrySet()) {
          ItemType type = ItemRegistry.getTypeById(entry.getKey());
          ingredients.put(type, entry.getValue());
        }
        recipes.add(new Recipe(ingredients, stack));
      }

      return new RecipeBook(recipes);

    } catch (IOException e) {
      throw new RuntimeException("Failed to load recipes", e);
    }
  }

  private static class RecipeData {
    public Map<String, Integer> ingredients;
    public Result result;

    private static class Result {
      public String itemId;
      public int quantity;
    }
  }
}
