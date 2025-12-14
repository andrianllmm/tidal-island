package io.tidalisland.worldbuilder.ui;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

/**
 * A custom toggle button for the world builder.
 */
public class CustomToggleButton extends JToggleButton {

  public CustomToggleButton(String text) {
    super(text);
    init();
  }

  public CustomToggleButton(ImageIcon icon) {
    super(icon);
    init();
  }

  private void init() {
    setBackground(Color.BLACK);
    setForeground(Color.WHITE);
    setFocusPainted(false);
    setBorder(new LineBorder(Color.DARK_GRAY));
    setSelected(false);
    addChangeListener(e -> setBorder(
        isSelected() ? new LineBorder(Color.YELLOW, 2) : new LineBorder(Color.DARK_GRAY)));
  }
}
