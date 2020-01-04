import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Game extends JFrame {

  private JPanel panel1;
  private JPanel panel;

  private JPanel contentPane;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Game frame = new Game();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public Game() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 612, 618);
    contentPane = new JPanel() {
      @Override
      public void paint(Graphics arg0) {
        // TODO Auto-generated method stub
        super.paint(arg0);
        int x = 300;
        int y = 300;
        int r = 15;
        for (int i = 0; i < 5; i++) {
          r += 30;
          arg0.drawOval(x - r, y - r, 2 * r, 2 * r);
          arg0.setColor(Color.MAGENTA);
          arg0.fillOval(x - 5, y - r - 5, 2 * 5, 2 * 5);
          arg0.setColor(Color.BLACK);

        }


      }
    };
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);
  }

}
