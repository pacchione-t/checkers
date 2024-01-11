package src;

// Represents the state of the board for a game of checkers
// The "model" in the MVC design pattern
public class Checkerboard {
    // The states of the individual squares on the board
    public static final int EMPTY = 0;
    public static final int WHITE_PIECE = 1;
    public static final int BLACK_PIECE = 2;
    public static final int WHITE_KING = 3;
    public static final int BLACK_KING = 4;

    // The states of the individual squares on the board
    private int[] states;
    // The numbers of white and black pieces remaining on the board
    private int whitePieces;
    private int blackPieces;

    // Creates a new checkers board
    public Checkerboard() {
        states = new int[32];
        makeBoard();
        whitePieces = 12;
        blackPieces = 12;
    }

    // Sets each square on the board to the values
    // corresponding to the beginning of a game of checkers
    private void makeBoard() {
        for (int i = 0; i < 12; i++) {
            states[i] = BLACK_PIECE;
        }
        for (int i = 12; i < 20; i++) {
            states[i] = EMPTY;
        }
        for (int i = 20; i < 32; i++) {
            states[i] = WHITE_PIECE;
        }
    }

    // Returns the state of a given square on the checkers board
    public int getState(int square) {
        return states[square];
    }

    // Sets the state of the given square to the given value
    private void setState(int square, int state) {
        states[square] = state;
    }

    // Returns the number of white pieces left on the board
    public int whitePieces() {
        return whitePieces;
    }

    // Returns the number of black pieces left on the board
    public int blackPieces() {
        return blackPieces;
    }

    // Completes a move if it is legal; returns true if successful
    public boolean move(int fromSquare, int toSquare) {
        int fromState = getState(fromSquare);
        int toState = getState(toSquare);

        if (fromState != EMPTY && toState == EMPTY
                && ((fromSquare < toSquare && fromState != WHITE_PIECE)
                        || (fromSquare > toSquare && fromState != BLACK_PIECE))
                && ((Math.abs(fromSquare - toSquare) == 4)
                        || ((fromSquare / 4 % 2 == 0 && fromSquare % 8 != 3 && fromSquare % 8 != 4)
                                && (toSquare == fromSquare - 3 || toSquare == fromSquare + 5))
                        || ((fromSquare / 4 % 2 == 1 && fromSquare % 8 != 3 && fromSquare % 8 != 4)
                                && (toSquare == fromSquare - 5 || toSquare == fromSquare + 3)))) {
            setState(toSquare, getState(fromSquare));
            setState(fromSquare, EMPTY);

            king(toSquare);

            return true;
        }

        return false;
    }

    // Completes a jump if it is legal; returns true if successful
    public boolean jump(int fromSquare, int toSquare) {
        int midSquare = fromSquare / 4 % 2 == 1 ? Math.abs(fromSquare + toSquare) / 2
                : Math.abs(fromSquare + toSquare) / 2 + 1;

        int fromState = getState(fromSquare);
        int midState = getState(midSquare);
        int toState = getState(toSquare);

        if (fromState != EMPTY && midState != EMPTY && toState == EMPTY
                && fromState % 2 != midState % 2
                && ((fromSquare < toSquare && fromState != WHITE_PIECE)
                        || (fromSquare > toSquare && fromState != BLACK_PIECE))
                && (((toSquare == fromSquare + 9 || toSquare == fromSquare - 7) && fromSquare % 4 != 3)
                        || ((toSquare == fromSquare + 7 || toSquare == fromSquare - 9) && fromSquare % 4 != 0))) {
            setState(toSquare, getState(fromSquare));
            setState(fromSquare, EMPTY);
            setState(midSquare, EMPTY);

            if (midState % 2 == 1) {
                whitePieces--;
            } else {
                blackPieces--;
            }

            king(toSquare);

            return true;
        }

        return false;
    }

    // "Kings" the checkers piece at the given square on the board
    private void king(int square) {
        if ((getState(square) == WHITE_PIECE && square / 4 == 0)
                || (getState(square) == BLACK_PIECE && square / 4 == 7)) {
            setState(square, getState(square) + 2);
        }
    }

    // Determines whether the piece at the given square has a legal move or jump
    public boolean hasMove(int square) {
        int state = getState(square);

        if (state == EMPTY) {
            return false;
        }

        int endState;

        if (square % 8 != 3 && square / 4 < 7) {
            endState = getState((square + 5) - (square / 4 % 2));

            if (state != WHITE_PIECE && endState == EMPTY) {
                return true;
            }
        }
        if (square % 8 != 4 && square / 4 < 7) {
            endState = getState((square + 4) - (square / 4 % 2));

            if (state != WHITE_PIECE && endState == EMPTY) {
                return true;
            }
        }
        if (square % 8 != 3 && square / 4 > 0) {
            endState = getState((square - 3) - (square / 4 % 2));

            if (state != BLACK_PIECE && endState == EMPTY) {
                return true;
            }
        }
        if (square % 8 != 4 && square / 4 > 0) {
            endState = getState((square - 4) - (square / 4 % 2));

            if (state != BLACK_PIECE && endState == EMPTY) {
                return true;
            }
        }

        return hasJump(square);
    }

    // Determines whether the piece at the given square has a legal jump
    public boolean hasJump(int square) {
        int state = getState(square);

        if (state == EMPTY) {
            return false;
        }

        int midState;
        int endState;

        if (square % 4 < 3 && square / 4 < 6) {
            midState = getState((square + 5) - (square / 4 % 2));
            endState = getState(square + 9);

            if (state != WHITE_PIECE && midState != EMPTY && endState == EMPTY && state % 2 != midState % 2) {
                return true;
            }
        }
        if (square % 4 > 0 && square / 4 < 6) {
            midState = getState((square + 4) - (square / 4 % 2));
            endState = getState(square + 7);

            if (state != WHITE_PIECE && midState != EMPTY && endState == EMPTY && state % 2 != midState % 2) {
                return true;
            }
        }
        if (square % 4 < 3 && square / 4 > 1) {
            midState = getState((square - 3) - (square / 4 % 2));
            endState = getState(square - 7);

            if (state != BLACK_PIECE && midState != EMPTY && endState == EMPTY && state % 2 != midState % 2) {
                return true;
            }
        }
        if (square % 4 > 0 && square / 4 > 1) {
            midState = getState((square - 4) - (square / 4 % 2));
            endState = getState(square - 9);

            if (state != BLACK_PIECE && midState != EMPTY && endState == EMPTY && state % 2 != midState % 2) {
                return true;
            }
        }

        return false;
    }

    // Determines whether white or black has any legal moves (including jumps)
    public boolean hasMove(boolean color) {
        int state;

        for (int i = 0; i < 32; i++) {
            state = getState(i);

            if (state != EMPTY && color == (state % 2 == 1) && hasMove(i)) {
                return true;
            }
        }

        return false;
    }
}
