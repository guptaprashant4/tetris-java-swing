/*
 * This class is responsible for keeping track of the highest scores
 * Prashant Gupta
 * 26 April 2024
 */

import java.io.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class LeaderBoard extends JPanel {
    
    private JFrame scoreWin;
    private File scoreFile = new File("./Leaderboard_scores.txt");
    private int win_height = 320;
    private int win_width = 400;
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Integer> scores = new ArrayList<Integer>();

    public LeaderBoard() {

        this.scoreWin = new JFrame();

        scoreWin.setTitle("Hall Of Fame");
        scoreWin.setSize(win_width, win_height);
        scoreWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        readScores();

        scoreWin.add(this);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {

        g.setColor(Color.white);
        g.fillRect(0, 0, win_width, win_height);

        int spacing = win_height / 20;
        int fontSize = 20;
        Font textFont = new Font("Arial", 0, fontSize);

        g.setColor(Color.BLACK);
        g.setFont(textFont);
        for(int index = 0; index < names.size(); index++) {
            g.drawString(names.get(index), 20, index * 30 + spacing);

            String score = Integer.toString(scores.get(index));
            g.drawString(score, win_width - 100, index * 30 + spacing);
        }
    }

    private void readScores() {

        try {
            
            Scanner scan1 = new Scanner(scoreFile);
            scan1.useDelimiter(":|\n");
            while(scan1.hasNext()) {

                String name = scan1.next();
                int score = Integer.parseInt(scan1.next());
                names.add(name);
                scores.add(score);
            }

            scan1.close();
        }
        catch(IOException ioe) {}

        sortScores();
    }

    private void sortScores() {

        ArrayList<Integer> scoresCopy = new ArrayList<Integer>();
        ArrayList<String> namesCopy = new ArrayList<String>();

        for(int score : scores) {
            scoresCopy.add(score);
            
        }

        for(String name : names) {
            namesCopy.add(name);
        }

        Collections.sort(scores, Collections.reverseOrder());
        for(int score : scores) {
            int index = scoresCopy.indexOf(score);
            names.set(scores.indexOf(score), namesCopy.get(index));
        }

    }

    public void clearBoard() {

        for(int scorePos = 0; scorePos < scores.size(); scorePos++) {
            scores.set(scorePos, 0);
        }
        try {
            FileWriter writer = new FileWriter(scoreFile);
            writer.write("");
            writer.close();
        }catch(IOException ioe) {}

        try{

            FileWriter writer = new FileWriter(scoreFile, true);
            for(int index = 0; index < names.size(); index++) {
                writer.append(names.get(index) + ":" + scores.get(index));
                if(index < names.size() - 1) {
                    writer.append("\n");
                }
            }
            writer.close();
        }catch(IOException ioe) {}

        readScores();
        scoreWin.setVisible(true);
    }

    private void overWriteScore(String name, int score) {

        try {
            File newFile = new File("./Leaderboard_scores.txt.tmp");

            FileReader reader = new FileReader(scoreFile);
            BufferedReader buffReader = new BufferedReader(reader);
            FileWriter writer = new FileWriter(newFile);

            String lowScore = "";
            for(int val : scores) {
                if(score > val) {
                    int indexLowScore = scores.indexOf(val);
                    lowScore = names.get(indexLowScore) + ":" + val;
                }
            }

            boolean hasContent = true;
            while(hasContent){

                String line = buffReader.readLine();
                if(line != null) {
                    if(line.equals(lowScore)){
                        writer.append(name + ":" + score + "\n");
                    }
                    else{
                        writer.append(line + "\n");
                    }
                }

                else{
                    hasContent = false;
                }
            }
            buffReader.close();
            writer.close();
            
            scoreFile.delete();
            newFile.renameTo(scoreFile);

        }catch(IOException ioe){}
    }

    public void writeScore(String name, int score) {

        if(scores.size() < 10) {

            try {
                FileWriter writer = new FileWriter(scoreFile, true);
                writer.append( "\n" + name + ":" + score);
                writer.close();
            }catch(IOException ioe){}
        }
        else {
            overWriteScore(name, score);
        }
    }

    public void gameOver(int currScore) {

        boolean qualifies = false;
        for(int score : scores) {
            if(currScore > score) {
                qualifies = true;
                break;
            }
        }

        String prompt = "Your score qualifies to be added to the Hall of Fame.";
        prompt += "\nDo you want to add your name?";
        if(qualifies) {
            int choice = JOptionPane.showConfirmDialog(null, 
                prompt, "Congrats!", JOptionPane.YES_NO_OPTION);

            if(choice == 0) {
                String name;
                name = JOptionPane.showInputDialog(null, 
                "Enter Your Name:", "Name", 0);

                writeScore(name, currScore);
                scores.clear();
                names.clear();
                readScores();
                scoreWin.setVisible(true);
            }
        }
    }

    public void setVisible( boolean visible) {
        this.repaint();
        scoreWin.setVisible(visible);
    }

    public int[] getAllScores() {
        
        int[] listOfScores = new int[scores.size()];
        for(int index = 0; index < scores.size(); index++) {
            listOfScores[index] = scores.get(index);
        }
        return listOfScores;
    }

    public String getName(int score) {

        int index = scores.indexOf(score);
        return names.get(index);
    }
}
