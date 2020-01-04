import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {

  private JPanel panel1;
  private JButton trackGame;
  private JButton atomStructure;
  private JButton socialNetworkCircle;
  private JPanel panel;

  /**
   * .
   */
  public GUI() {
    trackGame.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        System.out.println("dnakdn");
        GUI.super.setVisible(false);

        Game l = new Game();
        l.setVisible(true);
        //l.setVisible(true);
      }
    });
    atomStructure.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
      }
    });
    socialNetworkCircle.addMouseListener(new MouseAdapter() {
    });
  }

  /**
   *  .
   * @param args .
   */
  public static void main(String[] args) {
    JFrame frame = new JFrame("GUI");
    frame.setContentPane(new GUI().panel1);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
