/*
 * This file will govern all the game functionality of the Tetris Game.
 * Prashant Gupta
 * 26 April 2024
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class TetrisGame {
    
    private TetrisBrick fallingBrick;
    private int[][] background;
    private int rows;
    private int cols;
    private int numBrickTypes = 7;
    private int state = 1;
    private int score = 0;
    private Random randomGen;

    public TetrisGame(int gameRows, int gameCols) {
         
        this.rows = gameRows;
        this.cols = gameCols;

        randomGen = new Random();
        background = new int[rows][cols];
        
        initBoard();
        spawnBrick();
    }

    public String toString() {
        
        String out = Integer.toString(rows) + "," + Integer.toString(cols);
        out += "\n" + Integer.toString(score) + "\n";

        for(int baordRow = 0; baordRow < rows; baordRow++) {
            for(int boardCol = 0; boardCol < cols; boardCol++) {
                out += background[baordRow][boardCol];
                if(boardCol != cols - 1) {
                    out += ",";
                }
            }
            out += "\n";
        }

        return out;
    }

    public void initBoard() {

        for(int boardRows = 0; boardRows < rows; boardRows++) {
            for(int boardCols = 0; boardCols < cols; boardCols++) {
                background[boardRows][boardCols] = 0;
            }
        }
    }

    public void newGame() {
        state = 1;
        score = 0;

        initBoard();
        spawnBrick();
    }

    public int fetchBoardPosition(int row, int col) {
        return background[row][col];
    }

    private void transferColor(){

        for(int[] segPos : fallingBrick.position) {
            background[segPos[0]][segPos[1]] = fallingBrick.colorNum;
        }
        scoreGame();
    }

    private void spawnBrick() {

        int center_column = cols/2;
        int randBrickNum = randomGen.nextInt(numBrickTypes);

        if(background[0][center_column] != 0) {
            randBrickNum = numBrickTypes + 1;
        }
        switch (randBrickNum) {
            case 0:
                fallingBrick = new ElBrick(center_column);
                break;
            case 1:
                fallingBrick = new EssBrick(center_column);
                break;
            case 2:
                fallingBrick = new JayBrick(center_column);
                break;
            case 3:
                fallingBrick = new LongBrick(center_column);
                break;
            case 4:
                fallingBrick = new SquareBrick(center_column);
                break;
            case 5:
                fallingBrick = new StackBrick(center_column);
                break;
            case 6:
                fallingBrick = new ZeeBrick(center_column);
                break;
            default:
                state = 0;
                break;
        }

        boolean spawnNew = false;
        if(state == 1) {
            
            for(int[] segs : fallingBrick.position) {
                if(background[segs[0]][segs[1]] != 0) {
                    spawnNew = true;
                    break;
                }
            }

            if(spawnNew) {
                spawnBrick();
            }
        }
    }

    public void makeMove(char direction) {
        if(direction == 'T') {
            fallingBrick.rotate();
            if(!validateMove()) {
                fallingBrick.unrotate();
            }
        }
        else if(direction == 'D') {
            fallingBrick.moveDown();
            if(!validateMove()){
                fallingBrick.moveUp();
                transferColor();
                spawnBrick();
            }
        }
        else if(direction == 'F') {
            
            while(validateMove()) {
                fallingBrick.moveDown();
                if(!validateMove()) {
                    fallingBrick.moveUp();
                    transferColor();
                }
            }
            spawnBrick();
        }
        else if(direction == 'R') {
            fallingBrick.moveRight();
            if(!validateMove()) {
                fallingBrick.moveLeft();
            }
        }
        else if(direction == 'L') {
            fallingBrick.moveLeft();
            if(!validateMove()) {
                fallingBrick.moveRight();
            }
        }
    }

    private boolean validateMove() {

        boolean isValidMove = true;
        for(int[] segPos : fallingBrick.position) {
            if(segPos[0] < 0) {
                isValidMove = false;
            }
            else if(segPos[0] >= rows) {
                isValidMove = false;
            }
            else if(segPos[1] >= cols) {
                isValidMove = false;
            }
            else if(segPos[1] < 0) {
                isValidMove = false;
            }
            else if(background[segPos[0]][segPos[1]] != 0) {
                isValidMove = false;
            }
        }
        return isValidMove;
    }
    
    private boolean rowHasSpace(int rowNum) {

        boolean hasSpace = true;
        for(int baordCol = 0; baordCol < cols; baordCol++) {
            if(background[rowNum][baordCol] != 0) {
                hasSpace = false;
            }
            else {
                hasSpace = true;
                break;
            }
        }

        return hasSpace;
    }

    private boolean rowHasColor(int rowNum) {

        boolean hasColor = false;
        for(int baordCol = 0; baordCol < cols; baordCol++) {
            if(background[rowNum][baordCol] != 0) {
                hasColor = true;
            }
        }

        return hasColor;
    }

    private void copyRow(int rowNum) {

        for(int baordCol = 0; baordCol < cols; baordCol++) {
            background[rowNum][baordCol] = background[rowNum - 1][baordCol];
        }
    }

    private void copyAllRows(int startRowNum) {

        while(startRowNum > 0) {
            if(rowHasColor(startRowNum)) {
                copyRow(startRowNum);
            }
            startRowNum--;
        }
    }

    private void scoreGame() {
        
        int combo = 0;
        int startRow = fallingBrick.getMinRow();
        int stopRow = fallingBrick.getMaxRow();

        while(startRow <= stopRow) {

            if(!rowHasSpace(startRow)) {
                copyAllRows(startRow);
                combo++;
            }
            startRow++;
        }

        switch (combo) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 600;
                break;
            case 4:
                score += 1200;
                break;
        } 
    }
    
    public void saveToFile() {

        JFileChooser fileChooser = new JFileChooser(
            "./Saved_Games", 
            FileSystemView.getFileSystemView());
        fileChooser.showSaveDialog(null);

        String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".gam";

        File newFile = new File(filePath);
        try{
            FileWriter writer = new FileWriter(newFile, true);
            writer.append(toString());
            writer.append(fallingBrick.toString());
            writer.close();

        }catch(IOException ioe) {

        }
    }

    public void retrieveFromFile() {

        JFileChooser fileChooser = new JFileChooser(
            "./Saved_Games", 
            FileSystemView.getFileSystemView());
        fileChooser.showOpenDialog(null);
        File gameFile = fileChooser.getSelectedFile();
        try {

            Scanner scan = new Scanner(gameFile);
            scan.useDelimiter(",|\n");
    
            rows = Integer.parseInt(scan.next());
            cols = Integer.parseInt(scan.next());
            score = Integer.parseInt(scan.next());
    
            for(int boardRows = 0; boardRows < rows; boardRows++) {
                for(int boardCols = 0; boardCols < cols; boardCols++) {
                    background[boardRows][boardCols] = Integer.parseInt(scan.next());
                }
            }
            
            fallingBrick.colorNum = Integer.parseInt(scan.next());
            determineBrickType(fallingBrick.colorNum);
            for(int seg = 0; seg < fallingBrick.position.length; seg++) {
                fallingBrick.position[seg][0] = Integer.parseInt(scan.next());
                fallingBrick.position[seg][1] = Integer.parseInt(scan.next());
            }
            
            scan.close();
        }catch(IOException ioe) {}
    }

    private void determineBrickType(int colorNum) {

        int center_column = cols/2;
        switch(colorNum) {

            case 2:
                fallingBrick = new ElBrick(center_column);
                break;
            case 3:
                fallingBrick = new EssBrick(center_column);
                break;
            case 4:
                fallingBrick = new JayBrick(center_column);
                break;
            case 5:
                fallingBrick = new LongBrick(center_column);
                break;
            case 6:
                fallingBrick = new SquareBrick(center_column);
                break;
            case 7:
                fallingBrick = new StackBrick(center_column);
                break;
            case 8:
                fallingBrick = new ZeeBrick(center_column);
                break;
        }
    }

    public int getFallingBrickColor() {
        return fallingBrick.colorNum;
    }

    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    public int getNumberSegs() {
        return fallingBrick.numSegments;
    }

    public int getState() {
        return state;
    }

    public int getScore() {
        return score;
    }

    public int getSegRow(int segNum) {
        return fallingBrick.position[segNum][0];
    }

    public int getSegCol(int segNum) {
        return fallingBrick.position[segNum][1];
    } 
}