package io.tidalisland.events;

/** A listener for events of type E. */
public interface EventListener<E extends Event> {
  /** Called when an event is fired. */
  void onEvent(E event);
}
