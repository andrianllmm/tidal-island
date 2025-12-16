package io.tidalisland.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting key codes.
 */
public class KeyCodeUtils {

  private static final Map<String, Integer> NAME_TO_CODE = new HashMap<>();

  static {
    // Letters
    for (char c = 'A'; c <= 'Z'; c++) {
      NAME_TO_CODE.put(String.valueOf(c), KeyEvent.getExtendedKeyCodeForChar(c));
    }

    // Numbers
    for (char c = '0'; c <= '9'; c++) {
      NAME_TO_CODE.put(String.valueOf(c), KeyEvent.getExtendedKeyCodeForChar(c));
    }

    // Punctuation and symbols
    NAME_TO_CODE.put("COMMA", KeyEvent.VK_COMMA);
    NAME_TO_CODE.put("PERIOD", KeyEvent.VK_PERIOD);
    NAME_TO_CODE.put("SLASH", KeyEvent.VK_SLASH);
    NAME_TO_CODE.put("BACKSLASH", KeyEvent.VK_BACK_SLASH);
    NAME_TO_CODE.put("SEMICOLON", KeyEvent.VK_SEMICOLON);
    NAME_TO_CODE.put("QUOTE", KeyEvent.VK_QUOTE);
    NAME_TO_CODE.put("OPEN_BRACKET", KeyEvent.VK_OPEN_BRACKET);
    NAME_TO_CODE.put("CLOSE_BRACKET", KeyEvent.VK_CLOSE_BRACKET);
    NAME_TO_CODE.put("MINUS", KeyEvent.VK_MINUS);
    NAME_TO_CODE.put("EQUALS", KeyEvent.VK_EQUALS);
    NAME_TO_CODE.put("PLUS", KeyEvent.VK_PLUS);

    // Arrow keys
    NAME_TO_CODE.put("UP", KeyEvent.VK_UP);
    NAME_TO_CODE.put("DOWN", KeyEvent.VK_DOWN);
    NAME_TO_CODE.put("LEFT", KeyEvent.VK_LEFT);
    NAME_TO_CODE.put("RIGHT", KeyEvent.VK_RIGHT);

    // Special keys
    NAME_TO_CODE.put("ESCAPE", KeyEvent.VK_ESCAPE);
    NAME_TO_CODE.put("SPACE", KeyEvent.VK_SPACE);
    NAME_TO_CODE.put("ENTER", KeyEvent.VK_ENTER);
    NAME_TO_CODE.put("TAB", KeyEvent.VK_TAB);
    NAME_TO_CODE.put("BACKSPACE", KeyEvent.VK_BACK_SPACE);
    NAME_TO_CODE.put("DELETE", KeyEvent.VK_DELETE);
    NAME_TO_CODE.put("CAPS_LOCK", KeyEvent.VK_CAPS_LOCK);
    NAME_TO_CODE.put("SHIFT", KeyEvent.VK_SHIFT);
    NAME_TO_CODE.put("CTRL", KeyEvent.VK_CONTROL);
    NAME_TO_CODE.put("ALT", KeyEvent.VK_ALT);
    NAME_TO_CODE.put("META", KeyEvent.VK_META); // Windows/Command key
    NAME_TO_CODE.put("CONTEXT_MENU", KeyEvent.VK_CONTEXT_MENU);

    // Function keys
    for (int i = 1; i <= 12; i++) {
      NAME_TO_CODE.put("F" + i, KeyEvent.VK_F1 + (i - 1));
    }
  }


  /**
   * Converts a key name to a key code.
   *
   * @param name the key name
   * @return the key code
   */
  public static int getCode(String name) {
    Integer code = NAME_TO_CODE.get(name.toUpperCase());
    if (code == null) {
      throw new IllegalArgumentException("Unknown key name: " + name);
    }
    return code;
  }
}
