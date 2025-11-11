package io.tidalisland.graphics;

import io.tidalisland.graphics.SpriteAtlas.AtlasFrame;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Imports sprite sets.
 */
public class SpriteSetImporter {
  /**
   * Imports a sprite set from an object definition.
   */
  public static SpriteSet fromDefinition(SpriteAtlas atlas, Map<String, List<AtlasFrame>> config) {
    SpriteSet spriteSet = new SpriteSet();

    for (Map.Entry<String, List<AtlasFrame>> entry : config.entrySet()) {
      List<SpriteFrame> frames = new ArrayList<>();
      for (AtlasFrame af : entry.getValue()) {
        frames.add(new SpriteFrame(atlas.getFrame(af.x, af.y, af.width, af.height)));
      }
      spriteSet.addSprite(entry.getKey(), new Sprite(frames));
    }

    return spriteSet;
  }

  /**
   * Imports a sprite set from a JSON sheet.
   */
  public static SpriteSet fromJsonSheet(SpriteAtlas atlas, String jsonPath) {

    SpriteSet spriteSet = new SpriteSet();

    JSONObject jsonData = loadJson(jsonPath);
    JSONArray jsonFrames = jsonData.getJSONArray("frames");
    JSONObject meta = jsonData.getJSONObject("meta");
    JSONArray frameTags = meta.getJSONArray("frameTags");

    for (int i = 0; i < frameTags.length(); i++) {
      JSONObject frameTag = frameTags.getJSONObject(i);
      String tag = frameTag.getString("name");
      int start = frameTag.getInt("from");
      int end = frameTag.getInt("to");

      List<SpriteFrame> frames = new ArrayList<>();
      for (int j = start; j <= end; j++) {
        JSONObject jsonFrameData = jsonFrames.getJSONObject(j);
        JSONObject frameData = jsonFrameData.getJSONObject("frame");
        int x = frameData.getInt("x");
        int y = frameData.getInt("y");
        int width = frameData.getInt("w");
        int height = frameData.getInt("h");
        int duration = jsonFrameData.getInt("duration");
        frames.add(new SpriteFrame(atlas.getFrame(x, y, width, height), duration));
      }

      spriteSet.addSprite(tag, new Sprite(frames));
    }

    return spriteSet;
  }

  private static JSONObject loadJson(String path) {
    try {
      InputStream stream = SpriteSetImporter.class.getResourceAsStream(path);
      if (stream == null) {
        throw new IllegalArgumentException("JSON not found: " + path);
      }
      String jsonText = new String(stream.readAllBytes());
      return new JSONObject(jsonText);
    } catch (Exception e) {
      throw new RuntimeException("Failed to load data: " + e.getMessage(), e);
    }
  }

}
