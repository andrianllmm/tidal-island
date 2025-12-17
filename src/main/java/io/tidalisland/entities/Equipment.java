package io.tidalisland.entities;

import io.tidalisland.events.EquipmentChangeEvent;
import io.tidalisland.events.EventListener;
import io.tidalisland.events.Observable;
import io.tidalisland.items.ItemType;
import io.tidalisland.items.Tool;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages tool equipment for an entity.
 */
public class Equipment implements Observable<EquipmentChangeEvent> {

  private Tool equippedTool;

  private final CopyOnWriteArrayList<EventListener<EquipmentChangeEvent>> listeners =
      new CopyOnWriteArrayList<>();

  /**
   * Equips a tool.
   *
   * @param tool the tool to equip
   * @return the previously equipped tool, or null if none
   */
  public Tool equipTool(Tool tool) {
    Tool lastTool = this.equippedTool;
    this.equippedTool = tool;
    emitChange(this.equippedTool.getType(), true);
    return lastTool;
  }

  /**
   * Unequips the current tool.
   *
   * @return the previously equipped tool, or null if none
   */
  public Tool unequipTool() {
    emitChange(this.equippedTool.getType(), false);
    Tool lastTool = this.equippedTool;
    this.equippedTool = null;
    return lastTool;
  }

  /**
   * Gets the currently equipped tool.
   *
   * @return the equipped tool, or null if none
   */
  public Tool getEquippedTool() {
    return equippedTool;
  }

  /**
   * Checks if a tool is equipped.
   *
   * @return true if a tool is equipped
   */
  public boolean hasToolEquipped() {
    return equippedTool != null;
  }

  /**
   * Gets the damage multiplier from equipped tool.
   *
   * @return the damage multiplier, or 1.0 if no tool equipped
   */
  public double getDamageMultiplier() {
    if (hasToolEquipped()) {
      return equippedTool.getDamageMultiplier();
    }
    return 1.0;
  }

  /**
   * Checks if the equipped tool is effective against a target type.
   *
   * @param type the required tool type
   * @return true if equipped tool matches the type
   */
  public boolean hasEffectiveTool(ItemType type) {
    return hasToolEquipped() && equippedTool.getType() == type;
  }

  /**
   * Gets the durability percentage.
   *
   * @return durability as a percentage (0.0 to 1.0)
   */
  public double getDurabilityPercent() {
    if (!hasToolEquipped()) {
      return 0.0;
    }
    return (double) equippedTool.getCurrentDurability() / equippedTool.getDurability();
  }

  /** Emits an equipment change event. */
  private void emitChange(ItemType toolType, boolean equipped) {
    dispatch(new EquipmentChangeEvent(toolType, equipped), listeners);
  }

  @Override
  public void addListener(EventListener<EquipmentChangeEvent> listener) {
    listeners.add(listener);
  }

  @Override
  public void removeListener(EventListener<EquipmentChangeEvent> listener) {
    listeners.remove(listener);
  }
}
