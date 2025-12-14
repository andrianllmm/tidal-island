package io.tidalisland.ui;

import io.tidalisland.crafting.CraftingManager;
import io.tidalisland.crafting.Recipe;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.inventory.Inventory;
import io.tidalisland.items.Item;
import io.tidalisland.items.ItemRegistry;
import io.tidalisland.ui.components.UiButton;
import io.tidalisland.ui.components.UiImage;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.layout.GridLayout;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.layout.VerticalStackLayout;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Color;
import java.util.Map;

/**
 * Crafting window UI.
 */
public class UiCraftingPanel extends UiPanel {
  private final Inventory inventory;
  private final CraftingManager craftingManager;

  private final RecipeGrid recipeGrid;
  private final IngredientGrid ingredientGrid;
  private final ResultView resultView;
  private final UiButton craftButton;

  private Recipe selectedRecipe;

  /**
   * Creates a new crafting panel.
   */
  public UiCraftingPanel(Inventory inventory, CraftingManager craftingManager) {
    super(300, 560);
    this.inventory = inventory;
    this.craftingManager = craftingManager;

    style(s -> s.padding(16));
    setLayout(new VerticalStackLayout(12));
    getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    setStyle(UiStyleDirector.makePanel());

    // Title
    UiLabel title = new UiLabel("Crafting Bench", 300, 28);
    title.style(s -> s.fontSize(16));
    add(title);

    // Recipe grid header
    UiLabel recipeHeader = new UiLabel("Recipes", 300, 18);
    recipeHeader.style(s -> s.fontSize(14));
    add(recipeHeader);

    // Recipe grid
    recipeGrid = new RecipeGrid();
    add(recipeGrid);

    // Ingredients header
    UiLabel ingHeader = new UiLabel("Ingredients", 300, 18);
    ingHeader.style(s -> s.fontSize(14));
    add(ingHeader);

    ingredientGrid = new IngredientGrid();
    add(ingredientGrid);

    // Result header
    UiLabel resultHeader = new UiLabel("Result", 300, 18);
    resultHeader.style(s -> s.fontSize(14));
    add(resultHeader);

    resultView = new ResultView();
    add(resultView);

    // Craft button
    craftButton = new UiButton("Craft", 100, 24);
    craftButton.style(s -> s.borderWidth(0));
    craftButton.setOnClick(this::craftSelected);
    add(craftButton);

    visible = false;

    refreshAll();
    inventory.addListener(evt -> refreshAll());
  }

  private void refreshAll() {
    recipeGrid.refresh();
    ingredientGrid.refresh();
    resultView.refresh();
    craftButton.setEnabled(selectedRecipe != null && selectedRecipe.canCraft(inventory));
  }

  private void craftSelected() {
    if (selectedRecipe == null) {
      return;
    }
    if (craftingManager.craft(selectedRecipe, inventory)) {
      refreshAll();
    }
  }

  @Override
  public void onUpdate(KeyHandler keys, MouseHandler mouse) {
    if (keys.isJustPressed("toggle_crafting")) {
      toggleVisible();
    }
  }

  /**
   * Displays recipes in a grid.
   */
  class RecipeGrid extends UiPanel {
    public RecipeGrid() {
      super(276, 140);
      setLayout(new GridLayout(4, 64, 64, 4, 4));
      setStyle(UiStyleDirector.makeTransparent());
    }

    public void refresh() {
      getChildren().clear();
      for (Recipe recipe : craftingManager.getAllRecipes()) {
        UiPanel slot = new UiPanel(64, 64);
        slot.setStyle(UiStyleDirector.makeTransparent());
        slot.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        slot.style(s -> s.borderColor(Color.WHITE).cornerRadius(8));

        if (recipe == selectedRecipe) {
          slot.style(s -> s.borderWidth(2));
        } else {
          slot.style(s -> s.borderWidth(0));
        }

        slot.setOnClick(() -> {
          selectedRecipe = (selectedRecipe == recipe) ? null : recipe;
          runAfterUpdate(UiCraftingPanel.this::refreshAll);
        });

        Item item = recipe.getResult().getItem();
        UiImage icon = new UiImage(item.getSprite().getFrame().getImage(), 36, 36);
        slot.add(icon);

        add(slot);
      }
    }
  }

  /**
   * Displays ingredients in a grid.
   */
  class IngredientGrid extends UiPanel {
    public IngredientGrid() {
      super(276, 140);
      setLayout(new GridLayout(4, 64, 64, 4, 4));
      setStyle(UiStyleDirector.makeTransparent());
    }

    public void refresh() {
      getChildren().clear();
      if (selectedRecipe == null) {
        return;
      }

      for (Map.Entry<String, Integer> entry : selectedRecipe.getIngredients().entrySet()) {
        String itemType = entry.getKey();

        Item temp = ItemRegistry.create(itemType);

        UiPanel slot = new UiPanel(64, 64);
        slot.setStyle(UiStyleDirector.makeTransparent());
        slot.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

        UiImage icon = new UiImage(temp.getSprite().getFrame().getImage(), 36, 36);
        slot.add(icon);

        int needed = entry.getValue();
        int have = inventory.getQuantity(temp);
        UiLabel qty = new UiLabel(have + "/" + needed, 36, 12);
        qty.style(s -> s.fontSize(12));
        slot.add(qty);

        add(slot);
      }
    }
  }

  /**
   * Shows result item preview.
   */
  class ResultView extends UiPanel {
    public ResultView() {
      super(48, 48);
      setStyle(UiStyleDirector.makeTransparent());
      getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    }

    public void refresh() {
      getChildren().clear();
      if (selectedRecipe == null) {
        return;
      }

      Item item = selectedRecipe.getResult().getItem();
      int qty = selectedRecipe.getResult().getQuantity();
      UiImage icon = new UiImage(item.getSprite().getFrame().getImage(), 36, 36);
      add(icon);
      UiLabel label = new UiLabel("x" + qty, 36, 12);
      label.style(s -> s.fontSize(12));
      add(label);
    }
  }
}
