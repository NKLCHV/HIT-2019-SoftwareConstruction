package application;

import ADT.Ladder;
import ADT.Monkey;
import ADT.Rung;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui extends JFrame {

  static final int WIDTH = 800;
  static final int HEIGHT = 600;

  public Gui() {
    this.Show();
    this.setSize(WIDTH, HEIGHT);
    this.setTitle("猴子过河");
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  private void Show() {
    GuiPanel panel = new GuiPanel();
    Thread t = new Thread(panel);
    t.start();
    this.add(panel);
  }
}

class GuiPanel extends JPanel implements Runnable {

  List<Monkey> monkeyList = Simulator.monkeys;
  List<Ladder> ladderList = Simulator.ladders;

  @Override
  public void paint(Graphics g) {

    super.paint(g);

    // 画梯子
    int yPerLadder = 100;
    int xPerPedal = 30;
    int h = ladderList.get(0).rungList.size();
    for (int i = 1; i <= ladderList.size(); i++) {
      g.drawLine(80, (yPerLadder * i - 25), 80 + 30 * h, (yPerLadder * i - 25));
      g.drawLine(80, (yPerLadder * i + 25), 80 + 30 * h, (yPerLadder * i + 25));

      for (int k = 0; k <= h; k++) {
        g.drawLine(80 + xPerPedal * k, (yPerLadder * i - 25), 80 + xPerPedal * k,
            (yPerLadder * i + 25));
      }
    }

    // 模拟猴子的移动
    for (int j = 0; j < ladderList.size(); j++) {
      Ladder l = ladderList.get(j);
      for (int i = 0; i < l.rungList.size(); i++) {
        Rung rung = l.rungList.get(i);
        Monkey monkey = rung.getMonkey();
        if (monkey != null) {
          if ((monkey.getDirection().equals("L->R"))) {
            // 模拟向右行进的猴子
            String text = "L";

            BufferedImage img1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img1.createGraphics();
            Font font = new Font("Arial", Font.PLAIN, 48);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int width = fm.stringWidth(text);
            int height = fm.getHeight();
            g2d.dispose();

            img1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = img1.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
            g2d.setFont(font);
            fm = g2d.getFontMetrics();
            g2d.setColor(Color.RED);
            g2d.drawString(text, 0, fm.getAscent());
            g2d.dispose();

            g.drawImage(img1, 80 + i * xPerPedal, yPerLadder * (l.getID() + 1) - 25, 30, 60,
                this);
            g.drawString(String.valueOf(monkey.getID()), 85 + i * xPerPedal,
                yPerLadder * (l.getID() + 1) - 30);
          } else {
            // 模拟向左行进的猴子
            String text = "R";

            BufferedImage img2 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img2.createGraphics();
            Font font = new Font("Arial", Font.PLAIN, 48);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int width = fm.stringWidth(text);
            int height = fm.getHeight();
            g2d.dispose();

            img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = img2.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
            g2d.setFont(font);
            fm = g2d.getFontMetrics();
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, 0, fm.getAscent());
            g2d.dispose();
            g.drawImage(img2, 80 + 30 * h - xPerPedal * i - 30,
                yPerLadder * (l.getID() + 1) - 25, 30, 60, this);
            g.drawString(String.valueOf(monkey.getID()),
                80 + 30 * h - xPerPedal * i - 20,
                yPerLadder * (l.getID() + 1) - 30);
          }
        }
      }
    }
    g.drawString("左岸:", 10, 110);
    g.drawString("右岸:", 130 + 30 * h - 25, 110);
    int x = 0;
    int y = 0;
    for (Monkey m : monkeyList) {
      if (m.getStatus() == 10) {
        if (m.getDirection().equals("R->L")) {
          g.drawString(String.valueOf(m.getID()), 15, y + 140);
          y += 20;
        } else {
          g.drawString(String.valueOf(m.getID()), 135 + 30 * h - 25, x + 140);
          x += 20;
        }
      }
    }
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (Thread.activeCount() == 2) {
        Thread.yield();
      }
      repaint();
    }
  }
}
