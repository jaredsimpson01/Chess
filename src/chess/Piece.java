package chess;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public abstract class Piece {
	
	private final String color;
	private Square currentSquare;
	private BufferedImage img;
	private boolean selected;
	
	
	public Piece(String c, Square s, String img_file) {
		this.color = c;
		this.currentSquare = s;	
		this.selected = false;
		
		try {
			if(this.img == null) {
				this.img = ImageIO.read(new File(img_file));
			}
		}
		catch (IOException e) {
			System.out.println("File not found: " + e.getMessage());
		}
	}
	
	
	
	public void move(Square oldSquare, Square newSquare) {
		this.currentSquare = newSquare;
		newSquare.setOccupyingPiece(this);
		oldSquare.setOccupyingPiece(null);
		
	}
	
	public void eat(Square oldSquare, Square newSquare) {
		oldSquare.getOccupyingPiece().setCurrentSquare(null);
		this.currentSquare = newSquare;
		newSquare.setOccupyingPiece(this);
		oldSquare.setOccupyingPiece(null);
	}
	
	public List<Square> getDiagonalMoves(Board b) {
		LinkedList<Square> legalMoves = new LinkedList<Square>();
		Square[][] board = b.getSquareArray();
		
		int x = this.getCurrentSquare().getXNum();
		int y = this.getCurrentSquare().getYNum();

		
		//Up right
		while(x + 1 <= 7 && y - 1 >= 0) {
			x += 1;
			y -= 1;
			if(locatePiece(b, x, y) == null) {
				legalMoves.add(board[y][x]);				
			}
			else if(locatePiece(b, x, y).getColor()==this.getColor()) {
				break;
			}
			else {
				legalMoves.add(board[y][x]);
				break;
			}
		}
		
		x = this.getCurrentSquare().getXNum();
		y = this.getCurrentSquare().getYNum();
		
		//Up left
		while(x - 1 >= 0 && y - 1 >= 0) {
			x -= 1;
			y -= 1;
			if(locatePiece(b, x, y) == null) {
				legalMoves.add(board[y][x]);				
			}
			else if(locatePiece(b, x, y).getColor()==this.getColor()) {
				break;
			}
			else {
				legalMoves.add(board[y][x]);
				break;
			}
		}
		
		x = this.getCurrentSquare().getXNum();
		y = this.getCurrentSquare().getYNum();
		
		//Down right
		while(x + 1 <= 7 && y + 1 <= 7) {
			x += 1;
			y += 1;
			if(locatePiece(b, x, y) == null) {
				legalMoves.add(board[y][x]);				
			}
			else if(locatePiece(b, x, y).getColor()==this.getColor()) {
				break;
			}
			else {
				legalMoves.add(board[y][x]);
				break;
			}
		}
		
		x = this.getCurrentSquare().getXNum();
		y = this.getCurrentSquare().getYNum();
		
		//Down left
		while(x - 1 >= 0 && y + 1 <= 7) {
			x -= 1;
			y += 1;
			if(locatePiece(b, x, y) == null) {
				legalMoves.add(board[y][x]);				
			}
			else if(locatePiece(b, x, y).getColor()==this.getColor()) {
				break;
			}
			else {
				legalMoves.add(board[y][x]);
				break;
			}
		}
		return legalMoves;
		
	}
	
	public List<Square> getLinearMoves(Board b) {
		LinkedList<Square> legalMoves = new LinkedList<Square>();
		Square[][] board = b.getSquareArray();
		
		int x = this.getCurrentSquare().getXNum();
		int y = this.getCurrentSquare().getYNum();
		
		//right
		while(x + 1 <= 7) {
			x += 1;
			if(locatePiece(b, x, y) == null) {
				legalMoves.add(board[y][x]);				
			}
			else if(locatePiece(b, x, y).getColor()==this.getColor()) {
				break;
			}
			else {
				legalMoves.add(board[y][x]);
				break;
			}
		}
		
		x = this.getCurrentSquare().getXNum();
		y = this.getCurrentSquare().getYNum();
		
		//left
		while(x - 1 >= 0) {
			x -= 1;
			if(locatePiece(b, x, y) == null) {
				legalMoves.add(board[y][x]);				
			}
			else if(locatePiece(b, x, y).getColor()==this.getColor()) {
				break;
			}
			else {
				legalMoves.add(board[y][x]);
				break;
			}
		}
		
		x = this.getCurrentSquare().getXNum();
		y = this.getCurrentSquare().getYNum();
		
		//Down
		while(y + 1 <= 7) {
			y += 1;
			if(locatePiece(b, x, y) == null) {
				legalMoves.add(board[y][x]);				
			}
			else if(locatePiece(b, x, y).getColor()==this.getColor()) {
				break;
			}
			else {
				legalMoves.add(board[y][x]);
				break;
			}
		}
		
		x = this.getCurrentSquare().getXNum();
		y = this.getCurrentSquare().getYNum();
		
		//up
		while(y - 1 >= 0) {
			y -= 1;
			if(locatePiece(b, x, y) == null) {
				legalMoves.add(board[y][x]);				
			}
			else if(locatePiece(b, x, y).getColor()==this.getColor()) {
				break;
			}
			else {
				legalMoves.add(board[y][x]);
				break;
			}

		}
		return legalMoves;
	}
	
	
	public Piece locatePiece(Board b, int x, int y) {
		//return board[y][x].getOccupyingPiece();
		return b.getSquareArray()[y][x].getOccupyingPiece();
	}
	
	public Square getCurrentSquare() {
		return this.currentSquare;
	}
	
	public void setCurrentSquare(Square s) {
		this.currentSquare = s;
	}
	
	
	public BufferedImage getImage() {
		return this.img;
	}

	public void setPosition(Square square) {
		this.currentSquare = square;
	}


	public BufferedImage getImg() {
		return img;
	}


	public void setImg(BufferedImage img) {
		this.img = img;
	}


	public String getColor() {
		return color;
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public abstract List<Square> getLegalMoves(Board b);
	
}
