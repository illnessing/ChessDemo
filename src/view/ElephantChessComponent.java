package view;


import model.PlayerColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ElephantChessComponent extends ChessComponent {

    public ElephantChessComponent(PlayerColor owner, int size) {
        super(owner, size);
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        g2.setFont(font);
        g2.setColor(owner.getColor());
//        g2.drawString("象", getWidth() / 4, getHeight() * 5 / 8); // FIXME: Use library to find the correct offset.

        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        //画图片
        if(owner.getColor()==Color.blue) {
            Image image = null;
            try {
                image = ImageIO.read(new File("./resource/Elephant-blue.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
        else{
            Image image = null;
            try {
                image = ImageIO.read(new File("./resource/Elephant-red.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
