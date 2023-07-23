public class UserStats {


    private String[][] replicaLabelStringArray;
    private int rows;
    private int columns;
    private int player1Pieces = 0;
    private int player2Pieces = 0;
    private int player1Streak = 0;
    private int player2Streak = 0;
    private String currentPlayer;


    public UserStats(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        replicaLabelStringArray = new String[rows][columns];
    }


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
        printReplicaLabelStringArray();
    }


    public void printReplicaLabelStringArray() {
        for (int i = 0; i < replicaLabelStringArray.length; i++) {
            for (int j = 0; j < replicaLabelStringArray[i].length; j++) {
                System.out.print(replicaLabelStringArray[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("current player is " + currentPlayer);
        System.out.println("player one placed " + player1Pieces);
        System.out.println("player two placed " + player2Pieces);
        System.out.println("player one streak " + player1Streak);
        System.out.println("player two streak " + player2Streak);
    }


    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    public void incrementPlayerPieces(String currentPlayer) {
        if (currentPlayer.equals("X")) {
            player1Pieces++;
        } else if (currentPlayer.equals("O")) {
            player2Pieces++;
        }
    }


    public int getPlayer1Pieces() {
        return player1Pieces;
    }


    public int getPlayer2Pieces() {
        return player2Pieces;
    }


    public int getPlayer1Streak() {
        return player1Streak;
    }


    public int getPlayer2Streak() {
        return player2Streak;
    }


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


        // Update the streak for the correct player
        if (symbol.equals("X")) {
            player1Streak = Math.max(player1Streak, maxConsecutive);
        } else if (symbol.equals("O")) {
            player2Streak = Math.max(player2Streak, maxConsecutive);
        }
    }
}


