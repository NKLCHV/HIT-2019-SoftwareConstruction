import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class liao extends JFrame{
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
                    liao frame = new liao();
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
    public liao() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 612, 618);
        contentPane = new JPanel() {
            @Override
            public void paint(Graphics arg0) {
                // TODO Auto-generated method stub
                super.paint(arg0);
                int x=300,y=300,r=15;
                for(int i=0;i<5;i++) {
                    r+=30;
                    arg0.drawOval(x-r, y-r, 2*r, 2*r);
                    arg0.setColor(Color.MAGENTA);
                    arg0.fillOval(x-5, y-r-5, 2*5, 2*5);
                    arg0.setColor(Color.BLACK);
                }


            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }

}
