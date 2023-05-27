package view;

import controller.GameController;
import model.Chessboard;
import model.PlayerColor;
import Exception.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class StartFrame extends JFrame {
	//    public final Dimension FRAME_SIZE ;
	private final int WIDTH;
	private final int HEIGTH;

	private final int ONE_CHESS_SIZE;
	private ChessboardComponent chessboardComponent;

	public static JLabel statusLabel = new JLabel();


	public StartFrame(int width, int height) {
		setTitle("2023 CS109 Project Demo"); //设置标题
		this.WIDTH = width;
		this.HEIGTH = height;
		this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

		setSize(WIDTH, HEIGTH);
		setLocationRelativeTo(null); // Center the window.
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
		setLayout(null);



		addLabel();
		addStartButton();
		addExitButton();
		addLoadButton();
	}
	private void addLabel() {
		statusLabel = new JLabel("Hello World!");
		statusLabel.setLocation(87, HEIGTH / 10);
		statusLabel.setSize(200, 60);
		statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(statusLabel);
	}

	private void addStartButton() {
		JButton button = new JButton("Strat");
		button.setLocation(50, HEIGTH / 10 + 120);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);
		button.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
				GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
				//1.把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
				ImageIcon bg=new ImageIcon("./resource/background.png");
				JLabel label=new JLabel(bg);
				label.setSize(1100,810);
				mainFrame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
				//2.把窗口面板设为内容面板并设为透明。
				JPanel pan=(JPanel)mainFrame.getContentPane();
				pan.setOpaque(false);
				mainFrame.setSize(1100,810);
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setVisible(true);
			});
			this.setVisible(false);
		});

	}
	private void addExitButton() {
		JButton button = new JButton("Exit");
		button.setLocation(50, HEIGTH / 10 + 240);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
//		button.setContentAreaFilled(false);
		add(button);
		button.addActionListener(e -> {
			System.exit(0);
		});

	}
	private void addLoadButton() {
		JButton button = new JButton("Load");
		button.setLocation(50, HEIGTH / 10 + 360);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);

		button.addActionListener(e -> {

			SwingUtilities.invokeLater(() -> {
				ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
				GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
				mainFrame.setVisible(true);
			});

			try {
				chessboardComponent.gameController.Load("./resource/2.txt");
				PlayerColor playerColor = chessboardComponent.gameController.getCurrentPlayer();
				ChessGameFrame.statusLabel.setText("Player: "+playerColor.toString()+"  Turn: "+
						chessboardComponent.gameController.getTurnIndex());
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			} catch (WrongChessException ex) {
				JOptionPane.showMessageDialog(this, "Save Broken!");
			} catch (NoFileThereException ex) {
				throw new RuntimeException(ex);
			} catch (WrongFormatException ex) {
				throw new RuntimeException(ex);
			} catch (WrongChessBoardSizeException ex) {
				throw new RuntimeException(ex);
			}
		});
//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
	}

}