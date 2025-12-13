package io.tidalisland.ui.components;

import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * A component that displays text.
 */
public class UiLabel extends UiComponent {
  private String text;
  private boolean wrapText = true; // wrap text to fit width

  private List<String> lines = new ArrayList<>();

  /**
   * Creates a label.
   */
  public UiLabel(String text, int width, int height, int x, int y) {
    super(width, height, x, y);
    this.text = text;
    style = UiStyleDirector.makeTransparent();
  }

  public UiLabel(String text, int width, int height) {
    this(text, width, height, 0, 0);
  }

  @Override
  protected void onUpdate(KeyHandler keys, MouseHandler mouse) {
    // Does nothing
  }

  @Override
  protected void onRender(Graphics g) {
    int ax = getAbsX();
    int ay = getAbsY();

    g.setColor(new Color(0, 0, 0, 0));
    g.fillRect(ax, ay, width, height);

    // Text
    g.setFont(style.getFont());
    FontMetrics fm = g.getFontMetrics();
    lines.clear();

    if (wrapText) {
      wrapTextToLines(fm);
    } else {
      lines.add(text);
    }

    int totalHeight = lines.size() * fm.getHeight();
    int startY = ay + (height - totalHeight) / 2 + fm.getAscent();

    // Draw each line centered
    g.setColor(style.getTextColor());
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      int lineWidth = fm.stringWidth(line);
      int tx = ax + (width - lineWidth) / 2;
      int ty = startY + i * fm.getHeight();
      g.drawString(line, tx, ty);
    }
  }

  /**
   * Wraps text to lines.
   */
  private void wrapTextToLines(FontMetrics fm) {
    String[] words = text.split(" ");

    StringBuilder line = new StringBuilder(); // current line
    for (String word : words) {
      String testLine = line.length() == 0 ? word : line + " " + word;
      if (fm.stringWidth(testLine) > width - 2 * style.getPaddingX()) {
        if (line.length() > 0) {
          lines.add(line.toString());
        }
        line = new StringBuilder(word);
      } else {
        line = new StringBuilder(testLine);
      }
    }

    if (line.length() > 0) {
      lines.add(line.toString());
    }
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isWrapText() {
    return wrapText;
  }

  public void setWrapText(boolean wrapText) {
    this.wrapText = wrapText;
  }
}
