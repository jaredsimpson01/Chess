package chess;

import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece{

	public Queen(String c, Square s, String img_file) {
		super(c, s, img_file);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Square> getLegalMoves(Board b) {
		LinkedList<Square> legalMoves = new LinkedList<Square>();
		Square[][] board = b.getSquareArray();

		legalMoves.addAll(this.getDiagonalMoves(b));
		legalMoves.addAll(this.getLinearMoves(b));
		return legalMoves;
	}

}
