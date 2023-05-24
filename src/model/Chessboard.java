package model;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
        initSpecialCell();
    }

    public void initialize(){
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
        initSpecialCell();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {
        // initChess
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

    private void initSpecialCell() {
        // Rivers
        grid[3][1].setCellType(CellType.River);
        grid[3][2].setCellType(CellType.River);
        grid[4][1].setCellType(CellType.River);
        grid[4][2].setCellType(CellType.River);
        grid[5][1].setCellType(CellType.River);
        grid[5][2].setCellType(CellType.River);

        grid[3][4].setCellType(CellType.River);
        grid[3][5].setCellType(CellType.River);
        grid[4][4].setCellType(CellType.River);
        grid[4][5].setCellType(CellType.River);
        grid[5][4].setCellType(CellType.River);
        grid[5][5].setCellType(CellType.River);

        // Dens
        grid[0][3].setCellType(CellType.RedDen);
        grid[8][3].setCellType(CellType.BlueDen);

        // Traps
        grid[0][2].setCellType(CellType.Trap);
        grid[0][4].setCellType(CellType.Trap);
        grid[1][3].setCellType(CellType.Trap);

        grid[8][2].setCellType(CellType.Trap);
        grid[8][4].setCellType(CellType.Trap);
        grid[7][3].setCellType(CellType.Trap);
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
    public Cell[][] getClonedGrid() {
        Cell[][] result = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                result[i][j] = grid[i][j].clone();
            }
        }
        return result;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        // check dest and src
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }

        //Tiger, Lion JUMP
        if (
                calculateDistance(src, dest) != 1 && (
                getChessPieceAt(src).getType() == ChessPiece.PieceType.Lion ||
                getChessPieceAt(src).getType() == ChessPiece.PieceType.Tiger)
        ) {
            // not enter river
            if (getGridAt(dest).getCellType() == CellType.River) return false;
            // check path
            if (src.getRow() == dest.getRow()){
                for (int j = Math.min(src.getCol(), dest.getCol()) + 1; j < Math.max(src.getCol(), dest.getCol()); j++){
                    if (getGrid()[src.getRow()][j].getCellType() != CellType.River) return false;
                    if (getGrid()[src.getRow()][j].getPiece() != null) return false;
                }
                return true;
            }
            if (src.getCol() == dest.getCol()){
                for (int i = Math.min(src.getRow(), dest.getRow()) + 1; i < Math.max(src.getRow(), dest.getRow()); i++){
                    if (getGrid()[i][src.getCol()].getCellType() != CellType.River) return false;
                    if (getGrid()[i][src.getCol()].getPiece() != null) return false;
                }
                return true;
            }
        }

        // check distance
        if (calculateDistance(src, dest) != 1) return false;

        //check only rat enter river
        if(getGridAt(dest).getCellType() == CellType.River) return getChessPieceAt(src).getType() == ChessPiece.PieceType.Rat;
        // check if you are trying to enter a den
        if (getGridAt(dest).getCellType() == CellType.BlueDen) {
            return getChessPieceOwner(src) != PlayerColor.BLUE;
        }
        if (getGridAt(dest).getCellType() == CellType.RedDen) {
            return getChessPieceOwner(src) != PlayerColor.RED;
        }
        return calculateDistance(src, dest) == 1;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {

        // capture need there is two chessPiece
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) == null) {
            return false;
        }

        // you cannot eat yourself
        if (calculateDistance(src, dest) == 0) return false;

        //Tiger, Lion JUMP Capture
        if (
                calculateDistance(src, dest) != 1 && (
                        getChessPieceAt(src).getType() == ChessPiece.PieceType.Lion ||
                                getChessPieceAt(src).getType() == ChessPiece.PieceType.Tiger)
        ) {
            // not enter river
            if (getGridAt(dest).getCellType() == CellType.River) return false;
            // check path
            if (src.getRow() == dest.getRow()){
                for (int j = Math.min(src.getCol(), dest.getCol()) + 1; j < Math.max(src.getCol(), dest.getCol()); j++){
                    if (getGrid()[src.getRow()][j].getCellType() != CellType.River) return false;
                    if (getGrid()[src.getRow()][j].getPiece() != null) return false;
                }
                return getChessPieceAt(src).canCapture(getChessPieceAt(dest));
            }
            if (src.getCol() == dest.getCol()){
                for (int i = Math.min(src.getRow(), dest.getRow()) + 1; i < Math.max(src.getRow(), dest.getRow()); i++){
                    if (getGrid()[i][src.getCol()].getCellType() != CellType.River) return false;
                    if (getGrid()[i][src.getCol()].getPiece() != null) return false;
                }
                return getChessPieceAt(src).canCapture(getChessPieceAt(dest));
            }
        }

        // distance
        if (calculateDistance(src, dest) != 1) return false;

        // cannot eat teammate
        if(getChessPieceOwner(src) == getChessPieceOwner(dest)) return false;
        // cannot eat other if you stay in river
        if (getGridAt(src).getCellType() == CellType.River && getGridAt(dest).getCellType() == CellType.Land) return false;
        // only rat can enter river
        if (getGridAt(dest).getCellType() == CellType.River && getChessPieceAt(src).getType() != ChessPiece.PieceType.Rat) return false;
        // everyone can capture the animal in trap
        if (getGridAt(dest).getCellType() == CellType.Trap) return true;
        // distant equal 1 and can Capture
        return calculateDistance(src, dest) == 1 && getChessPieceAt(src).canCapture(getChessPieceAt(dest));
    }
}
