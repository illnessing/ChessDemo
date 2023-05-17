package model;


public class ChessPiece {
    // the owner of the chess
    private final PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private final PieceType type;
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
        // But there is a special case that eleplant cannot capture the rat while the rat can capture the eleplant
        if (this.getType() == PieceType.Rat && target.getType() == PieceType.Elephant) return true;
        if (this.getType() == PieceType.Elephant && target.getType() == PieceType.Rat) return false;
        return this.getType().ordinal() >= target.getType().ordinal();
    }

    public PieceType getType() {
        return type;
    }

    public PlayerColor getOwner() {
        return owner;
    }
}
