import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.StartFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartFrame mainFrame = new StartFrame(300, 700);

            //1.把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
            ImageIcon bg=new ImageIcon("./resource/background.png");
            JLabel label=new JLabel(bg);
            label.setSize(300,700);
            mainFrame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
            //2.把窗口面板设为内容面板并设为透明、流动布局。
            JPanel pan=(JPanel)mainFrame.getContentPane();
            pan.setOpaque(false);
            mainFrame.setSize(300,700);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
        });
    }
}
