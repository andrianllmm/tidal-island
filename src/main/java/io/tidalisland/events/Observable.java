package io.tidalisland.events;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents an observable object that can notify listeners of events.
 */
public interface Observable<E extends Event> {
  /**
   * Adds a listener to the observable.
   *
   * @param listener the listener
   */
  void addListener(EventListener<E> listener);

  /**
   * Removes a listener from the observable.
   *
   * @param listener the listener
   */
  void removeListener(EventListener<E> listener);

  /**
   * Call to dispatch an event to all listeners.
   *
   * @param event the event
   * @param listeners the listeners
   */
  default void dispatch(E event, CopyOnWriteArrayList<EventListener<E>> listeners) {
    // CopyOnWriteArrayList ensures thread-safe iteration
    // while listeners may be added/removed during dispatch
    for (EventListener<E> listener : listeners) {
      try {
        listener.onEvent(event);
      } catch (Throwable t) {
        // Handle exceptions so one bad listener doesn't break the chain
        t.printStackTrace();
      }
    }
  }
}

