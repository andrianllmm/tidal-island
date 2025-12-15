package io.tidalisland.worldbuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tidalisland.tiles.WorldMapLoader.WorldMapData;
import io.tidalisland.utils.Position;
import io.tidalisland.worldobjects.WorldObjectLoader.ObjectEntry;
import io.tidalisland.worldobjects.WorldObjectLoader.WorldObjectData;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Exports map data to external folders.
 */
public class WorldExporter {

  private static final String EXPORT_BASE = "exported";

  /** Exports map data to external folders. */
  public static void export(EditorState state, String tilesetPath) throws Exception {
    ObjectMapper mapper = new ObjectMapper();

    File mapsDir = getOrCreateExportDir("maps");
    File worldObjectsDir = getOrCreateExportDir("worldobjects");

    exportWorldMap(state, mapper, mapsDir, tilesetPath);
    exportWorldObjects(state, mapper, worldObjectsDir);
  }

  private static File getOrCreateExportDir(String folderName) {
    File dir = new File(EXPORT_BASE, folderName);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return dir;
  }

  private static void exportWorldMap(EditorState state, ObjectMapper mapper, File dir,
      String tilesetPath) throws Exception {
    List<List<Integer>> layout = new ArrayList<>();
    for (int y = 0; y < state.getMapHeight(); y++) {
      List<Integer> row = new ArrayList<>();
      for (int x = 0; x < state.getMapWidth(); x++) {
        row.add(state.getTileId(x, y));
      }
      layout.add(row);
    }

    WorldMapData mapData = new WorldMapData();
    mapData.tileset = tilesetPath;
    mapData.layout = layout;

    mapper.writerWithDefaultPrettyPrinter().writeValue(new File(dir, "map.json"), mapData);
  }

  private static void exportWorldObjects(EditorState state, ObjectMapper mapper, File dir)
      throws Exception {
    List<ObjectEntry> objects = new ArrayList<>();
    for (Map.Entry<Position, String> entry : state.getWorldObjects().entrySet()) {
      ObjectEntry obj = new ObjectEntry();
      obj.id = entry.getValue();
      obj.position = Arrays.asList(entry.getKey().getX(), entry.getKey().getY());
      objects.add(obj);
    }

    WorldObjectData worldObjData = new WorldObjectData();
    worldObjData.objects = objects;

    mapper.writerWithDefaultPrettyPrinter().writeValue(new File(dir, "worldobjects.json"),
        worldObjData);
  }
}
