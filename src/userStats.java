import java.util.Scanner;


public class UserStats {
    private static final int ROWS = ConnectFourGUI.rows ;
    private static final int COLUMNS = ConnectFourGUI.columns;
    private static final char EMPTY = '-';
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';


    private char[][] board;
    private int[] lastMove;
    private char currentPlayer;
    private int player1Pieces;
    private int player2Pieces;
    private int maxConnected1;
    private int maxConnected2;


    public UserStats() {
        board = new char[ROWS][COLUMNS];
        lastMove = new int[2];
        currentPlayer = PLAYER1; 
        player1Pieces = 0;
        player2Pieces = 0;
        maxConnected1 = 0;
        maxConnected2 = 0;
        initializeBoard();
    }


    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = EMPTY;
            }
        }
    }


    private void printBoard() {
        for (int row = ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < COLUMNS; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    private boolean isValidMove(int column) {
        return column >= 0 && column < COLUMNS && board[ROWS - 1][column] == EMPTY;
    }


    private void makeMove(int column) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][column] == EMPTY) {
                board[row][column] = currentPlayer;
                lastMove[0] = row;
                lastMove[1] = column;
                break;
            }
        }

        
    }


    private boolean checkWin() {
        int connected = 0;

        if (currentPlayer == PLAYER1) {
            // Check horizontal
            for (int col = 0; col < COLUMNS; col++) {
                connected = 0;
                for (int row = 0; row < ROWS; row++) {
                    if (board[row][col] == currentPlayer) {
                        connected++;
                        if (connected > maxConnected1) {
                            maxConnected1 = connected;
                        }
                        if (connected == UserInputDialogue.piecesToWin) {
                            return true;
                        }
                    } else {
                        connected = 0;
                    }
                }
            }


            // Check vertical
            for (int row = 0; row < ROWS; row++) {
                connected = 0;
                for (int col = 0; col < COLUMNS; col++) {
                    if (board[row][col] == currentPlayer) {
                        connected++;
                        if (connected > maxConnected1) {
                            maxConnected1 = connected;
                        }
                        if (connected == UserInputDialogue.piecesToWin) {
                            return true;
                        }
                    } else {
                        connected = 0;
                    }
                }
            }


            // Check diagonal (top-left to bottom-right)
            for (int row = 0; row < ROWS - UserInputDialogue.piecesToWin + 1; row++) {
                for (int col = 0; col < COLUMNS - UserInputDialogue.piecesToWin + 1; col++) {
                    connected = 0;
                    for (int i = 0; i < UserInputDialogue.piecesToWin; i++) {
                        if (board[row + i][col + i] == currentPlayer) {
                            connected++;
                            if (connected > maxConnected1) {
                                maxConnected1 = connected;
                            }
                            if (connected == UserInputDialogue.piecesToWin) {
                                return true;
                            }
                        } else {
                            connected = 0;
                        }
                    }
                }
            }


            // Check diagonal (bottom-left to top-right)
            for (int row = UserInputDialogue.piecesToWin - 1; row < ROWS; row++) {
                for (int col = 0; col < COLUMNS - UserInputDialogue.piecesToWin + 1; col++) {
                    connected = 0;
                    for (int i = 0; i < UserInputDialogue.piecesToWin; i++) {
                        if (board[row - i][col + i] == currentPlayer) {
                            connected++;
                            if (connected > maxConnected1) {
                                maxConnected1 = connected;
                            }
                            if (connected == UserInputDialogue.piecesToWin) {
                                return true;
                            }
                        } else {
                            connected = 0;
                        }
                    }
                }
            }
        } else {
            for (int col = 0; col < COLUMNS; col++) {
                connected = 0;
                for (int row = 0; row < ROWS; row++) {
                    if (board[row][col] == currentPlayer) {
                        connected++;
                        if (connected > maxConnected2) {
                            maxConnected2 = connected;
                        }
                        if (connected == UserInputDialogue.piecesToWin) {
                            return true;
                        }
                    } else {
                        connected = 0;
                    }
                }
            }


            // Check vertical
            for (int row = 0; row < ROWS; row++) {
                connected = 0;
                for (int col = 0; col < COLUMNS; col++) {
                    if (board[row][col] == currentPlayer) {
                        connected++;
                        if (connected > maxConnected2) {
                            maxConnected2 = connected;
                        }
                        if (connected == UserInputDialogue.piecesToWin) {
                            return true;
                        }
                    } else {
                        connected = 0;
                    }
                }
            }


            // Check diagonal (top-left to bottom-right)
            for (int row = 0; row < ROWS - UserInputDialogue.piecesToWin + 1; row++) {
                for (int col = 0; col < COLUMNS - UserInputDialogue.piecesToWin + 1; col++) {
                    connected = 0;
                    for (int i = 0; i < UserInputDialogue.piecesToWin; i++) {
                        if (board[row + i][col + i] == currentPlayer) {
                            connected++;
                            if (connected > maxConnected2) {
                                maxConnected2 = connected;
                            }
                            if (connected == UserInputDialogue.piecesToWin) {
                                return true;
                            }
                        } else {
                            connected = 0;
                        }
                    }
                }
            }


            // Check diagonal (bottom-left to top-right)
            for (int row = UserInputDialogue.piecesToWin - 1; row < ROWS; row++) {
                for (int col = 0; col < COLUMNS - UserInputDialogue.piecesToWin + 1; col++) {
                    connected = 0;
                    for (int i = 0; i < UserInputDialogue.piecesToWin; i++) {
                        if (board[row - i][col + i] == currentPlayer) {
                            connected++;
                            if (connected > maxConnected2) {
                                maxConnected2 = connected;
                            }
                            if (connected == UserInputDialogue.piecesToWin) {
                                return true;
                            }
                        } else {
                            connected = 0;
                        }
                    }
                }
            }
        }

        return false;
    }


    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;


        while (!gameOver) {
            printBoard();

            if (currentPlayer == PLAYER1) {
                System.out.println("Pieces Placed: " + player1Pieces);
            } else {
                System.out.println("Pieces Placed: " + player2Pieces);
            }

            if (currentPlayer == PLAYER1 ) {
                System.out.println("Maximum Connected Pieces: " + maxConnected1 );
            } else {
                System.out.println("Maximum Connected Pieces: " + maxConnected2 );
            }



            System.out.print("Enter column number (0-" + (COLUMNS - 1) + "): ");
            int column = scanner.nextInt();



            if (isValidMove(column)) {
                makeMove(column);


                if (currentPlayer == PLAYER1) {
                    player1Pieces++;
                } else {
                    player2Pieces++;
                }


                if (checkWin()) {
                    gameOver = true;
                    System.out.println("Player " + currentPlayer + " wins!");
                } else if (player1Pieces + player2Pieces == ROWS * COLUMNS) {
                    gameOver = true;
                    System.out.println("It's a draw!");
                } else {
                    currentPlayer = (currentPlayer == PLAYER1) ? PLAYER2 : PLAYER1;
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }


        scanner.close();
    }



    public static void main(String[] args) {
        UserStats game = new UserStats();
        game.play();
    }
}
