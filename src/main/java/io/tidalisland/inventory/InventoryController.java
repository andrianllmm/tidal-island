package io.tidalisland.inventory;

import io.tidalisland.entities.Player;
import io.tidalisland.items.Edible;
import io.tidalisland.items.Item;
import io.tidalisland.items.Placeable;
import io.tidalisland.items.Tool;
import io.tidalisland.worldobjects.WorldObjectManager;

/**
 * Controller for {@link Inventory} actions.
 */
public class InventoryController {

  private final Inventory inventory;
  private final Player player;
  private final WorldObjectManager worldObjectManager;

  /**
   * Creates a new inventory controller.
   */
  public InventoryController(Inventory inventory, Player player, WorldObjectManager wom) {
    this.inventory = inventory;
    this.player = player;
    this.worldObjectManager = wom;
  }

  /**
   * Places an item in the world.
   *
   * @param item the item
   * @return true if the item was placed, false otherwise
   */
  public boolean placeItem(Item item) {
    if (!(item instanceof Placeable)) {
      return false;
    }
    Placeable placeable = (Placeable) item;
    if (placeable.place(worldObjectManager, player)) {
      inventory.remove(item, 1);
      return true;
    }
    return false;
  }


  /**
   * Eats an item.
   *
   * @param item the item
   * @return true if the item was eaten, false otherwise
   */
  public boolean eatItem(Item item) {
    if (!(item instanceof Edible)) {
      return false;
    }
    Edible edible = (Edible) item;
    if (edible.getHungerValue() > 0) {
      player.eat(edible);
      inventory.remove(item, 1);
      return true;
    }
    return false;
  }

  /**
   * Equips an item.
   *
   * @param item the item
   * @return true if the item was equipped, false otherwise
   */
  public boolean equipItem(Item item) {
    if (!(item instanceof Tool)) {
      return false;
    }
    Tool tool = (Tool) item;
    player.getEquipment().equip(tool);
    return true;
  }

  public Inventory getInventory() {
    return inventory;
  }

  public Player getPlayer() {
    return player;
  }

  public WorldObjectManager getWorldObjectManager() {
    return worldObjectManager;
  }
}
