package model;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private final Cell[][] grid;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, ChessPiece.PieceType.Lion));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, ChessPiece.PieceType.Rat));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, ChessPiece.PieceType.Dog));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, ChessPiece.PieceType.Leopard));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, ChessPiece.PieceType.Wolf));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, ChessPiece.PieceType.Cat));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, ChessPiece.PieceType.Tiger));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, ChessPiece.PieceType.Elephant));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, ChessPiece.PieceType.Elephant));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, ChessPiece.PieceType.Tiger));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, ChessPiece.PieceType.Cat));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, ChessPiece.PieceType.Wolf));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, ChessPiece.PieceType.Leopard));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, ChessPiece.PieceType.Dog));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, ChessPiece.PieceType.Rat));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, ChessPiece.PieceType.Lion));
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        System.out.println(src + "capture" + dest);
        removeChessPiece(dest);
        setChessPiece(dest, removeChessPiece(src));
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        return calculateDistance(src, dest) == 1;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        // TODO:Fix this method
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) == null) {
            return false;
        }
        return calculateDistance(src, dest) == 1 && getChessPieceAt(src).canCapture(getChessPieceAt(dest));
    }
}
