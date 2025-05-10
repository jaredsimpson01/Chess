package chess;

import java.util.LinkedList;
import java.util.List;

public class King extends Piece {
	private Square move;

	public King(String c, Square s, String img_file) {
		super(c, s, img_file);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Square> getLegalMoves(Board b) {
		LinkedList<Square> legalMoves = new LinkedList<Square>();
		Square[][] board = b.getSquareArray();

		int x = this.getCurrentSquare().getXNum();
		int y = this.getCurrentSquare().getYNum();

		if (y - 1 >= 0) {
			move = board[y - 1][x];
			checkMove(move, legalMoves);
		}
		if (y - 1 >= 0 && x + 1 <= 7) {
			move = board[y - 1][x + 1];
			checkMove(move, legalMoves);
		}
		if (y - 1 >= 0 && x - 1 >= 0) {
			move = board[y - 1][x - 1];
			checkMove(move, legalMoves);
		}
		if (y + 1 <= 7) {
			move = board[y + 1][x];
			checkMove(move, legalMoves);
		}
		if (y + 1 <= 7 && x + 1 <= 7) {
			move = board[y + 1][x + 1];
			checkMove(move, legalMoves);
		}
		if (y + 1 <= 7 && x - 1 >= 0) {
			move = board[y + 1][x - 1];
			checkMove(move, legalMoves);
		}
		if (x + 1 <= 7) {
			move = board[y][x + 1];
			checkMove(move, legalMoves);
		}
		if (x - 1 >= 0) {
			move = board[y][x - 1];
			checkMove(move, legalMoves);
		}

		return legalMoves;
	}

	public LinkedList<Square> checkMove(Square m, LinkedList<Square> legalMoves) {
		if (m.getOccupyingPiece() == null) {
			legalMoves.add(m);
		} else if (!m.getOccupyingPiece().getColor().equals(this.getColor())) {
			legalMoves.add(m);
		}
		return legalMoves;
	}

}
