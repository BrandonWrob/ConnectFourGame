/**
 * Class stores userstats of how many pieces user placed
 * and their max number of consecutive pieces
 * @author Vishal Kommid
 * @author Brandon Wroblewski
 * @author Dolly Jani
 */
public class UserStats {


    /**  creates a 2D private instance string array */
    private String[][] replicaLabelStringArray;


    /** creates a private instance int for rows in array */
    private int rows;


    /** creates a private instance int for columns in array */
    private int columns;


    /**
     * creates a private instance int for number of pieces
     * placed by player 1
     */
    private int player1Pieces = 0;


    /**
     * creates a private instance int for number of pieces
     * placed by player 2
     */
    private int player2Pieces = 0;


    /**
     * creates a private instance int variable for number of
     * highest number of consecutive pieces placed by player 1
     */
    private int player1Streak = 0;


    /**
     * creates a private instance int variable for number of
     * highest number of consecutive pieces placed by player 2
     */
    private int player2Streak = 0;


    /**
     * creates a private instance string which represents current player
     */
    private String currentPlayer;



   /**
    * Constructor of UserStats which takes two parameters (rows, columns)
    * and stores labels for user stats
    * @param rows represents rows in array
    * @param columns represents columns in array
    */
    public UserStats(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        replicaLabelStringArray = new String[rows][columns];
    }


    /**
     * method that creates a array of the game board and calls upon
     * methods to find max number of consecutive pieces placed by
     * the current player, and how many pieces they placed
     * @param labelStringArray array values are stored in
     */
    public void updateLabelStringArray(String[][] labelStringArray) {
        for (int i = 0; i < labelStringArray.length; i++) {
            for (int j = 0; j < labelStringArray[i].length; j++) {
                replicaLabelStringArray[i][j] = labelStringArray[i][j];
            }
        }


        int length = currentPlayer.length();
        currentPlayer = currentPlayer.substring(length - 3, length - 2);
        findMaxConsecutiveSymbolsForSymbol(currentPlayer);
        incrementPlayerPieces(currentPlayer);
    }


    /**
     * method takes parameter of currentPlayer and sets it to the
     * current instance of currentPlayer variable
     * @param currentPlayer represents players who's turn it is
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    /**
     * method adds a piece to players counter when they place one
     * @param currentPlayer represents player who's turn it is
     */
    public void incrementPlayerPieces(String currentPlayer) {
        if (currentPlayer.equals("X")) {
            player1Pieces++;
        } else if (currentPlayer.equals("O")) {
            player2Pieces++;
        }
       
    }


    /**
     * getter method for player1pieces variable
     * @return number of pieces placed by player 1
     */
    public int getPlayer1Pieces() {
        return player1Pieces;
    }


    /**
     * getter method for player2pieces variable
     * @return number of pieces placed by player 2
     */
    public int getPlayer2Pieces() {
        return player2Pieces;
    }


    /**
     * getter method for player1streak variable
     * @return max number of connected player symbols
     */
    public int getPlayer1Streak() {
        return player1Streak;
    }
   
    /**
     * getter method for player2streak variable
     * @return max number of connected player symbols
     */
    public int getPlayer2Streak() {
        return player2Streak;
    }



    /**
     * method traverses the array and looks for consecutive
     * symbols horizontally, vertically, and diagnally and
     * and finds the highest consecutive amount in array
     * @param symbol represents current player's symbol
     */
    private void findMaxConsecutiveSymbolsForSymbol(String symbol) {
        int maxConsecutive = 0;
       
        // Check horizontally
        for (int row = 0; row < rows; row++) {
            int currentConsecutive = 0;
            for (int col = 0; col < columns; col++) {
                if (replicaLabelStringArray[row][col].equals(symbol)) {
                    currentConsecutive++;
                    if (currentConsecutive > maxConsecutive) {
                        maxConsecutive = currentConsecutive;
                    }
                } else {
                    currentConsecutive = 0;
                }
            }
        }
        // Check vertically
        for (int col = 0; col < columns; col++) {
            int currentConsecutive = 0;
            for (int row = 0; row < rows; row++) {
                if (replicaLabelStringArray[row][col].equals(symbol)) {
                    currentConsecutive++;
                    if (currentConsecutive > maxConsecutive) {
                        maxConsecutive = currentConsecutive;
                    }
                } else {
                    currentConsecutive = 0;
                }
            }
        }
        

        // Check diagonally (top-left to bottom-right)
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int currentConsecutive = 0;
                for (int i = 0; i < rows; i++) {
                    int newRow = row + i;
                    int newCol = col + i;
                    if (newRow < rows && newCol < columns && 
                        replicaLabelStringArray[newRow][newCol].equals(symbol)) {
                        currentConsecutive++;
                        if (currentConsecutive > maxConsecutive) {
                            maxConsecutive = currentConsecutive;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
   
        // Check diagonally (top-right to bottom-left)
        for (int row = 0; row < rows; row++) {
            for (int col = columns - 1; col >= 0; col--) {
                int currentConsecutive = 0;
                for (int i = 0; i < rows; i++) {
                    int newRow = row + i;
                    int newCol = col - i;
                    if (newRow < rows && newCol >= 0 && 
                        replicaLabelStringArray[newRow][newCol].equals(symbol)) {
                        currentConsecutive++;
                        if (currentConsecutive > maxConsecutive) {
                            maxConsecutive = currentConsecutive;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
   
        // Update the streak for the correct player
        if (symbol.equals("X")) {
            player1Streak = Math.max(player1Streak, maxConsecutive);
        } else if (symbol.equals("O")) {
            player2Streak = Math.max(player2Streak, maxConsecutive);
        }
    }
   
}
