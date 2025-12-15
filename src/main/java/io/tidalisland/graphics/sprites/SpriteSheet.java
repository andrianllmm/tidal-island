package io.tidalisland.graphics.sprites;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;

/**
 * A sprite sheet.
 */
public class SpriteSheet {
  public List<FrameData> frames;
  public Meta meta;

  public SpriteSheet() {}

  /**
   * Loads a sprite sheet from a file.
   *
   * @param path the path to the file
   * @return the loaded sprite sheet
   */
  public static SpriteSheet load(String path) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      InputStream is = SpriteSheet.class.getResourceAsStream(path);
      if (is == null) {
        throw new IllegalArgumentException("JSON not found: " + path);
      }
      return mapper.readValue(is, SpriteSheet.class);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to load sprite sheet: " + e.getMessage(), e);
    }
  }

  /** Sprite sheet frame data. */
  public static class FrameData {
    public String filename;
    public Frame frame;
    public int duration;
  }

  /** Sprite sheet meta data. */
  public static class Meta {
    public List<FrameTag> frameTags;
    public List<Layer> layers;
  }

  /** Sprite sheet frame. */
  public static class Frame {
    public int x;
    public int y;
    public int w;
    public int h;
  }

  /** Sprite sheet frame tag. */
  public static class FrameTag {
    public String name;
    public int from;
    public int to;
    public String direction;
    public String color;
  }

  /** Sprite sheet layer. */
  public static class Layer {
    public String name;
    public int opacity;
    public String blendMode;
  }

}
