package io.tidalisland.events;

import io.tidalisland.items.ItemType;

/**
 * Event triggered when equipment changes.
 */
public class EquipmentChangeEvent implements Event {

  private final ItemType toolType;
  private final boolean equipped;

  /**
   * Creates a new equipment change event.
   *
   * @param toolType the tool type
   * @param equipped true if equipped, false otherwise
   */
  public EquipmentChangeEvent(ItemType toolType, boolean equipped) {
    this.toolType = toolType;
    this.equipped = equipped;
  }

  public ItemType getToolType() {
    return toolType;
  }

  public boolean isEquipped() {
    return equipped;
  }
}
