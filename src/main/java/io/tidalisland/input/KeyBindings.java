package io.tidalisland.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores key bindings.
 */
public class KeyBindings {

  private final Map<Action, List<Integer>> keyBindings;

  /**
   * Creates key bindings from a map.
   *
   * @param keyBindings the key bindings
   */
  public KeyBindings(Map<Action, List<Integer>> keyBindings) {
    this.keyBindings = keyBindings;
  }

  /**
   * Creates empty key bindings.
   */
  public KeyBindings() {
    this.keyBindings = new HashMap<Action, List<Integer>>();
  }

  /**
   * Adds a key binding.
   *
   * @param action the action
   * @param keys the keys
   */
  public void add(Action action, List<Integer> keys) {
    if (keyBindings.containsKey(action)) {
      keyBindings.get(action).addAll(keys);
    } else {
      keyBindings.put(action, new ArrayList<>(keys));
    }
  }

  /**
   * Adds a key binding.
   *
   * @param action the action
   * @param key the key
   */
  public void add(Action action, int key) {
    add(action, new ArrayList<>(List.of(key)));
  }

  /**
   * Gets a key binding.
   *
   * @param action the action
   * @return the key binding
   */
  public List<Integer> get(Action action) {
    List<Integer> keys = keyBindings.get(action);
    return keys == null ? List.of() : List.copyOf(keys);
  }


  /**
   * Removes a key binding.
   *
   * @param action the action
   * @param key the key
   */
  public void remove(Action action, int key) {
    List<Integer> keys = keyBindings.get(action);
    if (keys != null) {
      keys.remove((Integer) key); // cast to remove object, not index
    }
  }


  /**
   * Sets a key binding.
   *
   * @param action the action
   * @param keys the keys
   */
  public void set(Action action, List<Integer> keys) {
    keyBindings.put(action, keys);
  }

  /**
   * Gets all actions.
   *
   * @return a list of actions
   */
  public List<Action> getActions() {
    return keyBindings.keySet().stream().toList();
  }

  /**
   * Gets all keys.
   *
   * @return a list of keys
   */
  public List<Integer> getKeys() {
    return keyBindings.values().stream().flatMap(List::stream).toList();
  }

  /**
   * Checks if the key bindings contain a given action.
   *
   * @param action the action
   * @return true if the action has a binding, false otherwise
   */
  public boolean has(Action action) {
    return keyBindings.containsKey(action) && keyBindings.get(action).size() > 0;
  }

  /**
   * Checks if the key bindings contain a given action and key.
   *
   * @param action the action
   * @param key the key
   * @return true if the action and key have a binding, false otherwise
   */
  public boolean has(Action action, int key) {
    return has(action) && keyBindings.get(action).contains(key);
  }
}
