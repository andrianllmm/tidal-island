package io.tidalisland.engine;

import io.tidalisland.collision.CollisionManager;
import io.tidalisland.config.Config;
import io.tidalisland.debug.DebugRenderer;
import io.tidalisland.entities.Player;
import io.tidalisland.graphics.Camera;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.spawning.SpawnManager;
import io.tidalisland.tide.TidalManager;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.ui.UiManager;
import io.tidalisland.worldobjects.InteractionManager;
import io.tidalisland.worldobjects.WorldObjectManager;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The game panel.
 */
public class GameCanvas extends Canvas {
  // Input handlers
  private KeyHandler keys;
  private MouseHandler mouse;

  private WorldMap worldMap;
  private WorldObjectManager worldObjectManager;
  private CollisionManager collisionManager;
  private InteractionManager interactionManager;
  private SpawnManager spawnManager;
  private Player player;
  private TidalManager tidalManager;
  private Camera camera;
  private UiManager ui;

  private DebugRenderer debugRenderer;

  /**
   * Sets up the panel.
   */
  public GameCanvas() {
    setPreferredSize(new Dimension(Config.screenWidth(), Config.screenHeight()));
    setBackground(Color.BLACK);

    // Initialize input handlers
    keys = new KeyHandler();
    mouse = new MouseHandler();
    addMouseListener(mouse);
    addMouseMotionListener(mouse);
    addMouseWheelListener(mouse);

    addKeyListener(keys);
    setFocusable(true);
    requestFocus();

    // Initialize members
    worldMap = new WorldMap();
    worldObjectManager = new WorldObjectManager(worldMap);
    collisionManager = new CollisionManager(worldMap, worldObjectManager);
    interactionManager = new InteractionManager(worldObjectManager);
    spawnManager = new SpawnManager(worldMap, worldObjectManager);
    player = new Player(keys, spawnManager.findValidSpawnPosition());
    tidalManager =
        new TidalManager(worldMap, worldObjectManager, worldMap.getTileSet(), player, 5, 10);

    camera = new Camera();
    ui = new UiManager(keys, mouse, player.getInventory(), worldObjectManager, tidalManager,
        player);

    debugRenderer =
        new DebugRenderer(mouse, ui, worldObjectManager, collisionManager, camera, player);
  }

  /**
   * Updates the game.
   */
  public void update() {
    if (!tidalManager.isFullyFlooded()) {
      tidalManager.update();
      worldObjectManager.update();
      player.update(collisionManager, interactionManager);
      camera.update(player);
    }
    ui.update();
  }

  /**
   * Renders the game.
   */
  protected void render(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    try {
      worldMap.draw(g2, camera);
      worldObjectManager.draw(g2, camera);
      player.draw(g2, camera);
      ui.render(g2);

      debugRenderer.render(g2);
    } finally {
      g2.dispose();
    }
  }

  /**
   * Called after each frame.
   */
  public void endFrame() {
    keys.endFrame();
    mouse.endFrame();
  }
}
