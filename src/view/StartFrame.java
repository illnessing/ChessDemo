package view;

import controller.GameController;
import model.Chessboard;
import model.ChessboardPoint;
import model.PlayerColor;
import Exception.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

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
	}
	public ChessboardComponent getChessboardComponent() {
		return chessboardComponent;
	}

	public void setChessboardComponent(ChessboardComponent chessboardComponent) {
		this.chessboardComponent = chessboardComponent;
	}
	private void addLabel() {

		statusLabel = new JLabel("Hello World!");
		statusLabel.setLocation(87, HEIGTH / 10);
		statusLabel.setSize(200, 60);
		statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(statusLabel);

	}

	private void addStartButton() {

		JButton button = new JButton("New Game");
		button.setLocation(50, HEIGTH / 10 + 120);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);

		button.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {

				ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
				GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
				chessboardComponent = mainFrame.getChessboardComponent();

				//1.把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
				ImageIcon bg=new ImageIcon("./resource/image/3.png");
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

//				CellComponent[][] gridComponents = chessboardComponent.getGridComponents();
//
//				for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
//					for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
//						ChessboardPoint temp = new ChessboardPoint(i, j);
//						if (riverCell.contains(temp)) {
//
//						} else if (trapCell.contains(temp)) {
//
//						} else if (blueDenCell.contains(temp) || redDenCell.contains(temp)) {
//
//						} else {
//
//					Graphics graphics = gridComponents[i][j].getGraphics();
//					Image image = null;
//					try {
//						image = ImageIO.read(new File("./resource/image/BackGround.png"));
//					} catch (IOException h) {
//						throw new RuntimeException(h);
//					}
//					graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
//
//						}
//					}
//				}



			});
			this.dispose();
		});
	}
	//打开读档界面
	private void addLoadButton() {
		JButton button = new JButton("Load");
		button.setLocation(50, HEIGTH / 10 + 240);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);

		button.addActionListener(e -> {

			SwingUtilities.invokeLater(() -> {
				ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
				GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
				chessboardComponent = mainFrame.getChessboardComponent();

				//1.把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
				ImageIcon bg=new ImageIcon("./resource/image/3.png");
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

			FileSystemView fsv = FileSystemView.getFileSystemView();
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(fsv.createFileObject("./resource"));
			fileChooser.setDialogTitle("请选择要上传的文件...");
			fileChooser.setApproveButtonText("确定");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			int result = fileChooser.showOpenDialog(null);

			if (JFileChooser.APPROVE_OPTION == result) {
				String path=fileChooser.getSelectedFile().getPath();
				try {
					chessboardComponent.gameController.Load(path);
					PlayerColor playerColor = chessboardComponent.gameController.getCurrentPlayer();
					ChessGameFrame.statusLabel.setText("Player: "+playerColor.toString()+"  Turn: "+
							chessboardComponent.gameController.getTurnIndex());
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				} catch (WrongChessException ex) {
					JOptionPane.showMessageDialog(this, "Save Broken!");
				} catch (NoFileThereException ex) {
					JOptionPane.showMessageDialog(this, "No File There!");
				} catch (WrongFormatException ex) {
					JOptionPane.showMessageDialog(this, "Wrong Format!!");
				} catch (WrongChessBoardSizeException ex) {
					JOptionPane.showMessageDialog(this, "Wrong Chess Board Size!");
				}
			}

			this.dispose();

		});

	}

	private void addExitButton() {

		JButton button = new JButton("Exit");
		button.setLocation(50, HEIGTH / 10 + 360);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));

//		button.setContentAreaFilled(false);
		add(button);

		button.addActionListener(e -> {
			System.exit(0);
		});

	}


}
