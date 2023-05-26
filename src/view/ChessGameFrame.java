package view;

import model.PlayerColor;
import Exception.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private ChessboardComponent chessboardComponent;
    public static JLabel statusLabel = new JLabel();


    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addLabel();
        addLoadLastStepButton();
        addLoadNextStepButton();
        addRestartButton();
        addLoadButton();
        addSaveButton();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }


    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("Player: A"+"  Turn: "+ 0);
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }


    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addLoadLastStepButton() {
        JButton button = new JButton("LoadLastStep");
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            chessboardComponent.gameController.LoadLastStep();
            PlayerColor playerColor = chessboardComponent.gameController.getCurrentPlayer();
            ChessGameFrame.statusLabel.setText("Player: "+playerColor.toString()+"  Turn: "+
                    chessboardComponent.gameController.getTurnIndex());
        });
    }
    private void addLoadNextStepButton() {
        JButton button = new JButton("LoadNextStep");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            chessboardComponent.gameController.LoadNextStep();
            PlayerColor playerColor = chessboardComponent.gameController.getCurrentPlayer();
            ChessGameFrame.statusLabel.setText("Player: "+playerColor.toString()+"  Turn: "+
                    chessboardComponent.gameController.getTurnIndex());
        });
    }


    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
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

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            try {
                chessboardComponent.gameController.Save("./resource/2.txt");
            } catch (IOException ex) {
                throw new RuntimeException(ex);

            }
        });


//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
    }

    private void addRestartButton() {
        JButton button = new JButton("Restrat");
        button.setLocation(HEIGTH, HEIGTH / 10 + 600);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            chessboardComponent.gameController.ReStart();
            PlayerColor playerColor = chessboardComponent.gameController.getCurrentPlayer();
            ChessGameFrame.statusLabel.setText("Player: "+playerColor.toString()+"  Turn: "+ 0);
        });

    }

}
