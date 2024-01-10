package src;

import java.awt.Color;
import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;

// Launches the checkers application
public class Checkers {
    public static void main(String[] args) {
        CheckersWorld checkers = new CheckersWorld();
        checkers.bigBang(BoardImage.SIZE * 8 + 6, BoardImage.SIZE * 10 + 9);
    }
}

// Represents the entire state of a game of checkers
// The controller in the MVC design pattern
class CheckersWorld extends World {
    // The game board
    private Checkerboard board;
    // The display image
    private BoardImage image;

    // Fields that keep track of user input and the state of the game
    private boolean turn; // false = black's turn
    private int selected;
    private boolean jumped;
    private boolean resigned;
    private boolean winner;

    // Creates a new game of checkers
    public CheckersWorld() {
        board = new Checkerboard();
        image = new BoardImage();
        selected = -1;
        turn = false;
        jumped = false;
        resigned = false;
        winner = false;
    }

    // Draws the entire display of a game of checkers, including the board and
    // information panel
    public WorldScene makeScene() {
        WorldScene scene = new WorldScene(0, 0);
        scene.placeImageXY(new RectangleImage(BoardImage.SIZE * 8 + 6, BoardImage.SIZE * 10 + 9, OutlineMode.SOLID,
                Color.DARK_GRAY), BoardImage.SIZE * 4 + 3, BoardImage.SIZE * 5 + 5);
        scene.placeImageXY(image.drawBoard(board, selected), 3, 3);
        scene.placeImageXY(image.drawPanel(turn, board.whitePieces(), board.blackPieces(), gameOver(), winner), 3,
                BoardImage.SIZE * 8 + 6);
        return scene;
    }

    // Handles mouse input events
    public void onMouseClicked(Posn pos) {
        int selSquare = -1;

        if (pos.x >= 3 && pos.x < BoardImage.SIZE * 8 + 3 && pos.y >= 3 && pos.y < BoardImage.SIZE * 8 + 3) {
            int row = (pos.y - 3) / BoardImage.SIZE;
            int col = (pos.x - 3) / BoardImage.SIZE;

            if (row % 2 != col % 2) {
                selSquare = row * 4 + col / 2;
            }
        }

        if (selSquare != -1 && !gameOver()) {
            int selState = board.getState(selSquare);

            if (selState != Checkerboard.EMPTY && ((turn && selState % 2 == 1) || (!turn && selState % 2 == 0))
                    && board.hasMove(selSquare)) {
                selected = selSquare;
            } else if (selected != -1) {
                int fromSquare = selected;
                int fromState = board.getState(fromSquare);
                int toSquare = selSquare;

                if (!jumped && board.move(fromSquare, toSquare)) {
                    turn = !turn;
                    selected = -1;
                }

                if (board.jump(fromSquare, toSquare)) {
                    selected = toSquare;
                    jumped = true;

                    selState = board.getState(selected);
                    if (selState == fromState + 2 || !board.hasJump(selected)) {
                        turn = !turn;
                        selected = -1;
                        jumped = false;
                    }
                }
            }
        }
    }

    // Handles keyboard input events
    public void onKeyEvent(String key) {
        if (key.equals("enter") && jumped && !gameOver()) {
            selected = -1;
            turn = !turn;
            jumped = false;
        }
        if (key.equals("r")) {
            if (!gameOver()) {
                selected = -1;
                resigned = true;
            } else {
                board = new Checkerboard();
                turn = false;
                jumped = false;
                resigned = false;
            }
        }
    }

    // Determines whether the game of checkers is over
    private boolean gameOver() {
        if (resigned) {
            winner = !turn;
            return true;
        }

        if (board.whitePieces() == 0) {
            winner = false;
            return true;
        }
        if (board.blackPieces() == 0) {
            winner = true;
            return true;
        }

        if (turn && !board.whiteHasMove()) {
            winner = false;
            return true;
        }
        if (!turn && !board.blackHasMove()) {
            winner = true;
            return true;
        }

        return false;
    }
}
