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

  /** Frame data. */
  public static class FrameData {
    public String filename;
    public Frame frame;
    public int duration;
  }

  /** Meta data. */
  public static class Meta {
    public List<FrameTag> frameTags;
    public List<Layer> layers;
  }

  /** Frame. */
  public static class Frame {
    public int x;
    public int y;
    public int w;
    public int h;
  }

  /** Frame tag. */
  public static class FrameTag {
    public String name;
    public int from;
    public int to;
    public String direction;
    public String color;
  }

  /** Layer. */
  public static class Layer {
    public String name;
    public int opacity;
    public String blendMode;
  }

}
