package view;
import controller.GameController;
import model.Chessboard;
import model.ChessboardPoint;
import model.PlayerColor;
import Exception.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class StartFrame extends JFrame {
	//    public final Dimension FRAME_SIZE ;
	private final int WIDTH;
	private final int HEIGTH;
	private final Set<ChessboardPoint> riverCell = new HashSet<>();
	private final Set<ChessboardPoint> trapCell = new HashSet<>();
	private final Set<ChessboardPoint> blueDenCell = new HashSet<>();
	private final Set<ChessboardPoint> redDenCell = new HashSet<>();
	private final int ONE_CHESS_SIZE;
	private ChessboardComponent chessboardComponent;
	public static JLabel statusLabel = new JLabel();
	private static Thread playThread;



	public StartFrame(int width, int height) {

		setTitle("Initial Interface"); //设置标题
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
		Play();


	}

	public ChessboardComponent getChessboardComponent() {
		return chessboardComponent;
	}

	public void setChessboardComponent(ChessboardComponent chessboardComponent) {
		this.chessboardComponent = chessboardComponent;
	}

	private void addLabel() {

		statusLabel = new JLabel("Jungle");
		statusLabel.setLocation(60, HEIGTH / 10);
		statusLabel.setSize(200, 60);
		statusLabel.setFont(new Font("Rockwell", Font.BOLD, 50));
		statusLabel.setForeground(Color.ORANGE);
		add(statusLabel);

	}

	private void addStartButton() {



		JButton button = new JButton("New Game");
		button.setLocation(50, HEIGTH / 10 + 120);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 30));
		add(button);

		button.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {

				over();
				PlayButton();

				ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
				GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
				chessboardComponent = mainFrame.getChessboardComponent();


				//1.把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
				ImageIcon bg = new ImageIcon("./resource/image/2.png");
				JLabel label = new JLabel(bg);
				label.setSize(1100, 810);
				mainFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
				//2.把窗口面板设为内容面板并设为透明。

				JPanel pan = (JPanel) mainFrame.getContentPane();
				pan.setOpaque(false);
				mainFrame.setSize(1100, 810);
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setVisible(true);

//				chessboardComponent.paintChessboard();

			});
			this.dispose();
		});
	}

	//打开读档界面
	private void addLoadButton() {
		JButton button = new JButton("Continue");
		button.setLocation(50, HEIGTH / 10 + 240);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 30));
		add(button);

		button.addActionListener(e -> {

			over();
			PlayButton();

			SwingUtilities.invokeLater(() -> {
				ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
				GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
				chessboardComponent = mainFrame.getChessboardComponent();

				//1.把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
				ImageIcon bg = new ImageIcon("./resource/image/2.png");
				JLabel label = new JLabel(bg);
				label.setSize(1100, 810);
				mainFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

				//2.把窗口面板设为内容面板并设为透明。
				JPanel pan = (JPanel) mainFrame.getContentPane();
				pan.setOpaque(false);
				mainFrame.setSize(1100, 810);
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setVisible(true);
			});

			FileSystemView fsv = FileSystemView.getFileSystemView();
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(fsv.createFileObject("./resource/saves"));
			fileChooser.setDialogTitle("请选择要上传的文件...");
			fileChooser.setApproveButtonText("确定");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			int result = fileChooser.showOpenDialog(null);

			if (JFileChooser.APPROVE_OPTION == result) {
				String path = fileChooser.getSelectedFile().getPath();
				try {
					chessboardComponent.gameController.Load(path);
					PlayerColor playerColor = chessboardComponent.gameController.getCurrentPlayer();
					ChessGameFrame.statusLabel.setText("Player: " + playerColor.toString() + "  Turn: " +
							chessboardComponent.gameController.getTurnIndex());
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				} catch (WrongChessException ex) {
					JOptionPane.showMessageDialog(this, "Error 103: Save Broken!");
				} catch (NoFileThereException ex) {
					JOptionPane.showMessageDialog(this, "No File There!");
				} catch (WrongFormatException ex) {
					JOptionPane.showMessageDialog(this, "Error 101: Wrong Format!");
				} catch (WrongChessBoardSizeException ex) {
					JOptionPane.showMessageDialog(this, "Error 102: Wrong Chess Board Size!");
				} catch (WrongPlayerException ex) {
					JOptionPane.showMessageDialog(this, "Error 104: Wrong Player!");
				}
			}

			this.dispose();

		});

	}

	private void addExitButton() {

		JButton button = new JButton("Exit");
		button.setLocation(50, HEIGTH / 10 + 360);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 30));

//		button.setContentAreaFilled(false);
		add(button);

		button.addActionListener(e -> {
			PlayButton();
			System.exit(0);
			PlayButton();
		});

	}
	private static void PlayAudio(String audio) {
		playThread = new Thread(new PlayRunnable(audio));
		playThread.start();
	}

	public void Play() {
		String audio = "./resource/music/menu1.mp3";
		PlayAudio(audio);
	}
	public void over() {
		playThread.stop();
	}

	public void PlayButton() {
		String audio = "./resource/music/open.mp3";
		PlayAudio(audio);
	}

}
