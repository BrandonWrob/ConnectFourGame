import javax.swing.*;

public class UserInputDialogue {

    public static int piecesToWin = 0;
    public static String player1 = null;
    public static String player2 = null;

    public static String[] collectUserInput() {
        // Create a dialog box
        JFrame frame = new JFrame("User Input");

        // Prompt user for Player 1 name
        String player1 = JOptionPane.showInputDialog(frame, "Enter Player 1 name:");

        String player2;
        boolean isValidInput;

        do {
            // Prompt user for Player 2 name
            player2 = JOptionPane.showInputDialog(frame, "Enter Player 2 name:");
            isValidInput = !player2.equalsIgnoreCase(player1);
            // User cannot give same name as player 1, if they do this prompts them to change it
            if (!isValidInput) {
                JOptionPane.showMessageDialog(frame, "Player 2 name must be different from Player 1.");
            }
        } while (!isValidInput);

        int piecesToWin = 0;
        isValidInput = false;

        do {
            try {
                // Prompt user for number of pieces to connect to win
                piecesToWin = Integer.parseInt(JOptionPane.showInputDialog(frame, "Number of pieces necessary to win:"));
                isValidInput = true;
            } catch (NumberFormatException e) {
                isValidInput = false;
            }
        } while (!isValidInput);

        // Return the entered values as an array
        return new String[]{player1, player2, String.valueOf(piecesToWin)};
    }

    public static void main(String[] args) {
        // Call the method to collect input and retrieve the values
        String[] inputValues = collectUserInput();
    }
}
