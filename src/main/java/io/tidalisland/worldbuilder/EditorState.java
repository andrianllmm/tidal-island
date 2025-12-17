package io.tidalisland.worldbuilder;

import io.tidalisland.tiles.Tile;
import io.tidalisland.tiles.TileSet;
import io.tidalisland.utils.Position;
import io.tidalisland.worldbuilder.actions.ActionHistory;
import io.tidalisland.worldbuilder.actions.EditorAction;
import io.tidalisland.worldobjects.WorldObjectType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central state container for {@link WorldBuilder}.
 */
public class EditorState {

  // Map data
  private final int[][] tileMap;
  private final Map<Position, WorldObjectType> worldObjects;
  private final TileSet tileSet;

  // Dimensions
  private final int mapWidth;
  private final int mapHeight;

  // Current selections
  private Tile selectedTile;
  private WorldObjectType selectedWorldObject;

  // View state
  private final ViewPort viewPort;

  // Tool state
  private boolean fillEnabled = false;
  private boolean isDrawing = false;

  // History
  private final ActionHistory actionHistory;

  // Inside EditorState.java
  private final List<Runnable> changeListeners = new ArrayList<>();

  /** Creates a new editor state. */
  public EditorState(int mapWidth, int mapHeight, TileSet tileSet) {
    this.mapWidth = mapWidth;
    this.mapHeight = mapHeight;
    this.tileSet = tileSet;
    this.tileMap = new int[mapWidth][mapHeight];
    this.worldObjects = new HashMap<>();
    this.viewPort = new ViewPort(mapWidth, mapHeight);
    this.actionHistory = new ActionHistory();

    // Initialize map with empty tiles
    for (int x = 0; x < mapWidth; x++) {
      for (int y = 0; y < mapHeight; y++) {
        tileMap[x][y] = -1;
      }
    }

    this.viewPort.setOnChange(() -> notifyChange());
  }

  /** Adds a listener that is notified when the editor state changes. */
  public void addChangeListener(Runnable listener) {
    changeListeners.add(listener);
  }

  /** Notifies all listeners that the editor state has changed. */
  public void notifyChange() {
    for (Runnable listener : changeListeners) {
      listener.run();
    }
  }

  /** Gets the tile id at a given position. */
  public int getTileId(int col, int row) {
    if (isInBounds(col, row)) {
      return tileMap[col][row];
    }
    return -1;
  }

  /** Sets the tile id at a given position. */
  public void setTileId(int col, int row, int tileId) {
    if (isInBounds(col, row)) {
      tileMap[col][row] = tileId;
      notifyChange();
    }
  }

  /** Gets the tile at a given position. */
  public Tile getTile(int col, int row) {
    int id = getTileId(col, row);
    return id >= 0 ? tileSet.get(id) : null;
  }

  /** Gets the world object id at a given position. */
  public WorldObjectType getWorldObject(Position pos) {
    return worldObjects.get(pos);
  }

  /** Sets the world object at a given position. */
  public void setWorldObject(Position pos, WorldObjectType type) {
    if (type == null) {
      worldObjects.remove(pos);
    } else {
      worldObjects.put(pos, type);
    }
    notifyChange();
  }

  /** Gets all world objects. */
  public Map<Position, WorldObjectType> getWorldObjects() {
    return new HashMap<>(worldObjects);
  }

  /** Checks if a position is within the map bounds. */
  public boolean isInBounds(int col, int row) {
    return col >= 0 && col < mapWidth && row >= 0 && row < mapHeight;
  }

  public void recordAction(EditorAction action) {
    actionHistory.record(action);
  }

  public void undo() {
    actionHistory.undo();
  }

  public void redo() {
    actionHistory.redo();
  }

  public int getMapWidth() {
    return mapWidth;
  }

  public int getMapHeight() {
    return mapHeight;
  }

  public TileSet getTileSet() {
    return tileSet;
  }

  public Tile getSelectedTile() {
    return selectedTile;
  }

  public void setSelectedTile(Tile tile) {
    this.selectedTile = tile;
  }

  public WorldObjectType getSelectedWorldObject() {
    return selectedWorldObject;
  }

  public void setSelectedWorldObject(WorldObjectType type) {
    this.selectedWorldObject = type;
  }

  public boolean isFillEnabled() {
    return fillEnabled;
  }

  public void setFillEnabled(boolean enabled) {
    this.fillEnabled = enabled;
  }

  public boolean isDrawing() {
    return isDrawing;
  }

  public void setDrawing(boolean drawing) {
    this.isDrawing = drawing;
  }

  public ViewPort getViewPort() {
    return viewPort;
  }

  public ActionHistory getActionHistory() {
    return actionHistory;
  }
}
