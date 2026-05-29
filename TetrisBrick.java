/*
 * This is an abstract class only used as a Super class for creating various 
 * types of bricks required to implement the Tetris Game
 * Prashant Gupta
 * 20 April 2024
 */

import java.awt.Color;

public abstract class TetrisBrick {
    
    protected int numSegments = 4;
    protected int[][] position = new int[numSegments][2];
    protected Color color;
    protected int colorNum;

    public TetrisBrick() {}

    public String toString() {

        String out = Integer.toString(colorNum) + "\n";
        for(int[] segs : position) {
            out += segs[0] + "," + segs[1];
            if(segs != position[3]) {
                out += "\n";
            }
        }
        return out;
    }

    public void moveDown() {

        for(int[] pos: position) {
            pos[0]++;
        }
    }

    public void moveUp() {

        for(int[] pos : position) {
            pos[0]--;
        }
    }

    public void moveLeft() {

        for(int[] pos : position) {
            pos[1]--;
        }
    }

    public void moveRight() {

        for(int[] pos : position) {
            pos[1]++;
        }
    }

    public int getMinRow() {

        int minRow = this.position[0][0];
        for(int[] segs : this.position) {
            if(segs[0] < minRow) {
                minRow = segs[0];
            }
        }
        return minRow;
    }

    public int getMaxRow() {

        int maxRow = 0;
        for(int[] segs : this.position) {
            if(maxRow < segs[0]) {
                maxRow = segs[0];
            }
        }
        return maxRow;
    }
    
    public int getColorNumber() {
        return colorNum;
    }

    
    public abstract void initPosition(int center_column);

    public abstract void rotate();

    public abstract void unrotate();
}
