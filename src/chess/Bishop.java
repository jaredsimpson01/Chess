package chess;

import java.util.LinkedList;
import java.util.List;

public class Bishop extends Piece{

	public Bishop(String c, Square s, String img_file) {
		super(c, s, img_file);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Square> getLegalMoves(Board b) {
		LinkedList<Square> legalMoves = new LinkedList<Square>();
		
		legalMoves = (LinkedList<Square>) this.getDiagonalMoves(b);
		return legalMoves;
		
	}
}
