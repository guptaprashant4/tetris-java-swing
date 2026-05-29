/*
 * This file will draw and set the starting position of the Square Brick.
 * Prashant Gupta
 * 13 March 2024
 */

public class SquareBrick extends TetrisBrick {
    
    public SquareBrick(int center_column) {

        this.colorNum = 6;
        initPosition(center_column);
    }

    public void initPosition(int center_column) {

        this.position = new int[][] {
            {0, center_column},
            {0, center_column - 1},
            {1, center_column},
            {1, center_column - 1},
        };
    }

    public void rotate() {}

    public void unrotate() {}
}
