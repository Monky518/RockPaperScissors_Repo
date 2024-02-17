import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        JFrame mainFrame = new Frame();
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel displayPanel = new JPanel();
        JPanel recordsPanel = new JPanel();
        final String[] playerMove = {""};
        final String[] computerMove = {""};
        final String[] previousPlayerMove = {""};
        int[] counters = new int[5]; // rock use, paper use, scissors use, player wins, computer wins
        JTextArea displayText = new JTextArea(20, 40);
        JTextArea recordsText = new JTextArea(20, 20);

        // TITLE
        JLabel titleLabel = new JLabel("Rock Paper Scissors");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        titlePanel.add(titleLabel);

        // BUTTONS
        JButton rockButton = new JButton("Rock");
        JButton paperButton = new JButton("Paper");
        JButton scissorsButton = new JButton("Scissors");
        JButton quitButton = new JButton("Quit");

        rockButton.setFont(new Font("SansSerif", Font.ITALIC, 20));
        paperButton.setFont(new Font("SansSerif", Font.ITALIC, 20));
        scissorsButton.setFont(new Font("SansSerif", Font.ITALIC, 20));
        quitButton.setFont(new Font("SansSerif", Font.ITALIC, 20));

        rockButton.addActionListener
        (
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    previousPlayerMove[0] = playerMove[0];
                    playerMove[0] = "Rock";
                    counters[0]++;
                    new Main().GameManager(counters, computerMove, playerMove, previousPlayerMove, displayText, recordsText);
                }
            }
        );
        paperButton.addActionListener
        (
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    previousPlayerMove[0] = playerMove[0];
                    playerMove[0] = "Paper";
                    counters[1]++;
                    new Main().GameManager(counters, computerMove, playerMove, previousPlayerMove, displayText, recordsText);
                }
            }
        );
        scissorsButton.addActionListener
        (
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    previousPlayerMove[0] = playerMove[0];
                    playerMove[0] = "Scissors";
                    counters[2]++;
                    new Main().GameManager(counters, computerMove, playerMove, previousPlayerMove, displayText, recordsText);
                }
            }
        );
        quitButton.addActionListener((ActionEvent ae) -> System.exit(0));

        buttonPanel.setLayout(new GridLayout(1, 4));
        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);
        buttonPanel.add(quitButton);

        // DISPLAY
        displayText.setEditable(false);
        displayText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        displayPanel.add(new JScrollPane(displayText));

        // RECORDS
        recordsText.setEditable(false);
        recordsText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        displayPanel.add(new JScrollPane(recordsText));

        // FRAME
        mainFrame.add(titlePanel, BorderLayout.NORTH);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.add(displayPanel, BorderLayout.CENTER);
        mainFrame.add(recordsPanel, BorderLayout.EAST);
        mainFrame.show(); // repeat because sometimes, it's stupid
    }

    public void GameManager(int counters[], String computerMove[], String playerMove[], String previousPlayerMove[], JTextArea displayText, JTextArea recordsText)
    {
        // COMPUTER
        int d100Roll = new Random().nextInt(100);
        if (counters[3] == 0 && counters[4] == 0)
            d100Roll = 90; // forced random at the beginning

        if (d100Roll < 24) // most user plays
        {
            if (counters[0] >= counters[1] || counters[0] > counters[2])
            {
                // rock >= paper OR rock > scissors
                computerMove[0] = "Paper";
            }
            else if (counters[1] >= counters[2] || counters[1] > counters[0])
            {
                // paper >= scissors OR paper > rock
                computerMove[0] = "Scissors";
            }
            else if (counters[2] >= counters[0] || counters[2] > counters[1])
            {
                // scissors >= rock OR scissors > paper
                computerMove[0] = "Rock";
            }
        }
        else if (d100Roll < 48) // least user plays
        {
            if (counters[0] <= counters[1] || counters[0] < counters[2])
            {
                // rock <= paper OR rock < scissors
                computerMove[0] = "Scissors";
            }
            else if (counters[1] <= counters[2] || counters[1] < counters[0])
            {
                // paper <= scissors OR paper < rock
                computerMove[0] = "Rock";
            }
            else if (counters[2] <= counters[0] || counters[2] < counters[1])
            {
                // scissors >= rock OR scissors > paper
                computerMove[0] = "Paper";
            }
        }
        else if (d100Roll < 72) // based off the previous player move
        {
            computerMove[0] = previousPlayerMove[0];
        }
        else if (d100Roll < 92) // random
        {
            int rand = new Random().nextInt(3);
            if (rand == 0)
                computerMove[0] = "Rock";
            else if (rand == 1)
                computerMove[0] = "Paper";
            else
                computerMove[0] = "Scissors";
            System.out.println("Random Random Num: " + rand);
        }
        else if (d100Roll < 100) // cheater
        {
            if (playerMove[0].equals("Rock"))
                computerMove[0] = "Paper";
            else if (playerMove[0].equals("Paper"))
                computerMove[0] = "Scissors";
            else
                computerMove[0] = "Rock";
        }
        else
            System.out.println("ERROR: Random d100 Roll out of Computer Strategy Chance");

        System.out.println("Computer Move: " + computerMove[0]);
        System.out.println("Random Num: " + d100Roll);

        // CHECK WINNER
        if (computerMove[0].equals(playerMove[0]))
            displayText.append("Game is a tie (" + computerMove[0] + ")\n");
        else if (computerMove[0].equals("Rock") && playerMove[0].equals("Paper"))
        {
            displayText.append("Paper covers rock (Player Wins)\n");
            counters[3]++;
        }
        else if (computerMove[0].equals("Rock") && playerMove[0].equals("Scissors"))
        {
            displayText.append("Rock breaks scissors (Computer Wins)\n");
            counters[4]++;
        }
        else if (computerMove[0].equals("Paper") && playerMove[0].equals("Scissors"))
        {
            displayText.append("Scissors cut paper (Player Wins)\n");
            counters[3]++;
        }
        else if (computerMove[0].equals("Paper") && playerMove[0].equals("Rock"))
        {
            displayText.append("Paper covers rock (Computer Wins)\n");
            counters[4]++;
        }
        else if (computerMove[0].equals("Scissors") && playerMove[0].equals("Rock"))
        {
            displayText.append("Rock breaks scissors (Player Wins)\n");
            counters[3]++;
        }
        else if (computerMove[0].equals("Scissors") && playerMove[0].equals("Paper"))
        {
            displayText.append("Scissors cut paper (Computer Wins)\n");
            counters[4]++;
        }
        else
            displayText.append("ERROR: Something is wrong\n");

        // UPDATE RECORDS
        recordsText.setText("Player Wins: " + counters[3] + "\nComputer Wins: " + counters[4] + "\n\nDEV NOTES:\nPlayer Rock Plays: " + counters[0] + "\nPlayer Paper Plays: " + counters[1] + "\nPlayer Scissors Plays: " + counters[2] + "\n");
    }
}
