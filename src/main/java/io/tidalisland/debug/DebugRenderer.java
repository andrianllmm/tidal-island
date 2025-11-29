package io.tidalisland.debug;

import static io.tidalisland.config.Config.DEBUG;
import static io.tidalisland.config.Config.FPS;

import io.tidalisland.collision.CollisionManager;
import io.tidalisland.entities.Player;
import io.tidalisland.graphics.Camera;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.ui.UiManager;
import io.tidalisland.ui.components.UiComponent;
import io.tidalisland.ui.styles.UiStyle;
import io.tidalisland.worldobjects.WorldObjectManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Renders debug information for development.
 */
public class DebugRenderer {
  private boolean enabled = DEBUG;

  private MouseHandler mouse;
  private UiManager ui;
  private WorldObjectManager worldObjectManager;
  private CollisionManager collisionManager;
  private Camera camera;
  private Player player;

  /**
   * Creates a new debug renderer.
   */
  public DebugRenderer(MouseHandler mouse, UiManager ui, WorldObjectManager wom,
      CollisionManager cm, Camera camera, Player player) {
    this.mouse = mouse;
    this.ui = ui;
    this.worldObjectManager = wom;
    this.collisionManager = cm;
    this.camera = camera;
    this.player = player;
  }

  /**
   * Renders debug information to the screen.
   */
  public void render(Graphics2D g) {
    if (!enabled) {
      return;
    }

    g.setColor(new Color(255, 0, 255, 128));
    g.setFont(new Font("Dialog", Font.BOLD, 14));
    int padding = 40;
    int spacing = 20;
    int x = padding;
    int y = padding;

    // FPS
    g.drawString("FPS: " + FPS, x, y);
    y += spacing;

    // Mouse info
    g.drawString("Mouse X: " + mouse.getX(), x, y);
    y += spacing;
    g.drawString("Mouse Y: " + mouse.getY(), x, y);

    y += spacing;
    // Player info
    g.drawString("Player X: " + player.getPosition().getX(), x, y);
    y += spacing;
    g.drawString("Player Y: " + player.getPosition().getY(), x, y);
    y += spacing;

    // Camera info
    g.drawString("Camera X: " + camera.getPosition().getX(), x, y);
    y += spacing;
    g.drawString("Camera Y: " + camera.getPosition().getY(), x, y);
    y += spacing;

    // World object info
    g.drawString("World Objects: " + worldObjectManager.size(), x, y);
    y += spacing;

    // Collision info
    g.drawString("Collisions: " + collisionManager.getCollisionCount(), x, y);
    y += spacing;

    // Colliders
    renderColliderDebug(g);

    // UI components
    renderUiDebug(g, ui.getRoot());
  }

  private void renderColliderDebug(Graphics2D g) {
    worldObjectManager.getAll().forEach(obj -> obj.getCollider().draw(g, camera));
    player.getCollider().draw(g, camera);
  }

  private void renderUiDebug(Graphics2D g, UiComponent component) {
    if (!component.isVisible()) {
      return;
    }

    // Get component properties
    int absX = component.getAbsX();
    int absY = component.getAbsY();
    int width = component.getWidth();
    int height = component.getHeight();
    UiStyle style = component.getStyle();

    // Draw component bounds
    g.setColor(new Color(255, 0, 0, 128));
    g.drawRect(absX, absY, width, height);

    // Draw padding bounds
    if (style.getPaddingX() > 0 || style.getPaddingY() > 0) {
      g.setColor(new Color(255, 120, 0, 128));
      g.drawRect(absX + style.getPaddingX(), absY + style.getPaddingY(),
          width - 2 * style.getPaddingX(), height - 2 * style.getPaddingY());
    }

    // Draw debug info
    g.setColor(new Color(255, 0, 255, 128));
    g.setFont(new Font("Dialog", Font.PLAIN, 8));
    String info = String.format("[%d,%d %dx%d]", absX, absY, width, height);
    g.drawString(info, absX, absY - 2);

    // Recursively render children
    for (UiComponent child : component.getChildren()) {
      renderUiDebug(g, child);
    }
  }

  public void toggle() {
    enabled = !enabled;
  }
}
