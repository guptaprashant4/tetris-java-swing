/*
 * This window controls the game window for the Tetris Game.
 * Prashant Gupta
 * 26 April 2024
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TetrisWindow extends JFrame implements ActionListener {

    private TetrisGame game;
    private TetrisDisplay display;
    private int win_width = 640;
    private int win_height = 800;
    private int game_rows = 20;
    private int game_cols = 12;

    public TetrisWindow() {

        this.setTitle("My Tetris Game       Prashant Gupta");
        this.setSize(win_width, win_height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new TetrisGame(game_rows, game_cols);
        display = new TetrisDisplay(game);
        this.add(display);

        initMenu();
        
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        LeaderBoard scoreBoard = new LeaderBoard();

        JMenuItem action = (JMenuItem) ae.getSource();
        switch (action.getText()) {
            case "New Game":
                game.newGame();
                display.setTimer(true);
                break;
            case "Save Game": 
                display.setTimer(false);
                game.saveToFile();
                break;
            case "Retrieve Saved Game":
                display.setTimer(false);
                game.retrieveFromFile();
                display.repaint();
                display.setTimer(true);
                break;
            case "Quit":
                System.exit(0);
                break;
            case "Show Leaderboard":
                display.setTimer(false);
                scoreBoard.setVisible(true);
                break;
            case "Clear Leaderboard":
                scoreBoard.clearBoard();
                break;
            case "Change Dimensions":
                changeDimensions();
                break;
        }
    }

    private void initMenu() {

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu leaderBoardMenu = new JMenu("Leaderboard");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(this);

        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(this);

        JMenuItem retrieve = new JMenuItem("Retrieve Saved Game");
        retrieve.addActionListener(this);

        JMenuItem dimension = new JMenuItem("Change Dimensions");
        dimension.addActionListener(this);

        JMenuItem close = new JMenuItem("Quit");
        close.addActionListener(this);

        JMenuItem showBoard = new JMenuItem("Show Leaderboard");
        showBoard.addActionListener(this);

        JMenuItem clear = new JMenuItem("Clear Leaderboard");
        clear.addActionListener(this);

        gameMenu.add(newGame);
        gameMenu.addSeparator();
        gameMenu.add(saveGame);
        gameMenu.addSeparator();
        gameMenu.add(retrieve);
        gameMenu.addSeparator();
        gameMenu.add(dimension);
        gameMenu.addSeparator();
        gameMenu.add(close);

        leaderBoardMenu.add(showBoard);
        leaderBoardMenu.addSeparator();
        leaderBoardMenu.add(clear);

        menuBar.add(gameMenu);
        menuBar.add(leaderBoardMenu);  
        
        this.setJMenuBar(menuBar);
    }

    private void changeDimensions() {

        String rows = JOptionPane.showInputDialog(this, 
            "Enter No. Of Rows:");
        String cols = JOptionPane.showInputDialog(this, 
            "Enter No. of Colums:");
        
        try {
            this.game_rows = Integer.parseInt(rows);
            this.game_cols = Integer.parseInt(cols);
        }catch(NumberFormatException e) {
            changeDimensions();
        }


        this.remove(display);

        game = new TetrisGame(game_rows, game_cols);
        display = new TetrisDisplay(game);

        this.add(display);
        this.validate();
        this.repaint();
    }

    public static void main(String[] args) {
        
        TetrisWindow win = new TetrisWindow();
    }
    
}