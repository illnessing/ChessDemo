package view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class SaveFrame extends JFrame {
	//    public final Dimension FRAME_SIZE ;
	private final int WIDTH;
	private final int HEIGTH;

	private final int ONE_CHESS_SIZE;
	public static JLabel statusLabel = new JLabel();
	private final ChessboardComponent chessboardComponent;


	public SaveFrame(int width, int height, ChessboardComponent chessBoardComponent) {
		setTitle("Save Interface"); //设置标题
		this.WIDTH = width;
		this.HEIGTH = height;
		this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
		this.chessboardComponent = chessBoardComponent;

		setSize(WIDTH, HEIGTH);
		setLocationRelativeTo(null); // Center the window.
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
		setLayout(null);



		addLabel();
		addSaveButton1();
		addSaveButton2();
		addSaveButton3();
		addSaveButton4();
		addExitButton();

	}


	private void addLabel() {
		statusLabel = new JLabel("Save Here");
		statusLabel.setLocation(87, HEIGTH / 10);
		statusLabel.setSize(200, 60);
		statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(statusLabel);
	}
	private void addSaveButton1() {
		JButton button = new JButton("Slot 1");
		button.setLocation(50, HEIGTH / 10 + 120);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);
		button.addActionListener(e -> {
			try {
				chessboardComponent.gameController.Save("./resource/1.txt");
			} catch (IOException ex) {
				throw new RuntimeException(ex);

			}
			this.dispose();
		});


//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
	}
	private void addSaveButton2() {
		JButton button = new JButton("Slot 2");
		button.setLocation(50, HEIGTH / 10 + 240);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);
		button.addActionListener(e -> {
			try {
				chessboardComponent.gameController.Save("./resource/2.txt");
			} catch (IOException ex) {
				throw new RuntimeException(ex);

			}
			this.dispose();
		});


//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
	}
	private void addSaveButton3() {
		JButton button = new JButton("Slot 3");
		button.setLocation(50, HEIGTH / 10 + 360);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);
		button.addActionListener(e -> {
			try {
				chessboardComponent.gameController.Save("./resource/3.txt");
			} catch (IOException ex) {
				throw new RuntimeException(ex);

			}
			this.dispose();
		});


//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
	}
	private void addSaveButton4() {
		JButton button = new JButton("Slot 4");
		button.setLocation(50, HEIGTH / 10 + 480);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);
		button.addActionListener(e -> {
			try {
				chessboardComponent.gameController.Save("./resource/4.txt");
			} catch (IOException ex) {
				throw new RuntimeException(ex);

			}
			this.dispose();
		});


//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
	}
	private void addExitButton() {
		JButton button = new JButton("Exit");
		button.setLocation(50, HEIGTH / 10 + 600);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
//		button.setContentAreaFilled(false);
		add(button);
		button.addActionListener(e -> {
			this.dispose();
		});

	}



}