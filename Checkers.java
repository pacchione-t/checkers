package checkers;

import java.awt.Color;
import javalib.impworld.*;
import javalib.worldimages.*;

// Launches the checkers application
public class Checkers {
	public static void main(String[] args)
	{
		CheckersWorld checkers = new CheckersWorld();
		checkers.bigBang(BoardImage.SIZE * 8 + 6, BoardImage.SIZE * 10 + 9);
	}
}

// Represents the entire state of a game of checkers
class CheckersWorld extends World {
	// The game board
	private Checkerboard board;
	// The display image
	private BoardImage image;
	
	// Fields that keep track of player input and the state of the game
	private boolean turn; // false = black's turn
	private int selected;
	private boolean jumped;
	
	// Creates a new game of checkers
	public CheckersWorld() {
		this.board = new Checkerboard();
		this.image = new BoardImage();
		this.selected = -1;
		this.turn = false;
		this.jumped = false;
	}
	
	// Draws the entire display of a game of checkers with the board and information panel
	public WorldScene makeScene() {
		WorldScene scene = new WorldScene(0, 0);
		scene.placeImageXY(
				new RectangleImage(BoardImage.SIZE * 8 + 6, BoardImage.SIZE * 10 + 9,
						OutlineMode.SOLID, Color.DARK_GRAY),
				BoardImage.SIZE * 4 + 3, BoardImage.SIZE * 5 + 5);
        scene.placeImageXY(this.image.drawBoard(this.board, this.selected), 3, 3);
        scene.placeImageXY(this.image.drawPanel(
        		this.turn,
        		this.board.whitePieces(), this.board.blackPieces(),
        		this.board.gameOver(this.turn), this.board.winner()),
        		3, BoardImage.SIZE * 8 + 6);
		return scene;
	}
	
	// Handles mouse input events
	public void onMouseClicked(Posn pos) {
		int selected = -1;
		
		if (pos.y >= 3 && pos.y < BoardImage.SIZE + 3) {
			if (pos.x >= BoardImage.SIZE + 3 && pos.x < BoardImage.SIZE * 2 + 3)
				selected = 0;
			else if (pos.x >= BoardImage.SIZE * 3 + 3 && pos.x < BoardImage.SIZE * 4 + 3)
				selected = 1;
			else if (pos.x >= BoardImage.SIZE * 5 + 3 && pos.x < BoardImage.SIZE * 6 + 3)
				selected = 2;
			else if (pos.x >= BoardImage.SIZE * 7 + 3 && pos.x < BoardImage.SIZE * 8 + 3)
				selected = 3;
		}
		else if (pos.y >= BoardImage.SIZE + 3 && pos.y < BoardImage.SIZE * 2 + 3) {
			if (pos.x >= 3 && pos.x < BoardImage.SIZE + 3)
				selected = 4;
			else if (pos.x >= BoardImage.SIZE * 2 + 3 && pos.x < BoardImage.SIZE * 3 + 3)
				selected = 5;
			else if (pos.x >= BoardImage.SIZE * 4 + 3 && pos.x < BoardImage.SIZE * 5 + 3)
				selected = 6;
			else if (pos.x >= BoardImage.SIZE * 6 + 3 && pos.x < BoardImage.SIZE * 7 + 3)
				selected = 7;
		}
		else if (pos.y >= BoardImage.SIZE * 2 + 3 && pos.y < BoardImage.SIZE * 3 + 3) {
			if (pos.x >= BoardImage.SIZE + 3 && pos.x < BoardImage.SIZE * 2 + 3)
				selected = 8;
			else if (pos.x >= BoardImage.SIZE * 3 + 3 && pos.x < BoardImage.SIZE * 4 + 3)
				selected = 9;
			else if (pos.x >= BoardImage.SIZE * 5 + 3 && pos.x < BoardImage.SIZE * 6 + 3)
				selected = 10;
			else if (pos.x >= BoardImage.SIZE * 7 + 3 && pos.x < BoardImage.SIZE * 8 + 3)
				selected = 11;
		}
		else if (pos.y >= BoardImage.SIZE * 3 + 3 && pos.y < BoardImage.SIZE * 4 + 3) {
			if (pos.x >= 3 && pos.x < BoardImage.SIZE + 3)
				selected = 12;
			else if (pos.x >= BoardImage.SIZE * 2 + 3 && pos.x < BoardImage.SIZE * 3 + 3)
				selected = 13;
			else if (pos.x >= BoardImage.SIZE * 4 + 3 && pos.x < BoardImage.SIZE * 5 + 3)
				selected = 14;
			else if (pos.x >= BoardImage.SIZE * 6 + 3 && pos.x < BoardImage.SIZE * 7 + 3)
				selected = 15;
		}
		else if (pos.y >= BoardImage.SIZE * 4 + 3 && pos.y < BoardImage.SIZE * 5 + 3) {
			if (pos.x >= BoardImage.SIZE + 3 && pos.x < BoardImage.SIZE * 2 + 3)
				selected = 16;
			else if (pos.x >= BoardImage.SIZE * 3 + 3 && pos.x < BoardImage.SIZE * 4 + 3)
				selected = 17;
			else if (pos.x >= BoardImage.SIZE * 5 + 3 && pos.x < BoardImage.SIZE * 6 + 3)
				selected = 18;
			else if (pos.x >= BoardImage.SIZE * 7 + 3 && pos.x < BoardImage.SIZE * 8 + 3)
				selected = 19;
		}
		else if (pos.y >= BoardImage.SIZE * 5 + 3 && pos.y < BoardImage.SIZE * 6 + 3) {
			if (pos.x >= 3 && pos.x < BoardImage.SIZE + 3)
				selected = 20;
			else if (pos.x >= BoardImage.SIZE * 2 + 3 && pos.x < BoardImage.SIZE * 3 + 3)
				selected = 21;
			else if (pos.x >= BoardImage.SIZE * 4 + 3 && pos.x < BoardImage.SIZE * 5 + 3)
				selected = 22;
			else if (pos.x >= BoardImage.SIZE * 6 + 3 && pos.x < BoardImage.SIZE * 7 + 3)
				selected = 23;
		}
		else if (pos.y >= BoardImage.SIZE * 6 + 3 && pos.y < BoardImage.SIZE * 7 + 3) {
			if (pos.x >= BoardImage.SIZE + 3 && pos.x < BoardImage.SIZE * 2 + 3)
				selected = 24;
			else if (pos.x >= BoardImage.SIZE * 3 + 3 && pos.x < BoardImage.SIZE * 4 + 3)
				selected = 25;
			else if (pos.x >= BoardImage.SIZE * 5 + 3 && pos.x < BoardImage.SIZE * 6 + 3)
				selected = 26;
			else if (pos.x >= BoardImage.SIZE * 7 + 3 && pos.x < BoardImage.SIZE * 8 + 3)
				selected = 27;
		}
		else if (pos.y >= BoardImage.SIZE * 7 + 3 && pos.y < BoardImage.SIZE * 8 + 3) {
			if (pos.x >= 3 && pos.x < BoardImage.SIZE + 3)
				selected = 28;
			else if (pos.x >= BoardImage.SIZE * 2 + 3 && pos.x < BoardImage.SIZE * 3 + 3)
				selected = 29;
			else if (pos.x >= BoardImage.SIZE * 4 + 3 && pos.x < BoardImage.SIZE * 5 + 3)
				selected = 30;
			else if (pos.x >= BoardImage.SIZE * 6 + 3 && pos.x < BoardImage.SIZE * 7 + 3)
				selected = 31;
		}
		
		if (selected != -1 && !this.board.gameOver(this.turn)) {
			int selState = this.board.getState(selected);
			
			if (((turn && (selState == Checkerboard.WHITE_PIECE
					|| selState == Checkerboard.WHITE_KING))
					|| (!turn && (selState == Checkerboard.BLACK_PIECE
					|| selState == Checkerboard.BLACK_KING)))
					&& (this.board.hasMove(selected) || this.board.hasJump(selected))) {
				this.selected = selected;
			}
			else if (this.selected != -1) {
				int from = this.selected;
				int fromState = this.board.getState(from);
				int to = selected;
				
				if (!this.jumped && this.board.move(from, to)) {
					this.turn = !this.turn;
					this.selected = -1;
				}
				
				if (this.board.jump(from, to)) {
					this.selected = to;
					this.jumped = true;
					
					selState = this.board.getState(this.selected);
					
					if ((fromState == Checkerboard.WHITE_PIECE && selState == Checkerboard.WHITE_KING)
							|| (fromState == Checkerboard.BLACK_PIECE && selState == Checkerboard.BLACK_KING)
							|| !this.board.hasJump(this.selected)) {
						this.turn = !this.turn;
						this.selected = -1;
						this.jumped = false;
					}
				}
			}
		}
	}
	
	// Handles keyboard input events
	public void onKeyEvent(String key) {
		if (key.equals("enter") && this.jumped && !this.board.gameOver(this.turn)) {
			this.selected = -1;
			this.turn = !this.turn;
			this.jumped = false;
		}
		else if (key.equals("r") && !this.board.gameOver(this.turn)) {
			this.selected = -1;
			this.board.resign(this.turn);
		}
		else if (key.equals("r") && this.board.gameOver(this.turn)) {
			this.board = new Checkerboard();
			this.turn = false;
			this.jumped = false;
		}
	}
}