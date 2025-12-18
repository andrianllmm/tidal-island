package io.tidalisland.states;

import io.tidalisland.config.Config;
import io.tidalisland.engine.GameClock;
import io.tidalisland.graphics.Camera;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.ui.components.UiButton;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.layout.VerticalStackLayout;
import io.tidalisland.ui.styles.UiStyleDirector;
import io.tidalisland.worldobjects.WorldObjectManager;
import java.awt.Color;
import java.awt.Graphics;

/**
 * The title screen.
 */
public class TitleState implements GameState {

  private final GameStateManager gsm;
  private final KeyHandler keys;
  private final MouseHandler mouse;

  private final WorldMap worldMap;
  private final WorldObjectManager worldObjectManager;
  private final Camera camera;
  private int panX = 1;
  private int panY = 1;

  private UiPanel ui;
  private UiButton startButton;
  private UiButton quitButton;

  /**
   * Initializes the title screen.
   */
  public TitleState(GameStateManager gsm, KeyHandler keys, MouseHandler mouse) {
    this.gsm = gsm;
    this.keys = keys;
    this.mouse = mouse;
    this.worldMap = new WorldMap();
    this.worldObjectManager = new WorldObjectManager(worldMap);
    this.camera = new Camera();
    this.camera.setSpeed(2);

    // Background panel
    ui = new UiPanel(Config.screenWidth(), Config.screenHeight(), 0, 0);
    ui.setStyle(UiStyleDirector.fromTransparent().padding(24).build());
    ui.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

    // Title label
    UiLabel titleLabel = new UiLabel("TIDAL ISLAND", 600, 180);
    titleLabel.setStyle(UiStyleDirector.makeTransparent());
    titleLabel.style(s -> s.fontSize(64));
    ui.add(titleLabel);

    UiPanel actionsPanel = new UiPanel(240, 160);
    actionsPanel.setStyle(UiStyleDirector.fromTransparent().padding(4).build());
    actionsPanel.setLayout(new VerticalStackLayout(16));
    actionsPanel.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    ui.add(actionsPanel);

    startButton = new UiButton("START", 180, 40);
    startButton.getLabel().style(s -> s.fontSize(24));

    startButton.setOnClick(() -> {
      gsm.set(new PlayingState(gsm, keys, mouse)); // start game
    });

    actionsPanel.add(startButton);

    quitButton = new UiButton("QUIT", 180, 40);
    quitButton.getLabel().style(s -> s.fontSize(24));

    quitButton.setOnClick(() -> {
      System.exit(0);
    });

    actionsPanel.add(quitButton);
  }

  @Override
  public void onEnter() {
    GameClock.getInstance().reset();
    GameClock.getInstance().setPaused(true);
  }

  @Override
  public void onExit() {
    GameClock.getInstance().reset();
    GameClock.getInstance().setPaused(false);
  }

  @Override
  public void update() {
    ui.update(keys, mouse);

    // Automatic panning
    camera.update(panX, panY);

    int x = camera.getPosition().getX();
    int y = camera.getPosition().getY();

    int maxX = Config.mapWidth() * Config.tileSize() - Config.screenWidth();
    int maxY = Config.mapHeight() * Config.tileSize() - Config.screenHeight();

    if (x <= 0 || x >= maxX) {
      panX = -panX;
    }

    if (y <= 0 || y >= maxY) {
      panY = -panY;
    }
  }

  @Override
  public void render(Graphics g) {
    worldMap.draw(g, camera);
    worldObjectManager.draw(g, camera);
    ui.render(g);
  }
}
