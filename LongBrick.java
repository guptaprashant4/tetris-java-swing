/*
 * This file will draw and set the starting position of the Long Brick.
 * Prashant Gupta
 * 29 March 2024
 */

public class LongBrick extends TetrisBrick{
    
    public LongBrick(int center_column) {

        this.colorNum = 5;
        initPosition(center_column);
    }

    public void initPosition(int center_column) {

        this.position = new int[][] {
            {0, center_column - 2},
            {0, center_column - 1},
            {0, center_column},
            {0, center_column + 1},
        };
    }

    public void rotate() {

        int[] scndSegPos = new int[] {this.position[2][0], this.position[2][1]};

        if(scndSegPos[0] == this.position[1][0] &&
            scndSegPos[1] == this.position[1][1] + 1) {
            this.position = new int[][] {
                {this.position[1][0] - 1, this.position[1][1]},
                {this.position[1][0], this.position[1][1]},
                {this.position[1][0] + 1, this.position[1][1]},
                {this.position[1][0] + 2, this.position[1][1]},
            };
        }
        else {
            this.position = new int[][] {
                {this.position[1][0], this.position[1][1] - 1},
                {this.position[1][0], this.position[1][1]},
                {this.position[1][0], this.position[1][1] + 1},
                {this.position[1][0], this.position[1][1] + 2},
            };
        }
    }

    public void unrotate() {
        this.rotate();
        this.rotate();
        this.rotate();
    }
}
