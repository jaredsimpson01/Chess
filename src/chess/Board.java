package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Board extends JPanel implements MouseListener {

	private Square[][] board;
	private String turn = "WHITE";
	private Square selectedSquare;
	private JLabel turnLabel;

	public static final String BBISHOP = "resources/bbishop.png";
	public static final String BKING = "resources/bking.png";
	public static final String BKNIGHT = "resources/bknight.png";
	public static final String BPAWN = "resources/bpawn.png";
	public static final String BQUEEN = "resources/bqueen.png";
	public static final String BROOK = "resources/brook.png";
	public static final String WBISHOP = "resources/wbishop.png";
	public static final String WKING = "resources/wking.png";
	public static final String WKNIGHT = "resources/wknight.png";
	public static final String WPAWN = "resources/wpawn.png";
	public static final String WQUEEN = "resources/wqueen.png";
	public static final String WROOK = "resources/wrook.png";

	public Board() {
		this.setPreferredSize(new Dimension(560, 560));
		this.addMouseListener(this);
		board = new Square[8][8];

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				String color = (row + col) % 2 == 0 ? "WHITE" : "BLACK";
				board[row][col] = new Square(col, row, color);
			}
		}

		initializeGame();
	}

	private void initializeGame() {
		for (int i = 0; i < 8; i++) {
			board[6][i].put(new Pawn("WHITE", board[6][i], WPAWN));
			board[1][i].put(new Pawn("BLACK", board[2][i], BPAWN));
		}

		board[0][0].put(new Rook("BLACK", board[0][0], BROOK));
		board[0][1].put(new Knight("BLACK", board[0][1], BKNIGHT));
		board[0][2].put(new Bishop("BLACK", board[0][2], BBISHOP));
		board[0][3].put(new Queen("BLACK", board[0][3], BQUEEN));
		board[0][4].put(new King("BLACK", board[0][4], BKING));
		board[0][5].put(new Bishop("BLACK", board[0][5], BBISHOP));
		board[0][6].put(new Knight("BLACK", board[0][6], BKNIGHT));
		board[0][7].put(new Rook("BLACK", board[0][7], BROOK));

		board[7][0].put(new Rook("WHITE", board[7][0], WROOK));
		board[7][1].put(new Knight("WHITE", board[7][1], WKNIGHT));
		board[7][2].put(new Bishop("WHITE", board[7][2], WBISHOP));
		board[7][3].put(new Queen("WHITE", board[7][3], WQUEEN));
		board[7][4].put(new King("WHITE", board[7][4], WKING));
		board[7][5].put(new Bishop("WHITE", board[7][5], WBISHOP));
		board[7][6].put(new Knight("WHITE", board[7][6], WKNIGHT));
		board[7][7].put(new Rook("WHITE", board[7][7], WROOK));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int squareSize = getWidth() / 8;

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Square sq = board[row][col];
				g.setColor(sq.getColor().equals("WHITE") ? new Color(255, 248, 220) : new Color(160, 82, 45));
				g.fillRect(col * squareSize, row * squareSize, squareSize, squareSize);

				if (sq.getOccupyingPiece() != null) {
					g.drawImage(sq.getOccupyingPiece().getImage(), col * squareSize, row * squareSize, squareSize,
							squareSize, null);
				}
			}
		}

		// Draw legal moves
		if (selectedSquare != null && selectedSquare.getOccupyingPiece() != null) {
			List<Square> legalMoves = selectedSquare.getOccupyingPiece().getLegalMoves(this);
			g.setColor(new Color(255, 0, 0, 128));
			for (Square move : legalMoves) {
				int mx = move.getXNum() * squareSize;
				int my = move.getYNum() * squareSize;
				g.fillRect(mx, my, squareSize, squareSize);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int squareSize = getWidth() / 8;
		int col = e.getX() / squareSize;
		int row = e.getY() / squareSize;

		if (row < 0 || row > 7 || col < 0 || col > 7)
			return;

		Square clicked = board[row][col];
		Piece clickedPiece = clicked.getOccupyingPiece();

		if (selectedSquare == null) {
			if (clickedPiece != null && clickedPiece.getColor().equals(turn)) {
				selectedSquare = clicked;
				clickedPiece.setSelected(true);
				repaint();
			}
			return;
		}

		Piece selectedPiece = selectedSquare.getOccupyingPiece();

		if (clicked == selectedSquare) {
			selectedPiece.setSelected(false);
			selectedSquare = null;
			repaint();
			return;
		}

		if (clickedPiece != null && clickedPiece.getColor().equals(turn)) {
			selectedPiece.setSelected(false);
			selectedSquare = clicked;
			clickedPiece.setSelected(true);
			repaint();
			return;
		}

		List<Square> legalMoves = selectedPiece.getLegalMoves(this);
		if (legalMoves.contains(clicked)) {
			// Simulate the move
			Square from = selectedSquare;
			Square to = clicked;
			Piece captured = to.getOccupyingPiece();

			from.setOccupyingPiece(null);
			to.setOccupyingPiece(selectedPiece);
			selectedPiece.setCurrentSquare(to);

			boolean leavesKingInCheck = isInCheck(turn);

			// Undo the move
			from.setOccupyingPiece(selectedPiece);
			to.setOccupyingPiece(captured);
			selectedPiece.setCurrentSquare(from);

			if (!leavesKingInCheck) {
				// Do the real move
				if (clicked.getOccupyingPiece() == null) {
					selectedPiece.move(selectedSquare, clicked);
				} else {
					selectedPiece.eat(selectedSquare, clicked);
				}

				if (selectedPiece instanceof Pawn) {
					int promoteRow = selectedPiece.getColor().equals("WHITE") ? 0 : 7;
					if (clicked.getYNum() == promoteRow) {
						clicked.setOccupyingPiece(new Queen(selectedPiece.getColor(), clicked,
								selectedPiece.getColor().equals("WHITE") ? WQUEEN : BQUEEN));
					}
				}

				changeTurn();
				if (isCheckmate(turn)) {
					repaint();
					JOptionPane.showMessageDialog(this,
							"Checkmate! " + (turn.equals("WHITE") ? "Black" : "White") + " wins.");
					resetGame();
					return;
				}
			} else {
				// Invalid move – leaves king in check
				JOptionPane.showMessageDialog(this, "You can't leave your king in check!");
			}
		}

		selectedPiece.setSelected(false);
		selectedSquare = null;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private void deselect() {
		if (selectedSquare != null && selectedSquare.getOccupyingPiece() != null) {
			selectedSquare.getOccupyingPiece().setSelected(false);
		}
		selectedSquare = null;
	}

	private void changeTurn() {
		turn = turn.equals("WHITE") ? "BLACK" : "WHITE";
		if (turnLabel != null) {
			turnLabel.setText("Turn: " + turn);
		}
	}

	public Square[][] getSquareArray() {
		return board;
	}

	public void setTurnLabel(JLabel label) {
		this.turnLabel = label;
		if (label != null) {
			label.setText("Turn: " + turn);
		}
	}

	public void resetGame() {
		selectedSquare = null;
		turn = "WHITE";

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j].setOccupyingPiece(null);
			}
		}

		initializeGame();
		if (turnLabel != null) {
			turnLabel.setText("Turn: " + turn);
		}
		repaint();
	}

	// ADD THIS TO Board.java

	public boolean isInCheck(String color) {
		Square kingSquare = null;

		// Find the king
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece p = board[row][col].getOccupyingPiece();
				if (p instanceof King && p.getColor().equals(color)) {
					kingSquare = board[row][col];
					break;
				}
			}
		}

		if (kingSquare == null)
			return false;

		// See if any opponent piece can attack it
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece enemy = board[row][col].getOccupyingPiece();
				if (enemy != null && !enemy.getColor().equals(color)) {
					List<Square> attacks = enemy.getLegalMoves(this);
					if (attacks.contains(kingSquare)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean isCheckmate(String color) {
		if (!isInCheck(color))
			return false;

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece p = board[row][col].getOccupyingPiece();
				if (p != null && p.getColor().equals(color)) {
					List<Square> legalMoves = p.getLegalMoves(this);
					for (Square target : legalMoves) {
						Square original = board[row][col];
						Piece captured = target.getOccupyingPiece();

						// Simulate move
						original.setOccupyingPiece(null);
						target.setOccupyingPiece(p);
						p.setCurrentSquare(target);

						boolean stillInCheck = isInCheck(color);

						// Undo move
						original.setOccupyingPiece(p);
						target.setOccupyingPiece(captured);
						p.setCurrentSquare(original);

						if (!stillInCheck)
							return false;
					}
				}
			}
		}

		return true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
