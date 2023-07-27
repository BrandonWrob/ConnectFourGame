# csc116-051-CE-02

Report Link [
    https://docs.google.com/document/d/14rHcsIZHYu5cl8nO4J0L-IIV3m07P27FKWhPYdq7USQ/edit
]

UML Diagram Link [
    https://cacoo.com/diagrams/AahisHAmDjqC02A6/2C242
]

Compiling [

To compile the program correctly you need to compile in the src directory and compile everything at once, so once there you type $ javac *.java then press enter, next you call the main class to run the program by typing $ java ConnectFourGUI   

It will not compile correctly if you don't follow the instructions!



]
Functionality of Classes:

UserInputDialogue Class [

collectUserInput method: This method creates JFrames to collect parameter values for the ConnectFourGUI Class:  first it shows a GUI to collect player 1's name, then player 2's name, and a win condition. It also confirms the user gives valid answers, else it will make them give a new value until it meets the criteria. The method then returns a string array with player 1 name, player 2 name, and win condition.

]

UserStats Class [

First the class creates a 2D private instance variables that create a replica 2D string array of game, number of rows, number of columns, and stat values of players.

UserStats Constructor: This constructor uses the parameters of rows and columns to assign them to their current instance variables and sets it as the size of the replica string array.

updateLabelStringArray Method: This method has the replica label string array copy the values of the lab string array which allows it to update with the GUI from the ConnectFourClass. 

setCurrentPlayer Method: This method takes a parameter of the current player and sets it to the current instance of currentPlayer variable

incrementPlayerPieces Method: This method will add 1 to a counter of the current player to represent that they performed a turn.

getPlayer1Pieces Method: getter method for player1pieces variable

getPlayer2Pieces Method: getter method for player2pieces variable

getPlayer1Streak Method: getter method for player1Streak variable

getPlayer2Streak Method: getter method for player2Streak variable

findMaxConsecutiveSymbolsForSymbol Method: This method has a parameter of the current players symbol, and will go through the replica string array to see how many consecutive symbols it can find. First it traverses through each row of the array and will add to a counter each consecutive symbol, once it fails to find a new one it will update a maxConsecutive int if it is greater than its current value, else it will reset counter and go to next row in array until it iterates the whole array. Then it will iterate the columns with the same process. Next it will check diagnally from top-left to bottom-right where it will iterate through the array by going right and down one with the same counting process, then it does it from top-right to bottom-left. This allows the method to check all possible directions for the symbol to be consecutive, and update the max found value for the specific symbol each time more consecutive symbols are found. Finally it will update the streak for the current player. 

]

EndScreen Class [

EndScreen Constructor: This constructor has the parameters winner, player1Win, player2Win, and ConnectFourGUI object. First the constructor creates a GUI with a specified size and font with a title of "Game Over". Next it has a JLabel which will display the winner from the parameter value, the winner string will display which player won (or if nobody won that board was full), next it will use the player1Win and player2Win parameters to show the scoreboard from previous and current game of wins. FInally below this is a JButton which displays "Replay", this has an ActionListener attached where if it is clicked then it will use the resetGame method from ConnectFourGUI class to create a new game. 

]

ConnectFourGUI Class [

First the class creates private instance variables to be used withen the class

main Class: This class first calls upon the UserInputDialogue class to create GUI prompts to collect parameter values to be used when designing the game from the user. It returns an array of the users names and win condition they want, next the method stores these values in variables and calls the constructo to create a GUI of the game.

ConnectFourGUI Constructor: This constructor has parameters rows, columns, player1, player2, piecesToWin. First the constructor stores the parameter values into instance variables, next it creates a main JFrame with a specified size, then creates a JPanel with a specified layout. First it creates a header for the game which is 1 row, 3 columns. The first column is set to store which player's turn it is, the second stores the max number of connected pieces the player has, and the third has the total number of pieces placed by that player. Next it uses the row and column instance variables to create a label string array which is filled with "E", and a grid array made of JLabels in the GUI which are blank, followed by a extra row of buttons that represent the column. Next it adds active listeners to the buttons so if the user clicks them then the program will know. Next it creates a user stats object with the appropriate row and column size for a replica array as the parameter. 

actionPerformed Method: This method represents when a button is clicked in the ConnectFourGUI game, first it confirms that the game is still being played (if game is over then user cannot perform an action), next it gets the text of the button clicked to know which column the user selected. Then it uses an if statement to know which players turn it is, once foun it will iterate through the column selected and confirm there is a spot available, if there is then it will update it with the Users symbol, else it will it will show a message dialogue informing them the column is full and will let them go again. Once a valid move is made it will use the switch user method to switch users, next it will call the updateStats method to update the string array in the game, it will also check if a player won or if board is full, if either are true then it will end the game and call the EndScreen class. 

switchUser method: First it collects the current player turn and checks if it equals player 1, if it does it changes it to player 2, else if false it sets it as player 1. Next if will use the userStats method to collect the stats of the user it is switching to, then it will use those stats to update the headerPanel with the next players data. 

isBoardFull method: This method iterates through the board looking to see if it has a "E" string left (as in empty), if it does then it returns false, else it will return true.

getPlayer1Name: getter method for player 1 name

getPlayer2Name: getter method for player 2 name

getPlayer1WinCount: getter method for total number of wins by player 1

getPlayer2WinCount: getter method for total number of wins by player 2

resetGame: This method resets the game to let the users play again, first it says the current user back to player 1, then it iterates the string array setting all the values back to "E", next it goes through the JLabel arrays in GUI and sets the labels equal to "", then it resets the user array in UserStats class, and updates the headerPanel with setText to go back to its original values. Finally it sets gameEnded to false so a new game can begin!
]
