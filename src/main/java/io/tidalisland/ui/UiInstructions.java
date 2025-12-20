package io.tidalisland.ui;

import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.layout.VerticalStackLayout;
import io.tidalisland.ui.styles.UiStyleDirector;

/**
 * Instructions for how to play the game.
 */
public class UiInstructions extends UiPanel {

  /**
   * Initializes the instructions panel.
   */
  public UiInstructions(int width, int height) {
    super(width, height);
    setStyle(UiStyleDirector.makeTransparent());
    style(s -> s.padding(16));
    setLayout(new VerticalStackLayout(16));
    getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

    UiLabel title = new UiLabel("How to Play", width, 24);
    title.style(s -> s.fontSize(32));
    add(title);

    // Gameplay

    UiPanel gameplay = new UiPanel(width, 150);
    gameplay.style(s -> s.padding(8));
    gameplay.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    add(gameplay);

    UiLabel gameplayText = new UiLabel(
        "You are stranded on a deserted island and must gather resources and craft items to escape. You must progress toward building a raft to escape before the tides get too high and submerge the island!",
        width, 24);
    gameplay.add(gameplayText);

    // Controls

    UiPanel controls = new UiPanel(width, 250);
    controls.style(s -> s.padding(8));
    controls.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    add(controls);

    UiLabel movement = new UiLabel("Use WASD to move", width, 24);
    UiLabel interact = new UiLabel("Press E to interact with objects", width, 24);
    UiLabel inventory = new UiLabel("Press I to open inventory", width, 24);
    UiLabel craft = new UiLabel("Press C to open crafting bench", width, 24);
    UiLabel pause = new UiLabel("Press ESC to pause the game", width, 24);
    UiLabel fullScreen = new UiLabel("Press F to toggle fullscreen", width, 24);

    controls.add(movement);
    controls.add(interact);
    controls.add(inventory);
    controls.add(craft);
    controls.add(pause);
    controls.add(fullScreen);
  }
}
