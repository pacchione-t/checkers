import java.awt.Color;

import javalib.worldimages.AlignModeX;
import javalib.worldimages.AlignModeY;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EmptyImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.OverlayOffsetAlign;
import javalib.worldimages.OverlayOffsetImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.TriangleImage;
import javalib.worldimages.WorldImage;

// Represents the display image of a checkers application
public class BoardImage {
    // The image of the board
    private WorldImage checkerboard;

    // The relative size of the output image
    final public static int SIZE = 60;

    final private Color DARK_GREEN = Color.GREEN.darker().darker();

    // Creates a new display image object
    public BoardImage() {
        WorldImage checkerboard = new RectangleImage(
                SIZE * 8, SIZE * 8, OutlineMode.SOLID, Color.WHITE);
        checkerboard = checkerboard.movePinhole(-SIZE * 3, -SIZE * 4);

        WorldImage blackSquare = new RectangleImage(
                SIZE, SIZE, OutlineMode.SOLID, DARK_GREEN);
        blackSquare = blackSquare.movePinhole(-SIZE / 2, -SIZE / 2);

        for (int i = 0; i < 32; i++) {
            checkerboard = new OverlayImage(blackSquare, checkerboard);

            if (i % 4 != 3) {
                checkerboard = checkerboard.movePinhole(SIZE * 2, 0);
            } else {
                checkerboard = checkerboard.movePinhole(-SIZE * 6, SIZE);

                if ((i / 4) % 2 == 0) {
                    checkerboard = checkerboard.movePinhole(-SIZE, 0);
                } else {
                    checkerboard = checkerboard.movePinhole(SIZE, 0);
                }
            }
        }

        checkerboard = checkerboard.movePinhole(-SIZE, -SIZE * 8);

        this.checkerboard = checkerboard;
    }

    // Draws the checkerboard portion of the display image
    public WorldImage drawBoard(Checkerboard board, int selected) {
        WorldImage image = this.checkerboard;

        image = image.movePinhole(SIZE, 0);

        for (int i = 0; i < 32; i++) {
            image = new OverlayImage(this.drawSquare(board.getState(i), selected == i), image);

            if (i % 4 != 3) {
                image = image.movePinhole(SIZE * 2, 0);
            } else {
                image = image.movePinhole(-SIZE * 6, SIZE);

                if ((i / 4) % 2 == 0) {
                    image = image.movePinhole(-SIZE, 0);
                } else {
                    image = image.movePinhole(SIZE, 0);
                }
            }
        }

        image = image.movePinhole(-SIZE, -SIZE * 8);

        return image;
    }

    // Draws an individual square on the board
    private WorldImage drawSquare(int state, boolean selected) {
        WorldImage whitePiece = new CircleImage(
                SIZE * 5 / 12, OutlineMode.SOLID, Color.RED);
        whitePiece = whitePiece.movePinhole(-SIZE / 2, -SIZE / 2);

        WorldImage blackPiece = new CircleImage(
                SIZE * 5 / 12, OutlineMode.SOLID, Color.BLACK);
        blackPiece = blackPiece.movePinhole(-SIZE / 2, -SIZE / 2);

        WorldImage whiteKing = new TriangleImage(new Posn(0, 0),
                new Posn(SIZE / 3, SIZE / 3),
                new Posn(SIZE / 12, SIZE * 5 / 12),
                OutlineMode.SOLID, Color.BLACK);
        whiteKing = whiteKing.movePinhole(-SIZE / 6, -SIZE * 5 / 24);
        whiteKing = new OverlayImage(whiteKing,
                new CircleImage(SIZE / 24, OutlineMode.SOLID, Color.BLACK));
        whiteKing = whiteKing.movePinhole(SIZE / 4, SIZE / 6);
        whiteKing = new OverlayImage(whiteKing,
                new TriangleImage(new Posn(0, 0),
                        new Posn(-SIZE / 6, SIZE / 2),
                        new Posn(SIZE / 6, SIZE / 2),
                        OutlineMode.SOLID, Color.BLACK));
        whiteKing = whiteKing.movePinhole(0, -SIZE / 4);
        whiteKing = new OverlayImage(whiteKing,
                new CircleImage(SIZE / 24, OutlineMode.SOLID, Color.BLACK));
        whiteKing = whiteKing.movePinhole(SIZE / 12, SIZE * 7 / 24);
        whiteKing = new OverlayImage(whiteKing,
                new TriangleImage(new Posn(0, 0),
                        new Posn(-SIZE / 3, SIZE / 3),
                        new Posn(-SIZE / 12, SIZE * 5 / 12),
                        OutlineMode.SOLID, Color.BLACK));
        whiteKing = whiteKing.movePinhole(SIZE / 6, -SIZE * 5 / 24);
        whiteKing = new OverlayImage(whiteKing,
                new CircleImage(SIZE / 24, OutlineMode.SOLID, Color.BLACK));
        whiteKing = whiteKing.movePinhole(-SIZE * 3 / 4, -SIZE * 7 / 20);
        whiteKing = new OverlayImage(whiteKing, whitePiece);

        WorldImage blackKing = new TriangleImage(new Posn(0, 0),
                new Posn(SIZE / 3, SIZE / 3),
                new Posn(SIZE / 12, SIZE * 5 / 12),
                OutlineMode.SOLID, Color.WHITE);
        blackKing = blackKing.movePinhole(-SIZE / 6, -SIZE * 5 / 24);
        blackKing = new OverlayImage(blackKing,
                new CircleImage(SIZE / 24, OutlineMode.SOLID, Color.WHITE));
        blackKing = blackKing.movePinhole(SIZE / 4, SIZE / 6);
        blackKing = new OverlayImage(blackKing,
                new TriangleImage(new Posn(0, 0),
                        new Posn(-SIZE / 6, SIZE / 2),
                        new Posn(SIZE / 6, SIZE / 2),
                        OutlineMode.SOLID, Color.WHITE));
        blackKing = blackKing.movePinhole(0, -SIZE / 4);
        blackKing = new OverlayImage(blackKing,
                new CircleImage(SIZE / 24, OutlineMode.SOLID, Color.WHITE));
        blackKing = blackKing.movePinhole(SIZE / 12, SIZE * 7 / 24);
        blackKing = new OverlayImage(blackKing,
                new TriangleImage(new Posn(0, 0),
                        new Posn(-SIZE / 3, SIZE / 3),
                        new Posn(-SIZE / 12, SIZE * 5 / 12),
                        OutlineMode.SOLID, Color.WHITE));
        blackKing = blackKing.movePinhole(SIZE / 6, -SIZE * 5 / 24);
        blackKing = new OverlayImage(blackKing,
                new CircleImage(SIZE / 24, OutlineMode.SOLID, Color.WHITE));
        blackKing = blackKing.movePinhole(-SIZE * 3 / 4, -SIZE * 7 / 20);
        blackKing = new OverlayImage(blackKing, blackPiece);

        Color selectedColor = new Color(255, 255, 0, 127);
        WorldImage selectedSquare = new RectangleImage(
                SIZE, SIZE, OutlineMode.SOLID, selectedColor);
        selectedSquare = selectedSquare.movePinhole(-SIZE / 2, -SIZE / 2);

        if (!selected) {
            switch (state) {
                case Checkerboard.WHITE_PIECE:
                    return whitePiece;
                case Checkerboard.BLACK_PIECE:
                    return blackPiece;
                case Checkerboard.WHITE_KING:
                    return whiteKing;
                case Checkerboard.BLACK_KING:
                    return blackKing;
                default:
                    return new EmptyImage();
            }
        } else {
            switch (state) {
                case Checkerboard.WHITE_PIECE:
                    return new OverlayImage(selectedSquare, whitePiece);
                case Checkerboard.BLACK_PIECE:
                    return new OverlayImage(selectedSquare, blackPiece);
                case Checkerboard.WHITE_KING:
                    return new OverlayImage(selectedSquare, whiteKing);
                case Checkerboard.BLACK_KING:
                    return new OverlayImage(selectedSquare, blackKing);
                default:
                    return selectedSquare;
            }
        }
    }

    // Draws the panel containing important information about the state of the game
    public WorldImage drawPanel(boolean turn,
            int whitePieces, int blackPieces,
            boolean gameOver, boolean winner) {
        WorldImage panel = new RectangleImage(SIZE * 8, SIZE * 2,
                OutlineMode.SOLID, Color.WHITE);

        if (!gameOver) {
            panel = new OverlayOffsetImage(
                    new TextImage("Turn:", SIZE, Color.BLACK),
                    SIZE * 2.5, 0, panel);

            if (turn) {
                panel = new OverlayOffsetImage(
                        new CircleImage(SIZE / 2, OutlineMode.SOLID, Color.RED),
                        SIZE / 2, 0, panel);
            } else {
                panel = new OverlayOffsetImage(
                        new CircleImage(SIZE / 2, OutlineMode.SOLID, Color.BLACK),
                        SIZE / 2, 0, panel);
            }

            panel = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE,
                    new TextImage("Red: " + whitePieces, SIZE * 3 / 4, Color.BLACK),
                    -SIZE * 4.5, -SIZE / 2.5, panel);

            panel = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE,
                    new TextImage("Black: " + blackPieces, SIZE * 3 / 4, Color.BLACK),
                    -SIZE * 4.5, SIZE / 2.5, panel);
        } else {
            if (winner) {
                panel = new OverlayImage(
                        new TextImage("Red Wins!", SIZE * 1.2, Color.BLACK),
                        panel);
            } else {
                panel = new OverlayImage(
                        new TextImage("Black Wins!", SIZE * 1.2, Color.BLACK),
                        panel);
            }
        }

        return panel.movePinhole(-SIZE * 4, -SIZE);
    }
}
