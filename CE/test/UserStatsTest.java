import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests for UserStats
 * @author Dolly Jani 
 * @author Vishal Kommidi
 * @author Brandon Wroblewski
 */

public class UserStatsTest {

    /**
     * user stats for testing
     */
    private UserStats userStats;


    /**
     * BeforeEach method
     */
    @BeforeEach
    public void setUp() {
    
        userStats = new UserStats(3, 3);
    }

    /**
     * Tests increment player pieces 
     */
    @Test
    public void testIncrementPlayerPieces() {
            
        userStats.incrementPlayerPieces("X");
        assertEquals(1, userStats.getPlayer1Pieces());
        userStats.incrementPlayerPieces("O");
        assertEquals(1, userStats.getPlayer2Pieces());
        userStats.incrementPlayerPieces("X");
        userStats.incrementPlayerPieces("X");
        assertEquals(3, userStats.getPlayer1Pieces());
        userStats.incrementPlayerPieces("A");
        assertEquals(3, userStats.getPlayer1Pieces());
        assertEquals(1, userStats.getPlayer2Pieces());
    }

    /**
     * Tests increment player pieces player 1
     */
    @Test
    public void testIncrementPlayerPiecesPlayer1() {
        userStats.incrementPlayerPieces("X");
        userStats.incrementPlayerPieces("X");
        userStats.incrementPlayerPieces("X");
        assertEquals(3, userStats.getPlayer1Pieces());
        assertEquals(0, userStats.getPlayer2Pieces());
    }

    /**
     * Tests increment player pieces player 2
     */
    @Test
    public void testIncrementPlayerPiecesPlayer2() {
        userStats.incrementPlayerPieces("O");
        userStats.incrementPlayerPieces("O");
        userStats.incrementPlayerPieces("O");
        userStats.incrementPlayerPieces("O");
        assertEquals(4, userStats.getPlayer2Pieces());
        assertEquals(0, userStats.getPlayer1Pieces());
    }

    /**
     * Tests find maximum consecutive symbol 
     */
    @Test
    public void testFindMaxConsecutiveSymbolsForSymbolPlayer1() {
        String[][] labelStringArray = {
            {"X", "X", "X"},
            {"O", "X", "X"},
            {"X", "O", "X"}
        };

        
        userStats.setCurrentPlayer("Player1");
        userStats.updateLabelStringArray(labelStringArray);

        assertEquals(0, userStats.getPlayer1Streak());
        assertEquals(0, userStats.getPlayer2Streak());
    }

    /**
     * Tests find maximum consecutive symbol 
     */
    @Test
    public void testFindMaxConsecutiveSymbolsForSymbol() {
        String[][] labelStringArray = {
            {"X", "O", "X"},
            {"O", "X", "O"},
            {"O", "X", "O"}
        };
        
        userStats.setCurrentPlayer("Player1");
        userStats.updateLabelStringArray(labelStringArray);
        assertEquals(0, userStats.getPlayer1Streak());
        assertEquals(0, userStats.getPlayer2Streak());
    }
}