package io.tidalisland.ui;

import io.tidalisland.crafting.CraftingManager;
import io.tidalisland.crafting.Recipe;
import io.tidalisland.input.Action;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.inventory.Inventory;
import io.tidalisland.items.Item;
import io.tidalisland.items.ItemRegistry;
import io.tidalisland.items.ItemType;
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
 * UI panel for crafting.
 */
public class UiCraftingPanel extends UiPanel {

  private final Inventory inventory;
  private final CraftingManager craftingManager;

  private Recipe selectedRecipe;

  private final RecipeGrid recipeGrid;
  private final IngredientGrid ingredientGrid;
  private final ResultView resultView;
  private final UiButton craftButton;

  /**
   * Creates a new crafting panel.
   */
  public UiCraftingPanel(Inventory inventory, CraftingManager craftingManager) {
    super(296, 600);
    this.inventory = inventory;
    this.craftingManager = craftingManager;

    setVisible(false);
    setStyle(UiStyleDirector.makeTransparent());
    style(s -> s.padding(8));
    setLayout(new VerticalStackLayout(16));
    getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.TOP);

    UiPanel top = new UiPanel(width, 220);
    UiPanel middle = new UiPanel(width, 120);
    UiPanel bottom = new UiPanel(width, 110);
    top.style(s -> s.padding(8));
    middle.style(s -> s.padding(8));
    bottom.style(s -> s.padding(8));
    top.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.TOP);
    middle.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.TOP);
    bottom.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.TOP);
    add(top);
    add(middle);
    add(bottom);

    // Title
    UiLabel title = new UiLabel("Crafting Bench", 300, 28);
    title.style(s -> s.fontSize(16));
    top.add(title);

    // Recipe grid header
    UiLabel recipeHeader = new UiLabel("Recipes", 300, 18);
    recipeHeader.style(s -> s.fontSize(14));
    top.add(recipeHeader);

    // Recipe grid
    recipeGrid = new RecipeGrid(width - 16, 140);
    top.add(recipeGrid);

    // Ingredients header
    UiLabel ingHeader = new UiLabel("Ingredients", 300, 18);
    ingHeader.style(s -> s.fontSize(14));
    middle.add(ingHeader);

    ingredientGrid = new IngredientGrid(width - 16, 72);
    middle.add(ingredientGrid);

    // Result header
    resultView = new ResultView(width - 16, 64);
    bottom.add(resultView);

    // Craft button
    craftButton = new UiButton("Craft", 70, 20);
    craftButton.style(s -> s.borderWidth(0).fontSize(14));
    craftButton.setOnClick(this::craftSelected);
    bottom.add(craftButton);

    refresh();
    inventory.addListener(evt -> refresh());
  }

  @Override
  public void onUpdate(KeyHandler keys, MouseHandler mouse) {
    if (keys.isJustPressed(Action.TOGGLE_CRAFTING)) {
      toggleVisible();
    }
  }

  private void refresh() {
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
      refresh();
    }
  }

  /**
   * Displays recipes in a grid.
   */
  class RecipeGrid extends UiPanel {
    public RecipeGrid(int width, int height) {
      super(width, height);
      setStyle(UiStyleDirector.makeTransparent());
      setLayout(new GridLayout(4, 64, 64, 4, 4));
    }

    public void refresh() {
      beginBatch();
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
          runAfterUpdate(UiCraftingPanel.this::refresh);
        });

        Item item = recipe.getResult().getItem();
        UiImage icon = new UiImage(item.getSprite().getImage(), 36, 36);
        slot.add(icon);

        add(slot);
      }

      endBatch();
    }
  }

  /**
   * Displays ingredients in a grid.
   */
  class IngredientGrid extends UiPanel {
    public IngredientGrid(int width, int height) {
      super(width, height);
      setStyle(UiStyleDirector.makeTransparent());
      setLayout(new GridLayout(4, 64, 64, 4, 4));
    }

    public void refresh() {
      beginBatch();
      getChildren().clear();
      if (selectedRecipe == null) {
        return;
      }

      for (Map.Entry<ItemType, Integer> entry : selectedRecipe.getIngredients().entrySet()) {
        ItemType itemType = entry.getKey();

        Item temp = ItemRegistry.create(itemType);

        UiPanel slot = new UiPanel(64, 64);
        slot.setStyle(UiStyleDirector.makeTransparent());
        slot.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

        UiImage icon = new UiImage(temp.getSprite().getImage(), 36, 36);
        slot.add(icon);

        int needed = entry.getValue();
        int have = inventory.getQuantity(temp);
        UiLabel qty = new UiLabel(have + "/" + needed, 36, 12);
        qty.style(s -> s.fontSize(12));
        slot.add(qty);

        add(slot);
      }

      endBatch();
    }
  }

  /**
   * Shows result item preview.
   */
  class ResultView extends UiPanel {
    public ResultView(int width, int height) {
      super(width, height);
      setStyle(UiStyleDirector.makeTransparent());
      getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    }

    public void refresh() {
      beginBatch();
      getChildren().clear();
      if (selectedRecipe == null) {
        endBatch();
        return;
      }

      UiPanel slot = new UiPanel(64, 64);
      slot.setStyle(UiStyleDirector.makeTransparent());
      slot.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
      slot.style(s -> s.borderWidth(2).borderColor(new Color(60, 60, 60)).cornerRadius(8));

      Item item = selectedRecipe.getResult().getItem();
      int qty = selectedRecipe.getResult().getQuantity();

      UiImage icon = new UiImage(item.getSprite().getImage(), 36, 36);
      slot.add(icon);
      UiLabel label = new UiLabel("x" + qty, 36, 12);
      label.style(s -> s.fontSize(12));
      slot.add(label);

      add(slot);

      endBatch();
    }
  }
}
