import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConnectFourGUI extends JFrame implements ActionListener {

    private JPanel headerPanel;
    private JPanel arrayPanel;
    private static final Color COLOR_RED = Color.RED;
    private static final Color COLOR_YELLOW = Color.YELLOW;
    public static int rows;
    public static int columns;
    private String player1;
    private String player2;
    private JLabel[][] labels;
    private String[][] labelStringArray;
    private WinCondition winCondition;
    private UserStats userStats;
    private boolean gameOver;
    private String currentPlayer;
    private int currentStreak;
    private int incrementPlayerPieces;

    public static void main(String[] args) {
        String[] userInput = UserInputDialogue.collectUserInput();
        String player1 = userInput[0];
        String player2 = userInput[1];
        String piecesToWinString = userInput[2];
        int piecesToWin = Integer.parseInt(piecesToWinString);
        int rows = piecesToWin * 2 + 1;
        int columns = piecesToWin * 2;
        new ConnectFourGUI(rows, columns, player1, player2);
    }

    public ConnectFourGUI(int rows, int columns, String player1, String player2) {
        this.rows = rows;
        this.columns = columns;
        this.player1 = player1;
        this.player2 = player2;
        this.gameOver = false;
        this.currentPlayer = player1 + "'s turn (X):";

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        headerPanel = new JPanel(new GridLayout(1, 3));
        String[] columnNames = { currentPlayer, "Max # Connected Pieces: 0", "Total Pieces Placed: 0" };
        for (int i = 0; i < 3; i++) {
            JLabel headerLabel = new JLabel(columnNames[i]);
            headerPanel.add(headerLabel);
        }
        panel.add(headerPanel, BorderLayout.NORTH);

        arrayPanel = new JPanel(new GridLayout(rows, columns));
        labels = new JLabel[rows][columns]; 
        labelStringArray = new String[rows - 1][columns]; 
        for (int i = 0; i < labelStringArray.length; i++) {
            for (int j = 0; j < labelStringArray[i].length; j++) {
                labelStringArray[i][j] = "E";
            }
        }

        for (int i = 0; i < rows; i++) {
            if (i < columns) {
                for (int j = 0; j < columns; j++) {
                    JLabel label = new JLabel("");
                    label.setOpaque(true);
                    if ((i + j) % 2 == 0) {
                        label.setBackground(COLOR_RED);
                    } else {
                        label.setBackground(COLOR_YELLOW);
                    }
                    arrayPanel.add(label);
                    labels[i][j] = label;
                }
            } else {
                for (int j = 0; j < columns; j++) {
                    int dropNumber = j + 1;
                    JButton button = new JButton(String.valueOf(dropNumber));
                    arrayPanel.add(button);
                    button.addActionListener(this);
                }
            }
        }

        panel.add(arrayPanel, BorderLayout.CENTER);
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        winCondition = new WinCondition(rows - 1, columns);
        userStats = new UserStats(rows - 1, columns);
    }

    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            System.out.println("Game Over");
            System.exit(0);
        }
        Object source = e.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            String buttonText = button.getText();
            JLabel headerLabel = (JLabel) headerPanel.getComponent(0);
            if (currentPlayer.equals(player1 + "'s turn (X):")) {
                int columnSelected = Integer.parseInt(buttonText);
                boolean columnFound = false;
                for (int row = rows - 2; row >= 0; row--) {
                    int labelIndex = row * columns + columnSelected - 1;
                    Component component = arrayPanel.getComponent(labelIndex);
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;
                        if (label.getText().equals("")) {
                            label.setHorizontalAlignment(SwingConstants.CENTER);
                            label.setVerticalAlignment(SwingConstants.CENTER);
                            Font labelFont = label.getFont();
                            label.setFont(new Font(labelFont.getName(), Font.BOLD, 40));
                            label.setText("X");
                            columnFound = true;
                            labelStringArray[row][columnSelected - 1] = "X";
                            break;
                        }
                    }
                }
                if (!columnFound) {
                    JOptionPane.showMessageDialog(this, "Column is full. Choose another column.");
                    return;
                }
                switchUser(player2);
            } else {
                int columnSelected = Integer.parseInt(buttonText);
                boolean columnFound = false;
                for (int row = rows - 2; row >= 0; row--) {
                    int labelIndex = row * columns + columnSelected - 1;
                    Component component = arrayPanel.getComponent(labelIndex);
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;
                        if (label.getText().equals("")) {
                            label.setHorizontalAlignment(SwingConstants.CENTER);
                            label.setVerticalAlignment(SwingConstants.CENTER);
                            Font labelFont = label.getFont();
                            label.setFont(new Font(labelFont.getName(), Font.BOLD, 40));
                            label.setText("O");
                            columnFound = true;
                            labelStringArray[row][columnSelected - 1] = "O";
                            break;
                        }
                    }
                }
                if (!columnFound) {
                    JOptionPane.showMessageDialog(this, "Column is full. Choose another column.");
                    return;
                }
                switchUser(player1);
            }

            winCondition.updateLabelStringArray(labelStringArray);
            userStats.updateLabelStringArray(labelStringArray);

            if (isBoardFull()) {
                gameOver = true;
            }
        }
    }

    private void switchUser(String nextPlayer) {
        userStats.setCurrentPlayer(currentPlayer);
        currentPlayer = nextPlayer + "'s turn (";
        currentPlayer += (nextPlayer.equals(player1) ? "X" : "O") + "):";
        int length = currentPlayer.length();
        String symbol =  currentPlayer.substring(length - 3, length - 2);
        if (symbol.equals("X")) {
            currentStreak = userStats.getPlayer1Streak();
            incrementPlayerPieces = userStats.getPlayer1Pieces();
        } else {
            currentStreak = userStats.getPlayer2Streak();
            incrementPlayerPieces = userStats.getPlayer2Pieces();
        }
        JLabel headerLabel = (JLabel) headerPanel.getComponent(0);
        JLabel headerLabelStreak = (JLabel) headerPanel.getComponent(1);
        JLabel headerLabelCount = (JLabel) headerPanel.getComponent(2);
        headerLabel.setText(currentPlayer);
        headerLabelStreak.setText("Max # Connected Pieces: " + currentStreak);
        headerLabelCount.setText("Total Pieces Placed: " + incrementPlayerPieces);
        
    }
    

    private boolean isBoardFull() {
        for (int row = 0; row < rows - 1; row++) {
            for (int col = 0; col < columns; col++) {
                if (labelStringArray[row][col].equals("E")) {
                    return false;
                }
            }
        }
        return true;
    }

}
