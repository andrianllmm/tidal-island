package io.tidalisland.events;

/**
 * A listener for {@link Event}s.
 */
public interface EventListener<E extends Event> {

  /**
   * Called when an event is fired.
   */
  void onEvent(E event);
}
