package chess;

public class Square {
    private int xNum;  // column
    private int yNum;  // row
    private String color;
    private Piece occupyingPiece;

    public Square(int x, int y, String c) {
        this.xNum = x;
        this.yNum = y;
        this.color = c;
    }

    public void put(Piece p) {
        this.occupyingPiece = p;
        p.setCurrentSquare(this);
    }

    public int getXNum() { return xNum; }
    public int getYNum() { return yNum; }

    public Piece getOccupyingPiece() { return occupyingPiece; }
    public void setOccupyingPiece(Piece p) { occupyingPiece = p; }

    public String getColor() { return color; }
}
