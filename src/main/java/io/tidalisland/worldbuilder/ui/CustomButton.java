package io.tidalisland.worldbuilder.ui;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 * A custom button for the world builder.
 */
public class CustomButton extends JButton {
  /**
   * Creates a new custom button.
   */
  public CustomButton(String text, Runnable action) {
    super(text);
    setBackground(Color.BLACK);
    setForeground(Color.WHITE);
    setFocusPainted(false);
    setBorder(new LineBorder(Color.DARK_GRAY));
    addActionListener(e -> action.run());
  }
}
