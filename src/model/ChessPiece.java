package model;


public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private PieceType type;
    public enum PieceType{
        None,
        Rat,
        Cat,
        Dog,
        Wolf,
        Leopard,
        Tiger,
        Lion,
        Elephant
    }
    private int rank;

    public ChessPiece(PlayerColor owner, PieceType type) {
        this.owner = owner;
        this.type = type;
        this.rank = (int) this.type.ordinal();
    }

    public boolean canCapture(ChessPiece target) {
        // TODO: Finish this method!
        return false;
    }

    public PieceType getType() {
        return type;
    }

    public PlayerColor getOwner() {
        return owner;
    }
}
