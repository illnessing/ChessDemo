package model;

import java.io.Serializable;
/**
 * This class describe the slot for Chess in Chessboard
 * */
public class Cell implements Serializable {
    // the position for chess
    private ChessPiece piece;

    private CellType cellType = CellType.Floor; // We will update it later

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }

    public void setCellType(CellType cellType){
        this.cellType = cellType;
    }

    public CellType getCellType(){
        return cellType;
    }
}

