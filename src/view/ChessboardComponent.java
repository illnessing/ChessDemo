package view;


import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
	private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
	private final int CHESS_SIZE;
	private final Set<ChessboardPoint> riverCell = new HashSet<>();
	private final Set<ChessboardPoint> trapCell = new HashSet<>();
	private final Set<ChessboardPoint> nestCell = new HashSet<>();


	private GameController gameController;

	public ChessboardComponent(int chessSize) {
		CHESS_SIZE = chessSize;
		int width = CHESS_SIZE * 7;
		int height = CHESS_SIZE * 9;
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
		setLayout(null); // Use absolute layout.
		setSize(width, height);
		System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

		initiateGridComponents();
	}


	/**
	 * This method represents how to initiate ChessComponent
	 * according to Chessboard information
	 */
	public void initiateChessComponent(Chessboard chessboard) {
		Cell[][] grid = chessboard.getGrid();
		for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
			for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
				// TODO: Implement the initialization checkerboard

				if (grid[i][j].getPiece() != null) {
					ChessPiece chessPiece = grid[i][j].getPiece();
					System.out.println(chessPiece.getOwner());
					switch (chessPiece.getType()) {
						case Rat -> gridComponents[i][j].add(new RatChessComponent(chessPiece.getOwner(), CHESS_SIZE));
						case Cat -> gridComponents[i][j].add(new CatChessComponent(chessPiece.getOwner(), CHESS_SIZE));
						case Dog -> gridComponents[i][j].add(new DogChessComponent(chessPiece.getOwner(), CHESS_SIZE));
						case Wolf -> gridComponents[i][j].add(new WolfChessComponent(chessPiece.getOwner(), CHESS_SIZE));
						case Leopard -> gridComponents[i][j].add(new LeopardChessComponent(chessPiece.getOwner(), CHESS_SIZE));
						case Tiger -> gridComponents[i][j].add(new TigerChessComponent(chessPiece.getOwner(), CHESS_SIZE));
						case Lion -> gridComponents[i][j].add(new LionChessComponent(chessPiece.getOwner(), CHESS_SIZE));
						case Elephant -> gridComponents[i][j].add(new ElephantChessComponent(chessPiece.getOwner(), CHESS_SIZE));
					}
				}
			}
		}
	}

	public void initiateGridComponents() {


		riverCell.add(new ChessboardPoint(3, 1));
		riverCell.add(new ChessboardPoint(3, 2));
		riverCell.add(new ChessboardPoint(4, 1));
		riverCell.add(new ChessboardPoint(4, 2));
		riverCell.add(new ChessboardPoint(5, 1));
		riverCell.add(new ChessboardPoint(5, 2));

		riverCell.add(new ChessboardPoint(3, 4));
		riverCell.add(new ChessboardPoint(3, 5));
		riverCell.add(new ChessboardPoint(4, 4));
		riverCell.add(new ChessboardPoint(4, 5));
		riverCell.add(new ChessboardPoint(5, 4));
		riverCell.add(new ChessboardPoint(5, 5));

		trapCell.add(new ChessboardPoint(0, 2));
		trapCell.add(new ChessboardPoint(0, 4));
		trapCell.add(new ChessboardPoint(1, 3));
		nestCell.add(new ChessboardPoint(0, 3));

		trapCell.add(new ChessboardPoint(8, 2));
		trapCell.add(new ChessboardPoint(8, 4));
		trapCell.add(new ChessboardPoint(7, 3));
		nestCell.add(new ChessboardPoint(8, 3));


		for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
			for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
				ChessboardPoint temp = new ChessboardPoint(i, j);
				CellComponent cell;
				if (riverCell.contains(temp)) {
					cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
					this.add(cell);

				} else if (trapCell.contains(temp)) {
					cell = new CellComponent(Color.WHITE, calculatePoint(i, j), CHESS_SIZE);
					this.add(cell);
					//cell.paintComponent();
				} else if (nestCell.contains(temp)) {
					cell = new CellComponent(Color.YELLOW, calculatePoint(i, j), CHESS_SIZE);
					this.add(cell);
					//cell.paintComponent();
				} else {
					cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE);
					this.add(cell);
				}
				gridComponents[i][j] = cell;
			}
		}
	}

	public void registerController(GameController gameController) {
		this.gameController = gameController;
	}

	public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
		getGridComponentAt(point).add(chess);
	}

	public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
		// Note re-validation is required after remove / removeAll.
		ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
		getGridComponentAt(point).removeAll();
		getGridComponentAt(point).revalidate();
		chess.setSelected(false);
		return chess;
	}

	private CellComponent getGridComponentAt(ChessboardPoint point) {
		return gridComponents[point.getRow()][point.getCol()];
	}

	private ChessboardPoint getChessboardPoint(Point point) {
		System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
		return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
	}

	private ChessboardPoint onlyGetChessboardPoint(Point point) {

		return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
	}

	private Point calculatePoint(int row, int col) {
		return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void showWin(int x) {
		if (x == 1) {
			JOptionPane.showMessageDialog(this, "Player A Wins!");
		} else if (x == 2) {
			JOptionPane.showMessageDialog(this, "Player B Wins!");
		}
	}

	@Override
	protected void processMouseEvent(MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_PRESSED) {
			JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());

			if (clickedComponent.getComponentCount() == 0) {
				System.out.print("None chess here and ");
				gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
			}
            else {

//				for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
//					for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
//						ChessboardPoint temp = new ChessboardPoint(i, j);
//						CellComponent cell;
//						if (gameController.isValidCapture(onlyGetChessboardPoint(e.getPoint()), temp)) {
//							gridComponents[i][j].setBackground(Color.RED);
//							gridComponents[i][j].repaint();
//
//						}
//                        else if (gameController.isValidMove(onlyGetChessboardPoint(e.getPoint()), temp)) {
//							gridComponents[i][j].setBackground(Color.GREEN);
//                            gridComponents[i][j].repaint();
//
//						}
//					}
//				}

				System.out.print("One chess here and ");

				gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);

			}
			showWin(gameController.checkWin());
		}
	}
}
