/*
 * This file will draw and set the starting position of the Stack Brick.
 * Prashant Gupta
 * 29 March 2024
 */

public class StackBrick extends TetrisBrick {
    
    public StackBrick(int center_column) {

        this.colorNum = 7;
        initPosition(center_column);
    }

    public void initPosition(int center_column) {

        this.position = new int[][] {
            {0, center_column},
            {1, center_column - 1},
            {1, center_column},
            {1, center_column + 1},
        };
    }

    public void rotate() {

        int[] zeroSegPos = new int[] {this.position[0][0], this.position[0][1]};

        if(zeroSegPos[0] == this.position[2][0] - 1 &&
            zeroSegPos[1] == this.position[2][1]) {
            this.position = new int[][] {
                {this.position[2][0], this.position[2][1] + 1},
                {this.position[2][0] - 1, this.position[2][1]},
                {this.position[2][0], this.position[2][1]},
                {this.position[2][0] + 1, this.position[2][1]},
            };
        }
        else if(zeroSegPos[0] == this.position[2][0] &&
            zeroSegPos[1] == this.position[2][1] + 1) {
            this.position = new int[][] {
                {this.position[2][0] + 1, this.position[2][1]},
                {this.position[2][0], this.position[2][1] - 1},
                {this.position[2][0], this.position[2][1]},
                {this.position[2][0], this.position[2][1] + 1},
            };
        }
        else if(zeroSegPos[0] == this.position[2][0] + 1 &&
            zeroSegPos[1] == this.position[2][1]) {
            this.position = new int[][] {
                {this.position[2][0], this.position[2][1] - 1},
                {this.position[2][0] - 1, this.position[2][1]},
                {this.position[2][0], this.position[2][1]},
                {this.position[2][0] + 1, this.position[2][1]},
            };
        }
        else if(zeroSegPos[0] == this.position[2][0] &&
            zeroSegPos[1] == this.position[2][1] - 1) {
            this.position = new int[][] {
                {this.position[2][0] - 1, this.position[2][1]},
                {this.position[2][0], this.position[2][1] - 1},
                {this.position[2][0], this.position[2][1]},
                {this.position[2][0], this.position[2][1] + 1},
            };
        }
    }

    public void unrotate() {
        this.rotate();
        this.rotate();
        this.rotate();
    }
}
