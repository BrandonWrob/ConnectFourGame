import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class that creates a GUI which shows an endscreen with winner
 * and a scoreboard, offers option for user to reset game
 * @author Brandon Wroblewski
 */
public class EndScreen extends JFrame {

    /**
     * Constructor with parameters of the winner, and total wins
     * the players have from previous game, and the connectFourGUI
     * object, this creates a gui that shows winner, scoreboard,
     * and lets user replay the game if they choose to
     * @param winner person who won the gane
     * @param player1Win how many times player 1 won 
     * @param player2Win how many times player 2 won
     * @param game ConnectFourGuI object
     */
    public EndScreen(String winner, int player1Win, int player2Win, ConnectFourGUI game) {
        final int widthOfGUI = 300;
        final int heightOfGUI = 200;
        final int fontSize = 20;
        // creates GUI layout
        setTitle("Game Over");
        setSize(widthOfGUI, heightOfGUI);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // displays winner
        JLabel winnerLabel = new JLabel(winner);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(winnerLabel, BorderLayout.NORTH);

        // displays scoreboard
        JLabel player1WinsLabel = new JLabel(game.getPlayer1Name() + " Wins: " + player1Win);
        JLabel player2WinsLabel = new JLabel(game.getPlayer2Name() + " Wins: " + player2Win);
        JPanel winsPanel = new JPanel();
        winsPanel.setLayout(new GridLayout(2, 1));
        winsPanel.add(player1WinsLabel);
        winsPanel.add(player2WinsLabel);
        panel.add(winsPanel, BorderLayout.CENTER);

        // adds feature that lets user click replay to restart game
        JButton replayButton = new JButton("Replay");
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.resetGame();
                dispose(); // Close the EndScreen after clicking the replay button
            }
        });
        panel.add(replayButton, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);
    }
}
