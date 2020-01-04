import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame{
    private JPanel panel1;
    private JButton TrackGame;
    private JButton AtomStructure;
    private JButton SocialNetworkCircle;
    private JPanel Panel;

    public GUI() {
        TrackGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("dnakdn");
//                dispose();
                GUI.super.setVisible(false);

                liao l = new liao();
                l.setVisible(true);
                //l.setVisible(true);
            }
        });
        AtomStructure.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    SocialNetworkCircle.addMouseListener(new MouseAdapter() { } );}

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
