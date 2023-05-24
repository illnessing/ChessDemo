package view;


import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class WolfChessComponent extends ChessComponent {
	public WolfChessComponent(PlayerColor owner, int size) {
		super(owner, size);
		setSize(size, size);
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
		g2.drawString("狼", getWidth() / 4, getHeight() * 5 / 8); // FIXME: Use library to find the correct offset.
		if (isSelected()) { // Highlights the model if selected.
			g.setColor(Color.RED);
			g.drawOval(2, 2, getWidth()-4, getHeight()-4);
		}
	}
}
