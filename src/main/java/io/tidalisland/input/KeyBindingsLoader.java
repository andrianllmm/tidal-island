package io.tidalisland.input;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Loader for {@link KeyBindings}.
 */
public class KeyBindingsLoader {

  /**
   * Loads key bindings from a JSON file.
   */
  public static KeyBindings load(String path) {
    ObjectMapper mapper = new ObjectMapper();
    try (InputStream is = KeyBindingsLoader.class.getResourceAsStream(path)) {
      if (is == null) {
        throw new RuntimeException("Keybindings file not found: " + path);
      }

      KeyBindings keyBindings = new KeyBindings();
      Map<String, List<String>> data = mapper.readValue(is, new TypeReference<>() {});
      for (Entry<String, List<String>> entry : data.entrySet()) {
        Action action = Action.valueOf(entry.getKey().toUpperCase());
        List<Integer> codes = entry.getValue().stream().map(KeyCodeUtils::getCode).toList();
        keyBindings.add(action, codes);
      }

      return keyBindings;
    } catch (Exception ex) {
      throw new RuntimeException("Failed to load keybindings", ex);
    }
  }
}
