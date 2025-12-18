package io.tidalisland.states;

import io.tidalisland.collision.CollisionManager;
import io.tidalisland.debug.DebugRenderer;
import io.tidalisland.entities.Player;
import io.tidalisland.graphics.Camera;
import io.tidalisland.input.Action;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.spawning.SpawnManager;
import io.tidalisland.tide.TidalManager;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.ui.UiManager;
import io.tidalisland.worldobjects.InteractionManager;
import io.tidalisland.worldobjects.Raft;
import io.tidalisland.worldobjects.WorldObjectManager;
import java.awt.Graphics;

/**
 * Playing state.
 */
public class PlayingState implements GameState {

  private GameStateManager gsm;

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
   * Creates a new playing state.
   */
  public PlayingState(GameStateManager gsm, KeyHandler keys, MouseHandler mouse) {
    this.gsm = gsm;
    this.keys = keys;
    this.mouse = mouse;

    worldMap = new WorldMap();
    worldObjectManager = new WorldObjectManager(worldMap);
    collisionManager = new CollisionManager(worldMap, worldObjectManager);
    interactionManager = new InteractionManager(worldObjectManager, collisionManager);
    spawnManager = new SpawnManager(worldMap, worldObjectManager);

    player = new Player(keys, spawnManager.findValidSpawnPosition());

    tidalManager = new TidalManager(
        worldMap, worldObjectManager, collisionManager, worldMap.getTileSet(), player, 5,
        10);

    camera = new Camera();
    ui = new UiManager(gsm, keys, mouse, player.getInventory(), worldObjectManager, tidalManager,
        player);

    debugRenderer = new DebugRenderer(
        mouse, ui, worldObjectManager, collisionManager, camera, player);
  }

  @Override
  public void onEnter() {
  }

  @Override
  public void onExit() {
  }

  @Override
  public void update() {
    if (keys.isJustPressed(Action.PAUSE)) {
      gsm.push(new PauseState(gsm, keys, mouse));
    }

    if (player.isDead()) {
      gsm.push(new GameOverState(gsm, keys, mouse, false));
      return; // stop further updates
    }

    if (tidalManager.isFullyFlooded()) {
      if (collisionManager.isOnObject(player.getCollider(), Raft.TYPE)) {
        gsm.push(new GameOverState(gsm, keys, mouse, true)); // survived
      } else {
        gsm.push(new GameOverState(gsm, keys, mouse, false)); // drowned
      }
      return;
    }

    tidalManager.update();
    worldObjectManager.update();
    player.update(collisionManager, interactionManager);
    camera.update(player);

    ui.update();
  }

  @Override
  public void render(Graphics g) {
    worldMap.draw(g, camera);
    worldObjectManager.draw(g, camera);
    player.draw(g, camera);
    ui.render(g);
    debugRenderer.render(g);
  }
}
