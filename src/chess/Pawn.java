package chess;

import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece{
	

	public Pawn(String c, Square s, String img_file) {
		super(c, s, img_file);
	}
	
	
	@Override
	public List<Square> getLegalMoves(Board b) {
		LinkedList<Square> legalMoves = new LinkedList<Square>();
		Square[][] board = b.getSquareArray();
		
		
		if(this.getColor().equals("WHITE")) { //If White
			if(board[this.getCurrentSquare().getYNum()-1][this.getCurrentSquare().getXNum()].getOccupyingPiece()==null) { //If no piece one square in front		
				legalMoves.add(board[this.getCurrentSquare().getYNum()-1][this.getCurrentSquare().getXNum()]);	//add to legal moves
				if(this.getCurrentSquare().getYNum() == 6) { 													//If first turn
					if(board[this.getCurrentSquare().getYNum()-2][this.getCurrentSquare().getXNum()].getOccupyingPiece()==null) {		 //If no piece 2 squares in front			
					legalMoves.add(board[this.getCurrentSquare().getYNum()-2][this.getCurrentSquare().getXNum()]); // add to legal moves
					}
				}
			}

			
			if(this.getCurrentSquare().getXNum()+1 <= 7) {//Check right bounds
				if(board[this.getCurrentSquare().getYNum()-1][this.getCurrentSquare().getXNum()+1].getOccupyingPiece() != null) { //if there is a piece there
					if(board[this.getCurrentSquare().getYNum()-1][this.getCurrentSquare().getXNum()+1].getOccupyingPiece().getColor().equals("BLACK")) { //if its black
						legalMoves.add(board[this.getCurrentSquare().getYNum()-1][this.getCurrentSquare().getXNum()+1]); //add to legal moves
					}				
				}
			}
			
			if(this.getCurrentSquare().getXNum()-1 >= 0) {	//Check left bounds		
				if(board[this.getCurrentSquare().getYNum()-1][this.getCurrentSquare().getXNum()-1].getOccupyingPiece() != null) {
					if(board[this.getCurrentSquare().getYNum()-1][this.getCurrentSquare().getXNum()-1].getOccupyingPiece().getColor().equals("BLACK")) {
						legalMoves.add(board[this.getCurrentSquare().getYNum()-1][this.getCurrentSquare().getXNum()-1]);
					}
				}
			}
		}
		
		
		else { // If black (Same as white)
			if(board[this.getCurrentSquare().getYNum()+1][this.getCurrentSquare().getXNum()].getOccupyingPiece()==null) {				
				legalMoves.add(board[this.getCurrentSquare().getYNum()+1][this.getCurrentSquare().getXNum()]);	
				if(this.getCurrentSquare().getYNum() == 1) {					
					if(board[this.getCurrentSquare().getYNum()+2][this.getCurrentSquare().getXNum()].getOccupyingPiece()==null) {
						legalMoves.add(board[this.getCurrentSquare().getYNum()+2][this.getCurrentSquare().getXNum()]);
					}
				}	
			}

			
			if(this.getCurrentSquare().getXNum()+1 <= 7) {//Check right bounds
				if(board[this.getCurrentSquare().getYNum()+1][this.getCurrentSquare().getXNum()+1].getOccupyingPiece() != null) { //if there is a piece there
					if(board[this.getCurrentSquare().getYNum()+1][this.getCurrentSquare().getXNum()+1].getOccupyingPiece().getColor().equals("WHITE")) { //if its black
						legalMoves.add(board[this.getCurrentSquare().getYNum()+1][this.getCurrentSquare().getXNum()+1]);
					}				
				}
			}
			
			if(this.getCurrentSquare().getXNum()-1 >= 0) {	//Check left bounds		
				if(board[this.getCurrentSquare().getYNum()+1][this.getCurrentSquare().getXNum()-1].getOccupyingPiece() != null) {
					if(board[this.getCurrentSquare().getYNum()+1][this.getCurrentSquare().getXNum()-1].getOccupyingPiece().getColor().equals("WHITE")) {
						legalMoves.add(board[this.getCurrentSquare().getYNum()+1][this.getCurrentSquare().getXNum()-1]);
					}
				}
			}
		}

		return legalMoves;
	}


	
}
