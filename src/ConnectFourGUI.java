import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class that creates a GUI of an interactive variation of the
 * Connect Four Game
 * @author Brandon Wroblewski
 */
public class ConnectFourGUI extends JFrame implements ActionListener {

    /** creates a private instance variable for headerPanel of GUI*/
    private JPanel headerPanel;

    /** creates a private instance variable for an arrayPanel of GUI */
    private JPanel arrayPanel;

    /** creates a private instance variable for the color red */
    private static final Color COLOR_RED = Color.RED;

    /** creates a private instance variable for th color yelow */
    private static final Color COLOR_YELLOW = Color.YELLOW;

    /** creates a private instance variable for rows in array */
    public int rows;

    /** creates a private instance variable for columns in array */
    public int columns;

    /** creates a private instance variable for player 1's name */
    private String player1;

    /** creates a private instance variable for player 2's name */
    private String player2;

    /** creates a private 2D JLabel instance array of labels */
    private JLabel[][] labels;

    /** creates a private instance 2D string array */
    private String[][] labelStringArray;

    /** creates a private instance variable connected to UserStats class */
    private UserStats userStats;

    /** creates a private instance string for current player */
    private String currentPlayer;

    /** creates a private instance int for current streak */
    private int currentStreak;

    /** creates a private instance int for incrementPlayerPieces */
    private int incrementPlayerPieces;

    /** create a private instance int for pieces necessary to win */
    private int piecesToWin;

    /** creaates a private instance int of player 1's total wins */
    private int player1Win = 0;

    /** creates a private instance int of player 2's wins */
    private int player2Win = 0;

    /** creates a private instance string of winning message */
    private String winner;

    /** creates a private instance boolean of whether game ended */
    private boolean gameEnded = false;

    /**
     * Method which tells program where to start
     * It collects user input, initializes the games settings, and starts
     * the Connect Four Game
     * @param args command line argument
     */
    public static void main(String[] args) {
        // collects users input and stores it in variables
        String[] userInput = UserInputDialogue.collectUserInput();
        String player1 = userInput[0];
        String player2 = userInput[1];
        String piecesToWinString = userInput[2];
        int piecesToWin = Integer.parseInt(piecesToWinString);
        int rows = piecesToWin * 2 + 1;
        int columns = piecesToWin * 2;
        // starts a modified connect four game based on users input
        new ConnectFourGUI(rows, columns, player1, player2, piecesToWin);
    }

    /**
     * Constructor of ConnectFourGUI class which takes the parameter rows, columns, 
     * player1, player2, and pieces to win and creates a gameboard with then
     * @param rows number of rows in array
     * @param columns number of columns in array
     * @param player1 player 1's name
     * @param player2 player 2's name
     * @param piecesToWin Connected pieces required to win the game
     */
    public ConnectFourGUI(int rows, int columns, String player1, String player2, int piecesToWin) {
        // sets up initial game parameters
        this.rows = rows;
        this.columns = columns;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1 + "'s turn (X):";
        this.piecesToWin = piecesToWin;
        // creates the main JFrame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final int widthOfGame = 1000;
        final int heightOfGame = 500;
        frame.setSize(widthOfGame, heightOfGame);
        // creates the main JPanel and set its layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        // creates the header panel with player stats
        headerPanel = new JPanel(new GridLayout(1, 3));
        String[] columnNames = { currentPlayer, "Max # Connected Pieces: 0", 
            "Total Pieces Placed: 0" };
        for (int i = 0; i < 3; i++) {
            JLabel headerLabel = new JLabel(columnNames[i]);
            headerPanel.add(headerLabel);
        }
        panel.add(headerPanel, BorderLayout.NORTH);
        // creates the game board panel with labels and buttons
        arrayPanel = new JPanel(new GridLayout(rows, columns));
        labels = new JLabel[rows][columns];
        labelStringArray = new String[rows - 1][columns];
        for (int i = 0; i < labelStringArray.length; i++) {
            for (int j = 0; j < labelStringArray[i].length; j++) {
                labelStringArray[i][j] = "E";
            }
        }
        // creates the game board with a label array and a button row
        // at the bottom
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
        // adds the game board panel to main panel
        panel.add(arrayPanel, BorderLayout.CENTER);
        // sets the main panel as the content pane of frame
        frame.setContentPane(panel);
        // makes the frame visible
        frame.setVisible(true);
        // centers the frame on the screen
        frame.setLocationRelativeTo(null);
        // creates user stats object
        userStats = new UserStats(rows - 1, columns);
    }

    /**
     * Handles the action performed when the user clicks a button
     * in the ConnectFourGUI and implements the logic for updating
     * the board and deciding who won
     * @param e represents when a button is clicked in game
     */
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (gameEnded) {
            // makes it so if game is over user cannot click,
            // if they do nothing happens till they reset game
            // and reprompts with score board/ restart option
            new EndScreen(winner, player1Win, player2Win, this);
            return;
        }
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            String buttonText = button.getText();
            if (currentPlayer.equals(player1 + "'s turn (X):")) {
                // get column selected by the player
                int columnSelected = Integer.parseInt(buttonText);
                boolean columnFound = false;
                // loop through rows to find bottom-most empty spot in array
                for (int row = rows - 2; row >= 0; row--) {
                    // calculatres the index of the label for arrayPanel
                    int labelIndex = row * columns + columnSelected - 1;
                    Component component = arrayPanel.getComponent(labelIndex);
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;
                        // checks that label is empty
                        if (label.getText().equals("")) {
                            label.setHorizontalAlignment(SwingConstants.CENTER);
                            label.setVerticalAlignment(SwingConstants.CENTER);
                            Font labelFont = label.getFont();
                            final int fontSize = 40;
                            label.setFont(new Font(labelFont.getName(), Font.BOLD, fontSize));
                            label.setText("X");
                            columnFound = true;
                            // updates empty label with "X" value
                            labelStringArray[row][columnSelected - 1] = "X";
                            break;
                        }
                    }
                }
                // If column is not found then it shows a "Column is full" dialogue
                // and will let user select again
                if (!columnFound) {
                    JOptionPane.showMessageDialog(this, "Column is full. Choose another column.");
                    return;
                }
                // switches to player 2
                switchUser(player2);
            } else {
                // get column selected by the player
                int columnSelected = Integer.parseInt(buttonText);
                boolean columnFound = false;
                // loop through rows to find a empty row in column
                for (int row = rows - 2; row >= 0; row--) {
                    int labelIndex = row * columns + columnSelected - 1;
                    Component component = arrayPanel.getComponent(labelIndex);
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;
                        if (label.getText().equals("")) {
                            label.setHorizontalAlignment(SwingConstants.CENTER);
                            label.setVerticalAlignment(SwingConstants.CENTER);
                            Font labelFont = label.getFont();
                            final int fontSize = 40;
                            label.setFont(new Font(labelFont.getName(), Font.BOLD, fontSize));
                            label.setText("O");
                            columnFound = true;
                            // if empty row found in column then it sets value to "O"
                            labelStringArray[row][columnSelected - 1] = "O";
                            break;
                        }
                    }
                }
                // if column was not found then it gives a "Column is full" popup
                // and lets the user select again
                if (!columnFound) {
                    JOptionPane.showMessageDialog(this, "Column is full. Choose another column.");
                    return;
                }
                // switches to player 1
                switchUser(player1);
            }
            // updates the user stats and checks for a win or tie
            userStats.updateLabelStringArray(labelStringArray);
            if (isBoardFull()) {
                winner = "Tie";
                new EndScreen(winner, player1Win, player2Win, this);
                gameEnded = true;
            } else if (userStats.getPlayer1Streak() >= piecesToWin) {
                player1Win++;
                winner = player1 + " Won!";
                gameEnded = true;
                new EndScreen(winner, player1Win, player2Win, this);
            } else if (userStats.getPlayer2Streak() >= piecesToWin) {
                player2Win++;
                winner = player2 + " Won!";
                gameEnded = true;
                new EndScreen(winner, player1Win, player2Win, this);
            }
        }
    }

    /**
     * The method switches the current player in game to who's
     * turn it is and updates the current players values to the
     * header of the game
     * @param nextPlayer name of next player
     */
    private void switchUser(String nextPlayer) {
        // saves the current player in userStats object
        userStats.setCurrentPlayer(currentPlayer);
        // updates the current player
        currentPlayer = nextPlayer + "'s turn (";
        currentPlayer += (nextPlayer.equals(player1) ? "X" : "O") + "):";
        // get the current player's symbol
        int length = currentPlayer.length();
        String symbol =  currentPlayer.substring(length - 3, length - 2);
        // update the current streak and increment player pieces based
        // on symbol
        if (symbol.equals("X")) {
            currentStreak = userStats.getPlayer1Streak();
            incrementPlayerPieces = userStats.getPlayer1Pieces();
        } else {
            currentStreak = userStats.getPlayer2Streak();
            incrementPlayerPieces = userStats.getPlayer2Pieces();
        }
        // updates the header labels in headerPanel with next players
        // information
        JLabel headerLabel = (JLabel) headerPanel.getComponent(0);
        JLabel headerLabelStreak = (JLabel) headerPanel.getComponent(1);
        JLabel headerLabelCount = (JLabel) headerPanel.getComponent(2);
        headerLabel.setText(currentPlayer);
        headerLabelStreak.setText("Max # Connected Pieces: " + currentStreak);
        headerLabelCount.setText("Total Pieces Placed: " + incrementPlayerPieces);
       
    }
   

    /**
     * This method checks if game board is full
     * @return true if full, false otherwise
     */
    private boolean isBoardFull() {
        // iterates over each cell of board to find a empty value
        for (int row = 0; row < rows - 1; row++) {
            for (int col = 0; col < columns; col++) {
                if (labelStringArray[row][col].equals("E")) {
                    // if one is found it returns false
                    return false;
                }
            }
        }
        // if none is found it returns true
        return true;
    }

    /**
     * this method returns name of player 1
     * @return player 1's name
     */
    public String getPlayer1Name() {
        return player1;
    }
    
    /**
     * this method returns name of player 2
     * @return player 2's name
     */
    public String getPlayer2Name() {
        return player2;
    }
    
    /**
     * this method returns number of wins of player 1
     * @return player 1's win count
     */
    public int getPlayer1WinCount() {
        return player1Win;
    }
    
    /**
     * this method returns number of wins of player 2
     * @return player 2's win count
     */
    public int getPlayer2WinCount() {
        return player2Win;
    }
    
    /**
     * This method resets the game board
     */
    public void resetGame() {
        // Reset the game state
        currentPlayer = player1 + "'s turn (X):";
        labelStringArray = new String[rows - 1][columns];
        for (int i = 0; i < labelStringArray.length; i++) {
            for (int j = 0; j < labelStringArray[i].length; j++) {
                labelStringArray[i][j] = "E";
            }
        }
        // resets text of all labels in GUI
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < columns; j++) {
                labels[i][j].setText("");
            }
        }
        // resets the users stats
        userStats = new UserStats(rows - 1, columns);
        // resets the header panel with current players turn/ state
        JLabel headerLabel = (JLabel) headerPanel.getComponent(0);
        JLabel headerLabelStreak = (JLabel) headerPanel.getComponent(1);
        JLabel headerLabelCount = (JLabel) headerPanel.getComponent(2);
        headerLabel.setText(currentPlayer);
        headerLabelStreak.setText("Max # Connected Pieces: 0");
        headerLabelCount.setText("Total Pieces Placed: 0");
        gameEnded = false;
    }


}
