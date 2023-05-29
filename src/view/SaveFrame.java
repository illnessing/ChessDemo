package view;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;


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
	JTextField textField = new JTextField(); // 创建一个单行输入框


	public SaveFrame(int width, int height, ChessboardComponent chessBoardComponent) {
		setTitle("Save Interface"); //设置标题
		this.WIDTH = width;
		this.HEIGTH = height;
		this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
		this.chessboardComponent = chessBoardComponent;

		setSize(WIDTH, HEIGTH);
		setLocationRelativeTo(null); // Center the window.
		setDefaultCloseOperation(2); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
		setLayout(null);




		addCreateNewSave();
		addExitButton();
		addOverride();
	}

	public void addCreateNewSave() {
		//		创建按钮
		JButton button = new JButton("Create a new save");
		button.setLocation(50, 10 );
		button.setSize(300, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);
		button.addActionListener(e -> {
			onButtonOk();
		});
//		提示的文字
		statusLabel = new JLabel("Input a name");
		statusLabel.setLocation(87,10+120);
		statusLabel.setSize(200, 60);
		statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(statusLabel);
//		创建输入窗口
		textField.setLocation(50, 10 + 240);
		textField.setSize(200, 60);
		textField.setEditable(true); // 设置输入框允许编辑
		textField.setColumns(11); // 设置输入框的长度为11个字符
		add(textField); // 在面板上添加单行输入框

	}
	private void onButtonOk(){
		String name = textField.getText();
//		创建文件
		String filePath = "./resource/"+name+".txt";
		File file = new File(filePath);
		try {
			file.createNewFile();
			System.out.println("文件创建成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			chessboardComponent.gameController.Save("./resource/"+name+".txt");
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		this.dispose();
	}
	private void addOverride() {
		JButton button = new JButton("Override a existing save");
		button.setLocation(50, 10 + 360);
		button.setSize(300, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
		add(button);
		button.addActionListener(e -> {
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
					chessboardComponent.gameController.Save(path);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
				System.out.println("path: "+path);
			}
			this.dispose();
		});
	}

	private void addExitButton() {
		JButton button = new JButton("Back");
		button.setLocation(50,  10 + 480);
		button.setSize(200, 60);
		button.setFont(new Font("Rockwell", Font.BOLD, 20));
//		button.setContentAreaFilled(false);
		add(button);
		button.addActionListener(e -> {
			this.dispose();
		});

	}
}