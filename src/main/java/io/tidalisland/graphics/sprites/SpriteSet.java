package io.tidalisland.graphics.sprites;

import java.util.HashMap;
import java.util.Map;

/**
 * A set of {@link Sprite}s.
 */
public class SpriteSet {

  private final Map<String, Sprite> sprites = new HashMap<>();
  private Sprite current;

  /**
   * Adds a sprite to the set.
   *
   * @param tag the tag to semantically identify the sprite
   * @param sprite the sprite
   */
  public void addSprite(String tag, Sprite sprite) {
    sprites.put(tag, sprite);
    if (current == null) {
      current = sprite;
    }
  }

  /**
   * Sets the current sprite by tag.
   *
   * @param tag the tag of the sprite to set
   */
  public void setTag(String tag) {
    Sprite next = sprites.get(tag);

    if (next == null) {
      System.err.println("Warning: missing tag '" + tag + "'");
      return;
    }

    if (next == current) {
      return;
    }

    if (current != null) {
      current.stop();
    }

    current = next;
    current.play();
  }

  /**
   * Updates the current sprite.
   */
  public void update() {
    if (current != null) {
      current.update();
    }
  }

  public Sprite getCurrent() {
    return current;
  }

  public SpriteFrame getCurrentFrame() {
    return current == null ? null : current.getFrame();
  }
}
