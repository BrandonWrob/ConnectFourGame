import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Interactive GUI of the ConnectFour game which provides a graphical
 * user interface for the game which allows users to drop pieces until
 * they win the game, or the board gets full, it displays the game,
 * number of pieces the user placed, and the maximum number of connected
 * pieces the user has
 * 
 * @author Brandon Wroblewski
 */
public class ConnectFourGUI extends JFrame implements ActionListener {

    /** private instance variable of the headerPanel label */
    private JPanel headerPanel;

    /** private instance variable of the arrayPanel label */
    private JPanel arrayPanel;

    /** private instance variable of the color red*/
    private static final Color COLOR_RED = Color.RED;

    /** private instance variable of the color yellow */
    private static final Color COLOR_YELLOW = Color.YELLOW;

    /** private instance variable of the number of rows */
    public static int rows;

    /** private instance variable of the number of columns */
    public static int columns;

    /** private instance variable which represents player one's name */
    private String player1;

    /** private instance variable which represents player two's name */
    private String player2;

    /** 
     * private instance JLabel array that stores labels representing the 
     * board 
     * */
    private JLabel[][] labels;

    /** private instance string array which represents the board */
    private String[][] labelStringArray;

    /** private instance variable which calls the winCondition class */
    private WinCondition winCondition;

    /** flag instance which indicates if the game is over */
    private boolean gameOver;

    /** private instance variable which represents name of current player */
    private String currentPlayer;

    /**
     * The main method of the ConnectFourGUI class.
     * It collects user input and runs the ConnectFourGUI constructor
     * based on users input
     * 
     * @param args Command line arguments 
     */
    public static void main(String[] args) {
        // Collect user input
        String[] userInput = UserInputDialogue.collectUserInput();
        String player1 = userInput[0];
        String player2 = userInput[1];
        String piecesToWinString = userInput[2];
        int piecesToWin = Integer.parseInt(piecesToWinString);

        // Calculate the number of rows and columns on the game board
        int rows = piecesToWin * 2 + 1;
        int columns = piecesToWin * 2;

        // Create an instance of the ConnectFourGUI
        new ConnectFourGUI(rows, columns, player1, player2);
    }

    /**
     * Constructor for the ConnectFourGUI class.
     * It initializes the game board and sets up the GUI.
     * 
     * @param rows Number of rows on the game board
     * @param columns Number of columns on the game board
     * @param player1 Name of player 1
     * @param player2 Name of player 2
     */
    public ConnectFourGUI(int rows, int columns, String player1, String player2) {
        //sets the parameter values to the current instance variables
        this.rows = rows;
        this.columns = columns;
        this.player1 = player1;
        this.player2 = player2;
        this.gameOver = false;
        this.currentPlayer = player1 + "'s turn (X):";

        // Creates the main frame and gives it a set size
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create the header panel with labels that displays players turn,
        //pieces placed, and max number of collected pieces
        headerPanel = new JPanel(new GridLayout(1, 3));
        String[] columnNames = { currentPlayer, "Pieces Placed ______", "Max # of Connected Pieces _____" };

        // Add the header labels to the header panel
        for (int i = 0; i < 3; i++) {
            JLabel headerLabel = new JLabel(columnNames[i]);
            headerPanel.add(headerLabel);
        }
        panel.add(headerPanel, BorderLayout.NORTH);

        // Create the game board panels
        arrayPanel = new JPanel(new GridLayout(rows, columns));
        labels = new JLabel[rows][columns]; 
        // removes last row because it should be buttons, not part of
        // the game grid
        labelStringArray = new String[rows - 1][columns]; 

        // Initialize the labelStringArray with "E" for all spots
        for (int i = 0; i < labelStringArray.length; i++) {
            for (int j = 0; j < labelStringArray[i].length; j++) {
                labelStringArray[i][j] = "E";
            }
        }

        // Add labels and buttons to the game board panel
        for (int i = 0; i < rows; i++) {
            if (i < columns) {
                // Create and add labels for the game board
                for (int j = 0; j < columns; j++) {
                    JLabel label = new JLabel("");
                    label.setOpaque(true);
                    if ((i + j) % 2 == 0) {
                        label.setBackground(COLOR_RED);
                    } else {
                        label.setBackground(COLOR_YELLOW);
                    }
                    arrayPanel.add(label);
                    // stores label in the array
                    labels[i][j] = label; 
                }
            } else {
                // Create and add buttons for column selection
                for (int j = 0; j < columns; j++) {
                    int dropNumber = j + 1;
                    JButton button = new JButton(String.valueOf(dropNumber));
                    arrayPanel.add(button);
                    //adds action listener so program will know when they
                    //are clicked
                    button.addActionListener(this);
                }
            }
        }
        // sets panel at center of frame and makes it visible
        panel.add(arrayPanel, BorderLayout.CENTER);
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        // Create an instance of WinCondition
        winCondition = new WinCondition(rows - 1, columns);
    }

    /**
     * ActionListener implementation for button click events.
     * It handles the button click events and updates the game board accordingly.
     * 
     * @param e ActionEvent representing the button click event
     */
    public void actionPerformed(ActionEvent e) {
        // if gameOver boolean is true it ends the game
        if (gameOver) {
            System.out.println("Game Over");
            System.exit(0);
        }
        // checks if source of event is instance of JButton, i it is the it
        // gets the text and stores it in a string buttoText
        Object source = e.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            String buttonText = button.getText();
            JLabel headerLabel = (JLabel) headerPanel.getComponent(0);
            // checks if it is player 1's turn
            if (currentPlayer.equals(player1 + "'s turn (X):")) {
                // stores column user selects in an int
                int columnSelected = Integer.parseInt(buttonText);
                // creates boolean to see if user selected valid column
                boolean columnFound = false;
                // Find the appropriate row to place the piece in the selected column
                for (int row = rows - 2; row >= 0; row--) {
                    int labelIndex = row * columns + columnSelected - 1;
                    Component component = arrayPanel.getComponent(labelIndex);
                    // iterates the JLabel array looking for a available drop spot
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;
                        if (label.getText().equals("")) {
                            // Update the label to display the player's piece ("X")
                            label.setHorizontalAlignment(SwingConstants.CENTER);
                            label.setVerticalAlignment(SwingConstants.CENTER);
                            Font labelFont = label.getFont();
                            label.setFont(new Font(labelFont.getName(), Font.BOLD, 40));
                            label.setText("X");
                            // updates boolean to say true so it can move forward
                            columnFound = true;

                            // Update labelStringArray with "X" in the same position
                            labelStringArray[row][columnSelected - 1] = "X";
                            break;
                        }
                    }
                }
                // if column is not found it will return and display a prompt
                if (!columnFound) {
                    JOptionPane.showMessageDialog(this, "Column is full. Choose another column.");
                    return;
                }
                // switches to player 2
                switchUser(player2);
            } else {
                // stores column selected by user in an int
                int columnSelected = Integer.parseInt(buttonText);
                // creates boolean to see if valid column was selected
                boolean columnFound = false;
                // Find the appropriate row to place the piece in the selected column
                for (int row = rows - 2; row >= 0; row--) {
                    int labelIndex = row * columns + columnSelected - 1;
                    Component component = arrayPanel.getComponent(labelIndex);
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;
                        if (label.getText().equals("")) {
                            // Update the label to display the player's piece ("O")
                            label.setHorizontalAlignment(SwingConstants.CENTER);
                            label.setVerticalAlignment(SwingConstants.CENTER);
                            Font labelFont = label.getFont();
                            label.setFont(new Font(labelFont.getName(), Font.BOLD, 40));
                            label.setText("O");
                            // sets boolean to true so code can move on
                            columnFound = true;
                            // Update labelStringArray with "O" in the same position
                            labelStringArray[row][columnSelected - 1] = "O";
                            break;
                        }
                    }
                }
                // if invalid column it will display a prompt and return
                if (!columnFound) {
                    JOptionPane.showMessageDialog(this, "Column is full. Choose another column.");
                    return;
                }
                // switches to player 1
                switchUser(player1);
            }

            // Update the replicaLabelStringArray in WinCondition
            winCondition.updateLabelStringArray(labelStringArray);
            // Call the win condition checking methods in WinCondition
            winCondition.printReplicaLabelStringArray();

            if (isBoardFull()) {
                gameOver = true;
            }
        }
    }

    /**
     * Switches the current user to the next user's turn.
     * 
     * @param nextPlayer Name of the next player
     */
    private void switchUser(String nextPlayer) {
        currentPlayer = nextPlayer + "'s turn (";
        currentPlayer += (nextPlayer.equals(player1) ? "X" : "O") + "):";
        JLabel headerLabel = (JLabel) headerPanel.getComponent(0);
        headerLabel.setText(currentPlayer);
    }

    /**
     * Checks if the game board is full.
     * 
     * @return true if the game board is full, false otherwise
     */
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
