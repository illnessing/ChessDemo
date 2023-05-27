package view;

import model.PlayerColor;
import Exception.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class LoadFrame extends JFrame {
	//    public final Dimension FRAME_SIZE ;
	private final int WIDTH;
	private final int HEIGTH;

	private final int ONE_CHESS_SIZE;
	private final ChessboardComponent chessboardComponent;

	public static JLabel statusLabel = new JLabel();


	public LoadFrame(int width, int height, ChessboardComponent chessBoardComponent) {
		setTitle("Load Interface"); //设置标题
		this.WIDTH = width;
		this.HEIGTH = height;
		this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
		this.chessboardComponent = chessBoardComponent;

		setSize(WIDTH, HEIGTH);
		setLocationRelativeTo(null); // Center the window.
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
		setLayout(null);



		addLabel();
		addLoadButton1();
		addLoadButton2();
		addLoadButton3();
		addLoadButton4();
		addExitButton();

	}
	private void addLabel() {
		statusLabel = new JLabel("Load Here");
		statusLabel.setLocation(87, HEIGTH / 10);
		statusLabel.setSize(200, 60);
		statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(statusLabel);
	}
	private void addLoadButton1() {
		JButton button = new JButton("Slot 1");
		button.setLocation(50, HEIGTH / 10 + 120);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);
		button.addActionListener(e -> {
			try {
				chessboardComponent.gameController.Load("./resource/1.txt");
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
			this.dispose();
		});


//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
	}
	private void addLoadButton2() {
		JButton button = new JButton("Slot 2");
		button.setLocation(50, HEIGTH / 10 + 240);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);
		button.addActionListener(e -> {
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
				JOptionPane.showMessageDialog(this, "No File There!");
			} catch (WrongFormatException ex) {
				JOptionPane.showMessageDialog(this, "Wrong Format!!");
			} catch (WrongChessBoardSizeException ex) {
				JOptionPane.showMessageDialog(this, "Wrong Chess Board Size!");
			}
			this.dispose();
		});


//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
	}
	private void addLoadButton3() {
		JButton button = new JButton("Slot 3");
		button.setLocation(50, HEIGTH / 10 + 360);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);
		button.addActionListener(e -> {
			try {
				chessboardComponent.gameController.Load("./resource/3.txt");
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
			this.dispose();
		});


//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
	}
	private void addLoadButton4() {
		JButton button = new JButton("Slot 4");
		button.setLocation(50, HEIGTH / 10 + 480);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);
		button.addActionListener(e -> {
			try {
				chessboardComponent.gameController.Load("./resource/4.txt");
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