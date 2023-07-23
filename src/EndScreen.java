import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EndScreen extends JFrame {

    public EndScreen(String winner, int player1Win, int player2Win, ConnectFourGUI game) {
        setTitle("Game Over");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel winnerLabel = new JLabel(winner);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(winnerLabel, BorderLayout.NORTH);

        JLabel player1WinsLabel = new JLabel(game.getPlayer1Name() + " Wins: " + player1Win);
        JLabel player2WinsLabel = new JLabel(game.getPlayer2Name() + " Wins: " + player2Win);

        JPanel winsPanel = new JPanel();
        winsPanel.setLayout(new GridLayout(2, 1));
        winsPanel.add(player1WinsLabel);
        winsPanel.add(player2WinsLabel);
        panel.add(winsPanel, BorderLayout.CENTER);

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
