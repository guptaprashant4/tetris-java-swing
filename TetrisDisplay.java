/*
 * This file controls all the display animation and shapes of the tetris game
 * Prashant Gupta
 * 26 April 2024
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel {
    
    private TetrisGame game;
    private int start_x;
    private int start_y;
    private int rows;
    private int cols;
    private int cell_size = 20;
    private int delay = 200;
    private boolean pause = false;
    private Timer timer;
    private Color[] colors = {Color.WHITE, Color.BLACK, Color.BLUE, 
                        Color.YELLOW, Color.PINK, Color.RED,Color.MAGENTA, 
                        Color.ORANGE, Color.GREEN};

    public TetrisDisplay(TetrisGame gam) {

        this.game = gam;
        this.rows = game.getRows();
        this.cols = game.getCols();

        this.timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cycleMove();
            }
        });
        
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent k) {
                translateKey(k);
            }
        });

        this.timer.start();
        this.setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        
        float strokeWid = 2.0f;
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(strokeWid));

        drawWell(g);
        drawScoreBoard(g);
        drawBackground(g);
        drawFallingBrick(g);

        if(game.getState() == 0) {
            gameOver(g);
        }
    }

    private void drawFallingBrick(Graphics g) {

        for(int seg = 0; seg < game.getNumberSegs(); seg++) {
            int segRow = game.getSegCol(seg) * cell_size + this.start_x;
            int segCol = game.getSegRow(seg) * cell_size + this.start_y;

            Color brickColor = colors[game.getFallingBrickColor()];
            g.setColor(brickColor);
            g.fillRect(segRow, segCol, cell_size, cell_size);
            g.drawRect(segRow, segCol, cell_size, cell_size);
            g.setColor(colors[1]);
            g.drawRect(segRow, segCol, cell_size, cell_size);
        }
    }

    private void drawWell(Graphics g) {

        this.start_x = (this.getWidth() - game.getCols() * cell_size) / 2;
        this.start_y = (this.getHeight() - game.getRows() * cell_size) / 2;
        
        int p1_xcor = this.start_x;
        int p2_xcor = p1_xcor;
        int p3_xcor = p2_xcor + cols * cell_size;
        int p4_xcor = p3_xcor;
        int p5_xcor = p4_xcor + cell_size;
        int p6_xcor = p5_xcor;
        int p7_xcor = p2_xcor - cell_size;
        int p8_xcor = p7_xcor;

        int p1_ycor = this.start_y;
        int p2_ycor = p1_ycor + rows * cell_size;
        int p3_ycor = p2_ycor;
        int p4_ycor = p1_ycor;
        int p5_ycor = p4_ycor;
        int p6_ycor = p3_ycor + cell_size;
        int p7_ycor = p6_ycor;
        int p8_ycor = p1_ycor;

        int[] poly_xcor = {p1_xcor, p2_xcor, p3_xcor, p4_xcor, p5_xcor, p6_xcor,
                        p7_xcor, p8_xcor};
        int[] poly_ycor = {p1_ycor, p2_ycor, p3_ycor, p4_ycor, p5_ycor, p6_ycor,
                        p7_ycor, p8_ycor};
        int numOfPoints = 8;
        g.setColor(colors[1]);
        g.drawPolygon(poly_xcor, poly_ycor, numOfPoints);
        g.fillPolygon(poly_xcor, poly_ycor, numOfPoints);
    }

    private void drawBackground(Graphics g) {

        for(int backgroundRow = 0; backgroundRow < rows; backgroundRow++) {
            for(int backgroundCol = 0; backgroundCol < cols; backgroundCol++) {
                Color cellColor = colors[
                    game.fetchBoardPosition(backgroundRow, backgroundCol)];
                
                int boardStart_x = this.start_x + backgroundCol * cell_size;
                int boardStart_y = this.start_y + backgroundRow * cell_size;
                g.setColor(cellColor);
                g.fillRect(boardStart_x, boardStart_y, cell_size, cell_size);
                if(cellColor != Color.WHITE) {
                    g.setColor(colors[1]);
                    g.drawRect(boardStart_x, boardStart_y, cell_size, cell_size);
                }
            }
        }
    }

    private void drawScoreBoard(Graphics g) {

        int fontSize = 50;
        Font textFont = new Font("Arial", 1, fontSize);
        String label = "Score: " + Integer.toString(game.getScore());
        int boardWid = label.length() * fontSize / 2 + 5;
        int boardHeight = 60;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, boardWid, boardHeight);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, boardWid, boardHeight);

        g.setColor(Color.BLUE);
        g.setFont(textFont);
        g.drawString(label, 0, boardHeight / 2 + fontSize / 2);
        
    }

    private void gameOver(Graphics g) {

        int fontSize = 70;
        Font texFont = new Font("Arial", 1, fontSize);
        String text = "Game Over!  ";
        int boardWid = (cols + text.length()) * cell_size;
        int boardHeight = 100;
        int x_cor = start_x - (text.length()/2) * cell_size;
        int y_cor = start_y + boardHeight / 2 + fontSize / 2;

        g.setColor(Color.WHITE);
        g.fillRect(x_cor, y_cor, boardWid, boardHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x_cor, y_cor, boardWid, boardHeight);

        g.setColor(Color.BLUE);
        g.setFont(texFont);
        g.drawString(text, x_cor + 40 * rows/10, y_cor + 70);

        this.timer.stop();
    }

    private void translateKey(KeyEvent k) {

        if(k.getKeyCode() == KeyEvent.VK_N) {
            game.newGame();
            this.timer.start();
        }
        else if(k.getKeyCode() == KeyEvent.VK_SPACE) {
            if(this.pause == true) {
                this.timer.start();
                this.pause = false;
            }
            else if(this.pause == false) {
                this.timer.stop();
                this.pause = true;
            }
        }
        else if(k.getKeyCode() == KeyEvent.VK_UP) {
            game.makeMove('T');
        }
        else if(k.getKeyCode() == KeyEvent.VK_DOWN) {
            game.makeMove('F');
        }
        else if(k.getKeyCode() == KeyEvent.VK_LEFT) {
            game.makeMove('L');
        }
        else if(k.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.makeMove('R');
        }
        this.repaint();
    }

    private void cycleMove() {

        game.makeMove('D');
        this.repaint();

        LeaderBoard board = new LeaderBoard();
        if(game.getState() == 0) {
            board.gameOver(game.getScore());
        }
    }

    public void setTimer(boolean timerStarted) {
        if(timerStarted) {
            this.timer.start();
        }
        else if(!timerStarted) {
            this.timer.stop();
        }
    }
}
