import javax.swing.*;

/**
 * Class that creates a GUI that ask users for their name and
 * how many pieces they want to be the wincondition
 * @author Brandon Wroblewski
 */
public class UserInputDialogue {

    /**
     * method collects users input (reprompts if they give
     * invalid information) and returns a string of the values
     * @return users input in a string array
     */
    public static String[] collectUserInput() {
        JFrame frame = new JFrame("User Input");

        // Prompt user for Player 1 name and confirms it meets criteria
        String player1;
        do {
            player1 = JOptionPane.showInputDialog(frame, "Enter Player 1 name:");
            // confirms name is not blank
            if (player1 == null || player1.trim().isEmpty()) {
                // gives pop-up prompt if invalid input
                JOptionPane.showMessageDialog(frame, "Name cannot be blank.");
            }
        } while (player1 == null || player1.trim().isEmpty());

        // Prompt user for Player 2 name and confirms it meets criteria
        String player2;
        boolean isValidInput = false;
        do {
            player2 = JOptionPane.showInputDialog(frame, "Enter Player 2 name:");
            // confirms name is unique from player 1
            if (player2.equalsIgnoreCase(player1)) {
                // gives pop-up prompt if invalid input
                JOptionPane.showMessageDialog(frame, 
                    "Player 2 name must be different from Player 1.");
            }
            // confirms name is not empty
            else if (player2 == null || player2.trim().isEmpty()) {
                // gives pop-up prompt if invalid input
                JOptionPane.showMessageDialog(frame, "Name cannot be blank.");
            }
            else {
                isValidInput = true;
            }
        } while (!isValidInput);

        // Prompt user for number of pieces to connect to win and confirms
        // it is in a valid range
        final int minimumNumberNecessaryToWin = 4;
        final int maximumNumberNecessaryToWin = 10;
        int piecesToWin = 0;
        do {
            try {
                piecesToWin = Integer.parseInt(JOptionPane.showInputDialog(frame, 
                    "Number of pieces necessary to win (between 4-10 and a whole number):"));
                isValidInput = piecesToWin >= minimumNumberNecessaryToWin && piecesToWin 
                    <= maximumNumberNecessaryToWin;
                if (!isValidInput) {
                    // gives pop-up prompt if invalid input
                    JOptionPane.showMessageDialog(frame, 
                        "Please enter a value in the valid range of 4-10. ");
                }
            } catch (NumberFormatException e) {
                isValidInput = false;
            }
        } while (!isValidInput);

        // Return the entered values as an array
        return new String[]{player1, player2, String.valueOf(piecesToWin)};
    }
}
