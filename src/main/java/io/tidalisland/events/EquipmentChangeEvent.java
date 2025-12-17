package io.tidalisland.events;

/**
 * Event triggered when equipment changes.
 */
public class EquipmentChangeEvent implements Event {

  private final String toolType;
  private final boolean equipped;

  /**
   * Creates a new equipment change event.
   *
   * @param toolType the tool type
   * @param equipped true if equipped, false otherwise
   */
  public EquipmentChangeEvent(String toolType, boolean equipped) {
    this.toolType = toolType;
    this.equipped = equipped;
  }

  public String getToolType() {
    return toolType;
  }

  public boolean isEquipped() {
    return equipped;
  }
}
