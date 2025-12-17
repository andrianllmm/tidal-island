package io.tidalisland.graphics.sprites;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for creating {@link SpriteSet}s.
 */
public class SpriteSetBuilder {

  private SpriteSetBuilder() {}

  /**
   * Builds a sprite set from a sprite atlas and sheet.
   */
  public static SpriteSet build(SpriteAtlas atlas, SpriteSheet sheet) {
    SpriteSet spriteSet = new SpriteSet();
    if (sheet.meta.frameTags.isEmpty()) {
      // No tags, create one sprite assuming all frames are in under one tag
      List<SpriteFrame> frames = new ArrayList<>();
      for (SpriteSheet.FrameData fd : sheet.frames) {
        frames.add(new SpriteFrame(atlas.getFrame(fd.frame.x, fd.frame.y, fd.frame.w, fd.frame.h),
            fd.duration));
      }
      spriteSet.addSprite("default", new Sprite(frames));
    } else {
      for (SpriteSheet.FrameTag tag : sheet.meta.frameTags) {
        List<SpriteFrame> frames = new ArrayList<>();
        for (int i = tag.from; i <= tag.to; i++) {
          SpriteSheet.FrameData fd = sheet.frames.get(i);
          frames.add(new SpriteFrame(atlas.getFrame(fd.frame.x, fd.frame.y, fd.frame.w, fd.frame.h),
              fd.duration));
        }
        int repeat = tag.repeat == null ? -1 : Integer.parseInt(tag.repeat);
        Sprite sprite = new Sprite(frames, repeat);
        spriteSet.addSprite(tag.name, sprite);
      }
    }
    return spriteSet;
  }

  /** Builds a sprite set from a sprite sheet. */
  public static SpriteSet build(String atlasPath, String sheetPath) {
    SpriteAtlas atlas = new SpriteAtlas(atlasPath);
    SpriteSheet sheet = SpriteSheet.load(sheetPath);
    return build(atlas, sheet);
  }
}
