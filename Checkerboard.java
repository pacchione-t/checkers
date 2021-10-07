package checkers;

import java.util.ArrayList;

// Represents the state of the board for a game of checkers
public class Checkerboard {
	// The states of the individual squares on the board
	final public static int EMPTY = 0;
	final public static int WHITE_PIECE = 1;
	final public static int BLACK_PIECE = 2;
	final public static int WHITE_KING = 3;
	final public static int BLACK_KING = 4;
	
	// The states of the individual squares on the board
	private ArrayList<Integer> states;
	// The numbers of white and black pieces remaining on the board
	private int whitePieces;
	private int blackPieces;
	// The winner of the game, if the game has ended
	private boolean winner;
	
	// Creates a new checkers board
	public Checkerboard() {
		this.states = new ArrayList<Integer>();
		this.makeBoard();
		this.whitePieces = 12;
		this.blackPieces = 12;
	}
	
	// Sets each square on the board to the values
	// corresponding to the beginning of a game of checkers
	private void makeBoard() {
		this.states.add(BLACK_PIECE);
		this.states.add(BLACK_PIECE);
		this.states.add(BLACK_PIECE);
		this.states.add(BLACK_PIECE);
		this.states.add(BLACK_PIECE);
		this.states.add(BLACK_PIECE);
		this.states.add(BLACK_PIECE);
		this.states.add(BLACK_PIECE);
		this.states.add(BLACK_PIECE);
		this.states.add(BLACK_PIECE);
		this.states.add(BLACK_PIECE);
		this.states.add(BLACK_PIECE);
		this.states.add(EMPTY);
		this.states.add(EMPTY);
		this.states.add(EMPTY);
		this.states.add(EMPTY);
		this.states.add(EMPTY);
		this.states.add(EMPTY);
		this.states.add(EMPTY);
		this.states.add(EMPTY);
		this.states.add(WHITE_PIECE);
		this.states.add(WHITE_PIECE);
		this.states.add(WHITE_PIECE);
		this.states.add(WHITE_PIECE);
		this.states.add(WHITE_PIECE);
		this.states.add(WHITE_PIECE);
		this.states.add(WHITE_PIECE);
		this.states.add(WHITE_PIECE);
		this.states.add(WHITE_PIECE);
		this.states.add(WHITE_PIECE);
		this.states.add(WHITE_PIECE);
		this.states.add(WHITE_PIECE);
	}
	
	// Returns the state of a given square on the checkers board
	public int getState(int square) {
		return this.states.get(square);
	}
	
	// Sets the state of the given square to the given value
	private void setState(int square, int state) {
		this.states.set(square, state);
	}
	
	// Returns the number of white pieces left on the board
	public int whitePieces() {
		return this.whitePieces;
	}
	
	// Returns the number of black pieces left on the board
	public int blackPieces() {
		return this.blackPieces;
	}
	
	// Completes a move if it is legal and returns whether a legal move was completed
	public boolean move(int from, int to) {
		int fromState = this.getState(from);
		int toState = this.getState(to);
		
		if (toState == 0 && ((from < to && fromState != WHITE_PIECE)
				|| (from > to && fromState != BLACK_PIECE))
				&& ((Math.abs(from - to) == 4)
						|| ((from / 4 % 2 == 0 && from % 8 != 3 && from % 8 != 4)
								&& (to == from - 3 || to == from + 5))
						|| ((from / 4 % 2 == 1 && from % 8 != 3 && from % 8 != 4)
								&& (to == from - 5 || to == from + 3)))) {
			this.setState(to, this.getState(from));
			this.setState(from, 0);
			
			if ((fromState == 2 && to / 4 == 7) || (fromState == 1 && to / 4 == 0)) {
				this.king(to);
			}
			
			return true;
		}
		
		return false;
	}
	
	// Completes a jump if it is legal and returns whether a legal jump was completed
	public boolean jump(int from, int to) {
		int mid = from / 4 % 2 == 1 ? Math.abs(from + to) / 2
				: Math.abs(from + to) / 2 + 1;
		
		int fromState = this.getState(from);
		int midState = this.getState(mid);
		int toState = this.getState(to);
		
		if (toState == 0
				&& ((from < to && fromState != 1) || (from > to && fromState != 2))
				&& (((to == from + 9 || to == from - 7) && from % 4 != 3)
						|| ((to == from + 7 || to == from - 9) && from % 4 != 0))
				&& (((fromState == BLACK_PIECE || fromState == BLACK_KING)
						&& (midState == WHITE_PIECE || midState == WHITE_KING))
						|| ((fromState == WHITE_PIECE || fromState == WHITE_KING)
								&& (midState == BLACK_PIECE || midState == BLACK_KING)))) {
			this.setState(to, this.getState(from));
			this.setState(from, 0);
			this.setState(mid, 0);
			
			if (midState == WHITE_PIECE || midState == WHITE_KING) {
				this.whitePieces--;
			}
			else {
				this.blackPieces--;
			}
			
			if ((fromState == 2 && to / 4 == 7) || (fromState == 1 && to / 4 == 0)) {
				this.king(to);
			}
			
			return true;
		}
		
		return false;
	}
	
	// "Kings" the checkers piece at the given square on the board
	private void king(int square) {
		this.setState(square, this.getState(square) + 2);
	}
	
	// Determines whether the piece at the given square has a legal move
	public boolean hasMove(int square) {
		int state = this.getState(square);
		
		if (state == EMPTY) {
			return false;
		}
		
		int endState;
		
		if (square % 8 != 3 && square / 4 < 7) {
			endState = this.getState((square + 5) - (square / 4 % 2));
			
			if (state != WHITE_PIECE && endState == EMPTY) {
				return true;
			}
		}
		if (square % 8 != 4 && square / 4 < 7) {
			endState = this.getState((square + 4) - (square / 4 % 2));
			
			if (state != WHITE_PIECE && endState == EMPTY) {
				return true;
			}
		}
		if (square % 8 != 3 && square / 4 > 0) {
			endState = this.getState((square - 3) - (square / 4 % 2));
			
			if (state != BLACK_PIECE && endState == EMPTY) {
				return true;
			}
		}
		if (square % 8 != 4 && square / 4 > 0) {
			endState = this.getState((square - 4) - (square / 4 % 2));
			
			if (state != BLACK_PIECE && endState == EMPTY) {
				return true;
			}
		}
		
		return false;
	}
	
	// Determines whether the piece at the given square has a legal jump
	public boolean hasJump(int square) {	
		int state = this.getState(square);
		
		if (state == EMPTY) {
			return false;
		}
		
		int midState;
		int endState;
		
		if (square % 4 < 3 && square / 4 < 6) {
			midState = this.getState((square + 5) - (square / 4 % 2));
			endState = this.getState(square + 9);
			
			if ((((state == BLACK_PIECE || state == BLACK_KING)
					&& (midState == WHITE_PIECE || midState == WHITE_KING))
					|| (state == WHITE_KING
					&& (midState == BLACK_PIECE || midState == BLACK_KING)))
					&& endState == EMPTY) {
				return true;
			}
		}
		if (square % 4 > 0 && square / 4 < 6) {
			midState = this.getState((square + 4) - (square / 4 % 2));
			endState = this.getState(square + 7);
			
			if ((((state == BLACK_PIECE || state == BLACK_KING)
					&& (midState == WHITE_PIECE || midState == WHITE_KING))
					|| (state == WHITE_KING
					&& (midState == BLACK_PIECE || midState == BLACK_KING)))
					&& endState == EMPTY) {
				return true;
			}
		}
		if (square % 4 < 3 && square / 4 > 1) {
			midState = this.getState((square - 3) - (square / 4 % 2));
			endState = this.getState(square - 7);
			
			if ((((state == WHITE_PIECE || state == WHITE_KING)
					&& (midState == BLACK_PIECE || midState == BLACK_KING))
					|| (state == BLACK_KING
					&& (midState == WHITE_PIECE || midState == WHITE_KING)))
					&& endState == EMPTY) {
				return true;
			}
		}
		if (square % 4 > 0 && square / 4 > 1) {
			midState = this.getState((square - 4) - (square / 4 % 2));
			endState = this.getState(square - 9);
			
			if ((((state == WHITE_PIECE || state == WHITE_KING)
					&& (midState == BLACK_PIECE || midState == BLACK_KING))
					|| (state == BLACK_KING
					&& (midState == WHITE_PIECE || midState == WHITE_KING)))
					&& endState == EMPTY) {
				return true;
			}
		}
		
		return false;
	}
	
	// Determines whether the game of checkers is over
	public boolean gameOver(boolean turn) {
		if (this.whitePieces == 0 || this.blackPieces == 0) {
			return true;
		}
		
		boolean whiteHasMove = false;
		boolean blackHasMove = false;
		
		for (int i = 0; i < 32; i++) {
			if (turn && (this.getState(i) == WHITE_PIECE || this.getState(i) == WHITE_KING)
					&& (this.hasMove(i) || this.hasJump(i))) {
				whiteHasMove = true;
			}
			if (!turn && (this.getState(i) == BLACK_PIECE || this.getState(i) == BLACK_KING)
					&& (this.hasMove(i) || this.hasJump(i))) {
				blackHasMove = true;
			}
		}
		
		if (turn && !whiteHasMove) {
			this.winner = false;
		}
		if (!turn && !blackHasMove) {
			this.winner = true;
		}
		
		return (turn && !whiteHasMove) || (!turn && !blackHasMove);
	}
	
	// Resigns the game of checkers for the player whose turn it is currently
	public void resign(boolean turn) {
		if (turn) {
			this.winner = false;
			this.whitePieces = 0;
		}
		else {
			this.winner = true;
			this.blackPieces = 0;
		}
	}
	
	// Returns the winner of this game of checkers, if the game is over
	public boolean winner() {
		return this.winner;
	}
}